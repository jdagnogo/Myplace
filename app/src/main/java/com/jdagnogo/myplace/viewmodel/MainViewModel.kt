package com.jdagnogo.myplace.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.jdagnogo.myplace.model.Resource
import com.jdagnogo.myplace.model.Venue
import com.jdagnogo.myplace.repository.VenueRepository
import com.jdagnogo.myplace.ui.adapter.VenueListener
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

class MainViewModel @Inject constructor(repository: VenueRepository) : ViewModel(), VenueListener{

    val venues: LiveData<Resource<List<Venue>>> = repository.getData("")
        .onStart {
            Resource.loading(null)
        }
        .catch {
            //todo : display error msg
            val toto = 2
        }
        .asLiveData(viewModelScope.coroutineContext)

    override fun onClick(venue: Venue) {

    }
}