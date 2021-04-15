package com.jdagnogo.myplace.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jdagnogo.myplace.R
import com.jdagnogo.myplace.model.Resource
import com.jdagnogo.myplace.model.Venue
import com.jdagnogo.myplace.repository.VenueRepository
import com.jdagnogo.myplace.ui.adapter.VenueListener
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(var repository: VenueRepository) : ViewModel() {
    private var searchJob: Job? = null
    var currentVenueId: String? = null

    private val _currentResult = MutableLiveData<List<Venue>?>()
    val currentResult: LiveData<List<Venue>?>
        get() = _currentResult

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String>
        get() = _errorMessage

    /**
     * Show a loading spinner if true
     */
    private val _spinner = MutableLiveData<Boolean>(false)
    val spinner: LiveData<Boolean>
        get() = _spinner

    /**
     * Request a snackbar to display a string.
     */
    private val _snackbar = MutableLiveData<Int?>()
    val snackbar: LiveData<Int?>
        get() = _snackbar
    fun onSnackbarShown() {
        _snackbar.value = null
    }

    fun searchVenue(query: String) {
        searchJob?.cancel()
        val result = repository.getData(query)
            .catch {
                searchJob?.cancel()
                _spinner.postValue(false)
                _errorMessage.postValue("Oups.. something is wrong !")
            }
        searchJob = viewModelScope.launch {
            result.collectLatest {
                handleResource(it)
            }
        }

    }

    private fun handleResource(result: Resource<List<Venue>>) {
        when (result.status) {
            Resource.Status.SUCCESS -> {
                if (result.data?.isEmpty() == false) {
                    _spinner.postValue(false)
                    _currentResult.postValue(result.data)
                }
            }
            Resource.Status.LOADING -> {
                _spinner.postValue(true)
            }
            Resource.Status.ERROR -> {
                handleError(result.code)
            }
        }
    }

    private fun handleError(code: String?) {
        _spinner.postValue(false)
        _currentResult.postValue(null)
        if (code == ERROR_NO_INTERNET) _snackbar.postValue(R.string.no_internet)
        val msg = when (code) {
            ERROR_400 -> {
                "Wrong Localization"
            }
            ERROR_403, ERROR_429 -> {
                "You have reached your limit"
            }
            ERROR_500 -> {
                "Internal server error"
            }
            else -> {
                "Oups.. something is wrong !"
            }
        }
        _errorMessage.postValue(msg)
    }

    companion object {
        //Todo : enum
        private const val ERROR_400 = "HTTP 400 "
        private const val ERROR_403 = "HTTP 403"
        private const val ERROR_429 = "HTTP 429"
        private const val ERROR_500 = "HTTP 500"
        private const val ERROR_NO_INTERNET =
            "Unable to resolve host \"api.foursquare.com\": No address associated with hostname"
    }
}