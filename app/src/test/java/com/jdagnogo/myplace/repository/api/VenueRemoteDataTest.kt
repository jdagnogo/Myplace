package com.jdagnogo.myplace.repository.api

import com.jdagnogo.myplace.model.Resource
import com.jdagnogo.myplace.model.Venue
import com.jdagnogo.myplace.model.VenueDetails
import com.jdagnogo.myplace.repository.api.VenueRemoteData.Companion.CLIENT_ID
import com.jdagnogo.myplace.repository.api.model.SearchApiResponse
import com.jdagnogo.myplace.repository.api.model.VenueDetailsApiResponse
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@RunWith(MockitoJUnitRunner::class)
class VenueRemoteDataTest {
    private lateinit var sut: VenueRemoteData

    @Mock
    lateinit var venueDetailsApi: VenueDetailsApi

    @Mock
    lateinit var venueApi: VenueApi

    @Mock
    lateinit var mapper: VenueMapper

    @Before
    fun setup() {
        sut = VenueRemoteData(venueApi, venueDetailsApi, mapper)
    }

    //region getVenues
    @Test
    fun `given valid parameter to api, getVenues should return a success ressource with the data`() =
        runBlocking {
            val fakeResponse = mock<SearchApiResponse>()
            val fakeVenues = mock<List<Venue>>()
            given(
                venueApi.getVenue(
                    VenueRemoteData.CLIENT_ID,
                    VenueRemoteData.CLIENT_SECRET,
                    VenueRemoteData.VERSION, FAKE_QUERY,
                    VenueRemoteData.RADIUS,
                    VenueRemoteData.LIMIT
                )
            ).willReturn(fakeResponse)
            given(mapper.toVenue(fakeResponse, FAKE_QUERY)).willReturn(fakeVenues)

            val result = sut.getVenues(FAKE_QUERY)

            assertTrue("should be SUCCESS", result.status == Resource.Status.SUCCESS)
            assertTrue("should have the same data", result.data == fakeVenues)
        }

    @Test
    fun `given invalid param to api, getVenues should catch the error and return a error ressource with the message`() =
        runBlocking {
            given(
                venueApi.getVenue(
                    VenueRemoteData.CLIENT_ID,
                    VenueRemoteData.CLIENT_SECRET,
                    VenueRemoteData.VERSION, FAKE_QUERY,
                    VenueRemoteData.RADIUS,
                    VenueRemoteData.LIMIT
                )
            ).willAnswer {
                throw IOException(FAKE_STRING)
            }

            val result = sut.getVenues(FAKE_QUERY)

            assertTrue("should be ERROR", result.status == Resource.Status.ERROR)
            assertTrue("should have empty data", result.data?.isEmpty() == true)
            assertTrue("should have been FAKE_STRING", result.code == FAKE_STRING)
        }
    //endregion

    //region getVenueDetails
    @Test
    fun `given valid parameter to api, getVenueDetails should return a success ressource with the data`() =
        runBlocking {
            val fakeResponse = VenueDetailsApiResponse()
            val fakeVenueDetails = VenueDetails()
            given(
                venueDetailsApi.getVenueDetails(
                    FAKE_STRING,
                    CLIENT_ID,
                    VenueRemoteData.CLIENT_SECRET,
                    VenueRemoteData.VERSION
                )
            ).willReturn(fakeResponse)
            given(mapper.toVenueDetails(fakeResponse)).willReturn(fakeVenueDetails)

            val result = sut.getVenueDetails(FAKE_STRING)

            assertTrue("should be SUCCESS", result.status == Resource.Status.SUCCESS)
            assertTrue("should have the same data", result.data == fakeVenueDetails)
        }

    @Test
    fun `given invalid param to api, getVenueDetails should catch the error and return a error ressource with the message`() =
        runBlocking {
            given(
                venueDetailsApi.getVenueDetails(
                    FAKE_QUERY,
                    CLIENT_ID,
                    VenueRemoteData.CLIENT_SECRET,
                    VenueRemoteData.VERSION
                )
            ).willAnswer {
                throw IOException(FAKE_STRING)
            }

            val result = sut.getVenueDetails(FAKE_QUERY)

            assertTrue("should be ERROR", result.status == Resource.Status.ERROR)
            assertTrue("should have empty data", result.data == VenueDetails())
            assertTrue("should have been FAKE_STRING", result.code == FAKE_STRING)
        }
    //endregion
    companion object {
        private const val FAKE_QUERY = "FAKE_QUERY"
        private const val FAKE_STRING = "FAKE_STRING"
    }

}