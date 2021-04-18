package com.jdagnogo.myplace.repository.api

import androidx.annotation.VisibleForTesting
import com.jdagnogo.myplace.model.Venue
import com.jdagnogo.myplace.model.VenueDetails
import com.jdagnogo.myplace.repository.api.model.PhotoResponse
import com.jdagnogo.myplace.repository.api.model.SearchApiResponse
import com.jdagnogo.myplace.repository.api.model.VenueDetailsApiResponse

class VenueMapper {
    fun toVenue(searchResponse: SearchApiResponse, query: String): List<Venue> {
        return searchResponse.data.venues.map {
            Venue(it.id, it.name, query,
                    it.location.address.takeIf { address -> address.isNotEmpty() } ?: DEFAULT_ADDRESS
            )
        }
    }

    fun toVenueDetails(response: VenueDetailsApiResponse): VenueDetails {
        with(response.data.venueDetails) {
            return VenueDetails(id, name, contact.formattedPhone,
                    contact.facebookName, contact.twitter, location.address,
                    toPhoto(photos), rating, description)
        }
    }

    /**
     * to retrive the photo we need the suffix and the prefix
     */
    private fun toPhoto(photos: PhotoResponse): String {
        val toto = photos.groups.firstOrNull()?.items?.firstOrNull()
        return "${toto?.prefix?:""}${toto?.suffix?:""}"
    }

    companion object {
        @VisibleForTesting
        const val DEFAULT_ADDRESS = "---"
    }
}