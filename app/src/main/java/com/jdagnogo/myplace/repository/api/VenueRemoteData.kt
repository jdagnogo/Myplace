package com.jdagnogo.myplace.repository.api

import com.jdagnogo.myplace.model.Resource
import com.jdagnogo.myplace.model.Venue
import javax.inject.Inject

class VenueRemoteData @Inject constructor(
    private val api: VenueApi,
    private val mapper: VenueMapper
) {
    suspend fun getVenues(): Resource<List<Venue>> {
        return try {
            val result = api.getVenue(CLIENT_ID, CLIENT_SECRET, VERSION, "paris", RADIUS, LIMIT)
            Resource.success(mapper.toVenue(result))
        } catch (e: Exception) {
            Resource.error(e.message ?: "", emptyList())
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