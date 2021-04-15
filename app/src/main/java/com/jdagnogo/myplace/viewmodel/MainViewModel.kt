package com.jdagnogo.myplace.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jdagnogo.myplace.model.Resource
import com.jdagnogo.myplace.model.Venue
import com.jdagnogo.myplace.repository.VenueRepository
import com.jdagnogo.myplace.ui.VenueListView
import com.jdagnogo.myplace.ui.adapter.VenueListener
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(var repository: VenueRepository) : ViewModel(), VenueListener {
    lateinit var view: VenueListView
    private var searchJob: Job? = null

    fun searchVenue(query: String) {
        searchJob?.cancel()
        val result = repository.getData(query)
            .catch {
                searchJob?.cancel()
                view.displayError("Oups.. something is wrong !")
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
                    view.onNewData(result.data)
                }
            }
            Resource.Status.LOADING -> {
                view.displayLoader()
            }
            Resource.Status.ERROR -> {
                handleError(result.code)
            }
        }
    }

    private fun handleError(code: String?) {
        when (code) {
            ERROR_400 -> {
                view.displayError("Wrong Localization")
            }
            ERROR_403, ERROR_429 -> {
                view.displayError("You have reached your limit")
            }
            ERROR_500 -> {
                view.displayError("Internal server error")
            }
            ERROR_NO_INTERNET -> {
                view.displayNoInternet()
            }
            else -> {
                view.displayError("Oups.. something is wrong !")
            }
        }
    }

    override fun onClick(venue: Venue) {

    }

    companion object {
        private const val ERROR_400 = "HTTP 400 "
        private const val ERROR_403 = "HTTP 403"
        private const val ERROR_429 = "HTTP 429"
        private const val ERROR_500 = "HTTP 500"
        private const val ERROR_NO_INTERNET =
            "Unable to resolve host \"api.foursquare.com\": No address associated with hostname"
    }
}