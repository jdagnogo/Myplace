package com.jdagnogo.myplace.ui

import com.jdagnogo.myplace.model.Venue

interface VenueListView {
    fun onNewData(venues : List<Venue>?)

    fun displayLoader()

    fun displayNoValue()
}