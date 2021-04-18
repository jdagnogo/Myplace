package com.jdagnogo.myplace.repository.api

import com.jdagnogo.myplace.repository.api.VenueApi.Companion.CLIENT_ID
import com.jdagnogo.myplace.repository.api.VenueApi.Companion.CLIENT_SECRET
import com.jdagnogo.myplace.repository.api.VenueApi.Companion.VERSION
import com.jdagnogo.myplace.repository.api.model.VenueDetailsApiResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface VenueDetailsApi {
    /**
     * this method will retrieve all information for a venue from the api :
     * Doc : https://developer.foursquare.com/docs/api/venues/details
     *
     * user_id: the id of the venue we want more details
     *
     * return an VenueDetailsApiResponse. We can map it later to have the venueDetails.
     *
     */
    @GET(GET_VENUES_DETAILS)
    suspend fun getVenueDetails(
            @Path(value = "user_id") venueId: String,
            @Query(CLIENT_ID) clientID: String,
            @Query(CLIENT_SECRET) clientSecret: String,
            @Query(VERSION) version: String
            ): VenueDetailsApiResponse

    companion object {
        private const val GET_VENUES_DETAILS = "/v2/venues/{user_id}"
    }
}