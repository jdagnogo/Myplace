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
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(var repository: VenueRepository) : ViewModel(), VenueListener {
    lateinit var view: VenueListView
    private var searchJob: Job? = null

    fun searchVenue(query: String) {
        searchJob?.cancel()
        val result = repository.getData(query).onStart {
            view.displayLoader()
        }.catch {
            //todo : display error msg
            val toto = 2
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
                if (result.data?.isEmpty() == true){
                    view.displayNoValue()
                }else{
                    view.onNewData(result.data)
                }

            }
            Resource.Status.LOADING -> {
                val toto = 2
            }
            Resource.Status.ERROR -> {
            }
        }
    }

    override fun onClick(venue: Venue) {

    }
}