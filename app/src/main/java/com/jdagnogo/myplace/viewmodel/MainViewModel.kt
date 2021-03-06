package com.jdagnogo.myplace.viewmodel

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jdagnogo.myplace.R
import com.jdagnogo.myplace.model.Resource
import com.jdagnogo.myplace.model.Venue
import com.jdagnogo.myplace.model.VenueDetails
import com.jdagnogo.myplace.repository.VenueRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(var repository: VenueRepository) : ViewModel() {
    @VisibleForTesting var searchJob: Job? = null
    @VisibleForTesting var venueDetailsJob: Job? = null
    var currentVenueId: String? = null


    private val _currentResult = MutableLiveData<List<Venue>?>()
    val currentResult: LiveData<List<Venue>?>
        get() = _currentResult

    private val _currentVenueDetails = MutableLiveData<VenueDetails?>()
    val currentVenueDetails: LiveData<VenueDetails?>
        get() = _currentVenueDetails

    private val _errorMessage = MutableLiveData<Int>()
    val errorMessage: LiveData<Int>
        get() = _errorMessage

    /**
     * Show a loading spinner if true
     */
    private val _spinner = MutableLiveData<Boolean>(false)
    val spinner: LiveData<Boolean>
        get() = _spinner

    /**
     * Request a snackbar to display a message.
     * This is used to otify that we dont have internet
     */
    @VisibleForTesting
    val _snackbar = MutableLiveData<Int?>()
    val snackbar: LiveData<Int?>
        get() = _snackbar

    /**
     * We want to display the snackbar only once.
     * So after that the value is displayed, we should reset the value
     */
    fun onSnackbarShown() {
        _snackbar.value = null
    }

    fun getVenueDetails() {
        venueDetailsJob?.cancel()
        val result = repository.getVenueDetails(currentVenueId ?: "")
                .catch {
                    venueDetailsJob?.cancel()
                }
        venueDetailsJob = viewModelScope.launch {
            result.collectLatest {
                handleResource(it) {
                    it.data?.let { data ->
                        _spinner.postValue(false)
                        _currentVenueDetails.postValue(data)
                    }
                }
            }
        }
    }

    fun searchVenue(query: String) {
        searchJob?.cancel()
        val result = repository.getData(query)
                .catch {
                    searchJob?.cancel()
                    _spinner.postValue(false)
                    _errorMessage.postValue(R.string.unknown_error)
                }
        searchJob = viewModelScope.launch {
            result.collectLatest {
                handleResource(it) {
                    if (it.data?.isEmpty() == false) {
                        _spinner.postValue(false)
                        _errorMessage.postValue(0)
                        _currentResult.postValue(it.data)
                    }
                }
            }
        }
    }

    /**
     * A method to handle the status of the ressource
     * Note that for LOADING and ERROR we have the same behavior
     * But with SUCCESS, the function should decide what to do
     */
    private fun <T> handleResource(result: Resource<T>, function: () -> (Unit)) {
        when (result.status) {
            Resource.Status.SUCCESS -> {
                function()
            }
            Resource.Status.LOADING -> {
                _spinner.postValue(true)
            }
            else -> {
                handleError(result.code)
            }
        }
    }

    /**
     * This method will have all error cases.
     * We will trigger the snackbar when we see the no internet error
     * Or we will post a value in _errorMessage with the correct message
     */
    private fun handleError(code: String?) {
        _spinner.postValue(false)
        if (code == ERROR_NO_INTERNET) {
            _snackbar.postValue(R.string.no_internet)
            return
        }
        _currentResult.postValue(null)
        val msg = when (code) {
            ERROR_400 -> {
                R.string.wrong_location
            }
            ERROR_403, ERROR_429 -> {
                R.string.limit
            }
            ERROR_500 -> {
                R.string.internal_error
            }
            else -> {
                R.string.unknown_error
            }
        }
        _errorMessage.postValue(msg)
    }

    companion object {
        const val ERROR_400 = "HTTP 400 "
        const val ERROR_403 = "HTTP 403"
        const val ERROR_429 = "HTTP 429"
        const val ERROR_500 = "HTTP 500"
        const val ERROR_NO_INTERNET =
            "Unable to resolve host \"api.foursquare.com\": No address associated with hostname"
    }
}