package com.jdagnogo.myplace.repository.api

import com.jdagnogo.myplace.model.Venue
import com.jdagnogo.myplace.repository.api.model.SearchResponse

class VenueMapper {
    fun toVenue(searchResponse: SearchResponse): List<Venue> {
        return searchResponse.data.venues.map {
            Venue(it.id, it.name, it.location.address)
        }
    }
}