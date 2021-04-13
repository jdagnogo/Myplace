package com.jdagnogo.myplace.repository.api

import com.jdagnogo.myplace.repository.api.model.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface VenueApi {
    @GET(GET_VENUES)
    suspend fun getVenue(
        @Query(CLIENT_ID) seed: String,
        @Query(CLIENT_SECRET) page: Int,
        @Query("page") itemsPerPage: Int
    ): SearchResponse

    companion object {
        const val BASE_URL = "https://api.foursquare.com/v2/"
        private const val GET_VENUES = "/venues/search"
        private const val CLIENT_SECRET = "client_secret"
        private const val CLIENT_ID = "client_id"
    }
}