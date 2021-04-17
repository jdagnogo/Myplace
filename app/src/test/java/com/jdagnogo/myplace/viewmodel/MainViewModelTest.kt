package com.jdagnogo.myplace.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.jdagnogo.myplace.MainCoroutineRule
import com.jdagnogo.myplace.model.Resource
import com.jdagnogo.myplace.model.Venue
import com.jdagnogo.myplace.model.VenueDetails
import com.jdagnogo.myplace.repository.VenueRepository
import com.jdagnogo.myplace.repository.api.VenueRemoteDataTest
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.willAnswer
import getOrAwaitValue
import junit.framework.TestCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import java.io.IOException

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest : TestCase() {
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()
    private lateinit var sut: MainViewModel

    @Mock
    lateinit var repository: VenueRepository

    lateinit var venueDetails: VenueDetails
    lateinit var venues: List<Venue>

    @Before
    fun setup() {
        sut = MainViewModel(repository)
    }

    //region getVenueDetails
    private fun fakeVenuDetailsFlow() = flow {
        venueDetails = VenueDetails(FAKE_QUERY)
        val data = Resource.success(venueDetails)
        emit(data)
    }
    @Test
    fun `given that the repository will generate valid response, calling getVenueDetails should post a false to the spinner and post the new data in currentVenueDetails`() =
        mainCoroutineRule.testDispatcher.runBlockingTest {
            sut.currentVenueId = FAKE_STRING
            given(repository.getVenueDetails(FAKE_STRING)).willReturn(fakeVenuDetailsFlow())

            sut.getVenueDetails()
            val value = sut.currentVenueDetails.getOrAwaitValue()
            val spinnerValue = sut.spinner.getOrAwaitValue()

            assertTrue(value == venueDetails)
            assertTrue(spinnerValue.not())
        }
    @Test
    fun `given an error in the repository, calling getVenueDetails should cancel the job`() =
        mainCoroutineRule.testDispatcher.runBlockingTest {
            sut.currentVenueId = FAKE_STRING
            given(repository.getVenueDetails(FAKE_STRING)).willAnswer {
                throw IOException()
            }

            sut.getVenueDetails()

            assertTrue(sut.venueDetailsJob?.isCancelled == true)
        }
    //endregion

    //region searchVenue
    private fun fakeValidVenuesFlow() = flow {
        venues = listOf(Venue())
        val resource = Resource.success(venues)
        emit(resource)
    }

    private fun fakeLoadingVenuesFlow() = flow {
        venues = listOf(Venue())
        val resource = Resource.loading(venues)
        emit(resource)
    }
    @Test
    fun `given that the repository will generate valid response, calling searchVenue should post a false to the spinner and post the new data in currentVenueDetails and reset the error message`() =
        mainCoroutineRule.testDispatcher.runBlockingTest {
            given(repository.getData(FAKE_QUERY)).willReturn(fakeValidVenuesFlow())

            sut.searchVenue(FAKE_QUERY)
            val value = sut.currentResult.getOrAwaitValue()
            val spinnerValue = sut.spinner.getOrAwaitValue()
            val errorValue = sut.errorMessage.getOrAwaitValue()

            assertTrue(value == venues)
            assertTrue(errorValue.isEmpty())
            assertTrue(spinnerValue.not())
        }

    @Test
    fun `given that the repository will generate loading response, calling searchVenue should only post true to the spinner`() =
        mainCoroutineRule.testDispatcher.runBlockingTest {
            given(repository.getData(FAKE_QUERY)).willReturn(fakeLoadingVenuesFlow())

            sut.searchVenue(FAKE_QUERY)
            val spinnerValue = sut.spinner.getOrAwaitValue()

            assertTrue(spinnerValue)
            assertTrue(sut.currentResult.value == null)
        }
    //endregion

    //region onSnackbarShown
    @Test
    fun `calling onSnackbarShown should set the _snackbar value to null`(){
        sut._snackbar.postValue(0)

        sut.onSnackbarShown()
        val value = sut.snackbar.getOrAwaitValue()

        assertNull(value)
    }
    //endregion

    companion object {
        private const val FAKE_QUERY = "FAKE_QUERY"
        private const val FAKE_STRING = "FAKE_STRING"
    }
}