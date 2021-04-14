package com.jdagnogo.myplace.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jdagnogo.myplace.repository.VenueRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(val repository: VenueRepository) : ViewModel() {
    fun test() {
        viewModelScope.launch {
            repository.testApi()
           val toto= 2
        }
    }
}