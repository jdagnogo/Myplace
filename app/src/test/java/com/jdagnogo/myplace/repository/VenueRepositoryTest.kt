package com.jdagnogo.myplace.repository

import com.jdagnogo.myplace.MainCoroutineRule
import com.jdagnogo.myplace.model.Resource
import com.jdagnogo.myplace.model.Venue
import com.jdagnogo.myplace.model.VenueDetails
import com.jdagnogo.myplace.repository.api.VenueRemoteData
import com.jdagnogo.myplace.repository.data.VenueDao
import com.jdagnogo.myplace.repository.data.VenueDetailsDao
import com.nhaarman.mockitokotlin2.given
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner


@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class VenueRepositoryTest {
    private lateinit var sut: VenueRepository

    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    @Mock
    lateinit var remoteData: VenueRemoteData

    @Mock
    lateinit var dao: VenueDao

    @Mock
    lateinit var venueDetailsDao: VenueDetailsDao

    lateinit var venues: List<Venue>
    lateinit var venueDetails: VenueDetails

    @Before
    fun setup() {
        sut = VenueRepository(remoteData, dao, venueDetailsDao)
    }

    //region getVenueDetails
    private fun fakeVenuesFlow(venues: List<Venue>) = flow {
        emit(venues)
    }

    private fun fakeVenuesFlow(venueDetails: VenueDetails) = flow {
        emit(venueDetails)
    }

    /**
     * The goal of this test is to make sure that singleSourceOfTruthStrategy is working
     * We should first emit the loading state
     * then get the data from the database
     * then ask the remote data
     * then if it s a success save it to the database
     * then ask again the latest value from the database => those value should be the same as the remote one
     */
    @Test
    fun `given a success fetch, getdata should provide the lastest values `() =
        mainCoroutineRule.testDispatcher.runBlockingTest {
            venues = listOf(Venue())
            val venuesRemote = listOf(Venue(FAKE_ID))
            given(dao.getAll(FAKE_STRING)).willReturn(fakeVenuesFlow(venues))
            given(remoteData.getVenues(FAKE_STRING)).willReturn(Resource.success(venuesRemote))
            given(dao.insertAll(venuesRemote)).willAnswer {
                given(dao.getAll(FAKE_STRING)).willReturn(fakeVenuesFlow(venuesRemote))
            }

            val result = sut.getData(FAKE_STRING).toList()

            assert(result.first().status == Resource.Status.LOADING)
            assert(result[1].status == Resource.Status.SUCCESS)
            assert(result[1].data == venues)
            assert(result[2].status == Resource.Status.SUCCESS)
            assert(result[2].data == venuesRemote)
        }

    /**
     * The goal of this test is to make sure that singleSourceOfTruthStrategy is working when the data from remote contains error
     * We should first emit the loading state
     * then get the data from the database
     * then ask the remote data
     * as it will fails, we should emit an error with the same error code
     *
     */
    @Test
    fun `given a failed fetch, getdata should provide the lastest values `() =
        mainCoroutineRule.testDispatcher.runBlockingTest {
            venues = listOf(Venue())
            val venuesRemote = listOf(Venue(FAKE_ID))
            given(dao.getAll(FAKE_STRING)).willReturn(fakeVenuesFlow(venues))
            given(remoteData.getVenues(FAKE_STRING)).willReturn(
                Resource.error(
                    FAKE_STRING,
                    venuesRemote
                )
            )

            val result = sut.getData(FAKE_STRING).toList()

            assert(result.first().status == Resource.Status.LOADING)
            assert(result[1].status == Resource.Status.SUCCESS)
            assert(result[1].data == venues)
            assert(result[2].status == Resource.Status.ERROR)
            assert(result[2].code == FAKE_STRING)
        }
    //endregion

    //region getVenueDetails
    @Test
    fun `given a success fetch, getVenueDetails should provide the lastest values `() =
        mainCoroutineRule.testDispatcher.runBlockingTest {
            venueDetails = VenueDetails()
            val venuesRemote = VenueDetails(FAKE_ID)
            given(venueDetailsDao.getVenueDetails(FAKE_STRING)).willReturn(fakeVenuesFlow(venueDetails))
            given(remoteData.getVenueDetails(FAKE_STRING)).willReturn(Resource.success(venuesRemote))
            given(venueDetailsDao.insert(venuesRemote)).willAnswer {
                given(venueDetailsDao.getVenueDetails(FAKE_STRING)).willReturn(fakeVenuesFlow(venuesRemote))
            }

            val result = sut.getVenueDetails(FAKE_STRING).toList()

            assert(result.first().status == Resource.Status.LOADING)
            assert(result[1].status == Resource.Status.SUCCESS)
            assert(result[1].data == venueDetails)
            assert(result[2].status == Resource.Status.SUCCESS)
            assert(result[2].data == venuesRemote)
        }
    //endregion

    companion object {
        private const val FAKE_ID = "FAKE_QUERY"
        private const val FAKE_STRING = "FAKE_STRING"
    }
}