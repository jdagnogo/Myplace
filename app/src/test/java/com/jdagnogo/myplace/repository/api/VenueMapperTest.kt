package com.jdagnogo.myplace.repository.api

import com.jdagnogo.myplace.model.VenueDetails
import com.jdagnogo.myplace.repository.api.VenueMapper.Companion.DEFAULT_ADDRESS
import com.jdagnogo.myplace.repository.api.model.*
import junit.framework.TestCase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.BDDMockito.given
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@RunWith(MockitoJUnitRunner::class)
class VenueMapperTest : TestCase() {
    private val sut = VenueMapper()

    @Mock
    lateinit var searchApiResponse: SearchApiResponse

    @Mock
    lateinit var venueDetailsApiResponse: VenueDetailsApiResponse

    @Mock
    lateinit var searchContentResponse: SearchContentResponse

    @Mock
    lateinit var venueDetailsContentResponse: VenueDetailsContentResponse

    private lateinit var venueDetailsResponse: VenueDetailsResponse

    @Before
    fun setup() {
        given(searchApiResponse.data).willReturn(searchContentResponse)
        given(venueDetailsApiResponse.data).willReturn(venueDetailsContentResponse)

    }

    //region toVenue
    @Test
    fun `given a valid searchResponse with 2 item to toVenue should return a list with the 2 Venue`() {
        val fakeLocation = LocationResponse(FAKE_ADDRESS)
        val venue1 = VenueResponse(FAKE_ID, FAKE_NAME, fakeLocation)
        val venue2 = VenueResponse(FAKE_ID2, FAKE_NAME, fakeLocation)
        val venues = listOf(venue1, venue2)
        given(searchContentResponse.venues).willReturn(venues)

        val result = sut.toVenue(searchApiResponse, FAKE_QUERY)

        assertTrue("should have 2 items", result.size == 2)
        assertTrue("should have been FAKE_ID2", result[1].id == FAKE_ID2)
        with(result[0]) {
            assertTrue("should have been FAKE_ID", id == venue1.id)
            assertTrue("should have been FAKE_NAME", name == venue1.name)
            assertTrue("should have been FAKE_ADDRESS", address == venue1.location.address)
        }
    }

    @Test
    fun `given a valid searchResponse with 1 item but with an empty adress to toVenue should return a list with the Venue with the default address`() {
        val venues = listOf(VenueResponse())
        given(searchContentResponse.venues).willReturn(venues)

        val result = sut.toVenue(searchApiResponse, FAKE_QUERY)

        assertTrue("should have been ---", result[0].address == DEFAULT_ADDRESS)
    }

    @Test
    fun `given an invalid searchResponse to toVenue should return an empty list `() {
        //no op

        val result = sut.toVenue(searchApiResponse, FAKE_QUERY)

        assertTrue("should have been an empty list", result.isEmpty())
    }
    //endregion

    //region toVenueDetails
    @Test
    fun `given a valid venueDetailsApiResponse to toVenueDetails should return a venueDetails with the correct info`() {
        val contactInfoResponse = ContactInfoResponse(facebookName = FAKE_FACEBOOK, formattedPhone = FAKE_PHONE, twitter = FAKE_TWITTER)
        val photoResponse = PhotoResponse(listOf(PhotoGroupResponse(listOf(PhotoItemsResponse(FAKE_PREFIX, FAKE_SUFFIX)))))
        venueDetailsResponse = VenueDetailsResponse(FAKE_ID, FAKE_NAME, FAKE_NAME, FAKE_FLOAT, contactInfoResponse, photoResponse)
        given(venueDetailsContentResponse.venueDetails).willReturn(venueDetailsResponse)

        val result = sut.toVenueDetails(venueDetailsApiResponse)

        assertTrue("should have been FAKE_NAME", result.description == FAKE_NAME)
        assertTrue("should have been FAKE_FACEBOOK", result.facebook == FAKE_FACEBOOK)
        assertTrue("should have been FAKE_TWITTER", result.twitter == FAKE_TWITTER)
        assertTrue("should have been FAKE_PHONE", result.phone == FAKE_PHONE)
        assertTrue("should have been FAKE_PREFIXFAKE_SUFFIX", result.photo == FAKE_PREFIX + FAKE_SUFFIX)
        assertTrue("should have been FAKE_FLOAT", result.rating == FAKE_FLOAT)
    }

    @Test
    fun `given an invalid venueDetailsApiResponse to toVenueDetails should return an VenueDetails with the default values `() {
        val emptyVenue = VenueDetails()
        given(venueDetailsContentResponse.venueDetails).willReturn(VenueDetailsResponse())

        val result = sut.toVenueDetails(venueDetailsApiResponse)

        assertTrue(result == emptyVenue)
    }
    //endregion


    companion object {
        private const val FAKE_ID = "FAKE_ID"
        private const val FAKE_ID2 = "FAKE_ID2"
        private const val FAKE_NAME = "FAKE_NAME"
        private const val FAKE_FACEBOOK = "FAKE_FACEBOOK"
        private const val FAKE_PHONE = "FAKE_PHONE"
        private const val FAKE_TWITTER = "FAKE_TWITTER"
        private const val FAKE_ADDRESS = "FAKE_ADDRESS"
        private const val FAKE_SUFFIX = "FAKE_SUFFIX"
        private const val FAKE_PREFIX = "FAKE_PREFIX"
        private const val FAKE_QUERY = "FAKE_QUERY"
        private const val FAKE_FLOAT = 30f
    }
}