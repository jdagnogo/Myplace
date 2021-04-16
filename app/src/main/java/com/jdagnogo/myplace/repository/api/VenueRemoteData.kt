package com.jdagnogo.myplace.repository.api

import com.jdagnogo.myplace.model.Resource
import com.jdagnogo.myplace.model.Venue
import com.jdagnogo.myplace.model.VenueDetails
import javax.inject.Inject

class VenueRemoteData @Inject constructor(
        private val api: VenueApi,
        private val venueDetailsApi: VenueDetailsApi,
        private val mapper: VenueMapper
) {
    suspend fun getVenues(query: String): Resource<List<Venue>> {
        return try {
            val result = api.getVenue(CLIENT_ID, CLIENT_SECRET, VERSION, query, RADIUS, LIMIT)
            Resource.success(mapper.toVenue(result, query))
        } catch (e: Exception) {
            Resource.error(e.message ?: "", emptyList())
        }
    }

    suspend fun getVenueDetails(id: String): Resource<VenueDetails> {
        return try {
            val result = venueDetailsApi.getVenueDetails(id, CLIENT_ID, CLIENT_SECRET, VERSION)
            Resource.success(mapper.toVenueDetails(result))
        } catch (e: Exception) {
            Resource.error(e.message ?: "", VenueDetails())
        }
    }

    companion object {
        const val CLIENT_SECRET = "MDYGC0JSXMROUFV3EZPARH4UJXOAKAVS3TVUZDTWENZC51SV"
        val CLIENT_ID = "X2E04VTFMIU4DYRWSPLN2NN1HUBQQ1R0IO4GCBY1WFVZU022"
        const val LIMIT = "10"
        val RADIUS = "1000"
        const val VERSION = "20210425"
    }
}