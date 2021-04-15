package com.jdagnogo.myplace.repository.api

import com.jdagnogo.myplace.model.Venue
import com.jdagnogo.myplace.repository.api.model.SearchResponse

class VenueMapper {
    fun toVenue(searchResponse: SearchResponse, query: String): List<Venue> {
        return searchResponse.data.venues.map {
            Venue(it.id, it.name, query,
                it.location.address.takeIf { it.isNotEmpty() }?: DEFAULT_ADDRESS)
        }
    }
    companion object{
        private const val DEFAULT_ADDRESS = "---"
    }
}