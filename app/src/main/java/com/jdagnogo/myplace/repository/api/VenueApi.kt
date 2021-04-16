package com.jdagnogo.myplace.repository.api

import com.jdagnogo.myplace.repository.api.model.SearchApiResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface VenueApi {
    @GET(GET_VENUES)
    suspend fun getVenue(
            @Query(CLIENT_ID) clientID: String,
            @Query(CLIENT_SECRET) clientSecret: String,
            @Query(VERSION) version: String,
            @Query(NEAR) near: String,
            @Query(RADIUS) radius: String,
            @Query(LIMIT) limit: String
    ): SearchApiResponse

    companion object {
        const val BASE_URL = "https://api.foursquare.com"
        private const val GET_VENUES = "/v2/venues/search"
        const val CLIENT_SECRET = "client_secret"
        const val CLIENT_ID = "client_id"
        private const val RADIUS = "radius"
        private const val LIMIT = "limit"
        private const val NEAR = "near"
        const val VERSION = "v"
    }
}