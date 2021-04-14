package com.jdagnogo.myplace.repository

import com.jdagnogo.myplace.repository.api.VenueApi
import javax.inject.Inject

class VenueRepository @Inject constructor(
        private val api: VenueApi
) {
    suspend fun testApi() {
        try {
            val result = api.getVenue(CLIENT_ID, CLIENT_SECRET, VERSION, "Chicago, IL", RADIUS, LIMIT)
            val toto = 2
        } catch (e: Exception) {
            val toto = 3
        }

    }

    companion object {
        private const val CLIENT_SECRET = "MDYGC0JSXMROUFV3EZPARH4UJXOAKAVS3TVUZDTWENZC51SV"
        private const val CLIENT_ID = "X2E04VTFMIU4DYRWSPLN2NN1HUBQQ1R0IO4GCBY1WFVZU022"
        private const val LIMIT = "10"
        private const val RADIUS = "1000"
        private const val VERSION = "20210425"
    }
}