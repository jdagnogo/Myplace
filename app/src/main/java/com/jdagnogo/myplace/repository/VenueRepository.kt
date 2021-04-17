package com.jdagnogo.myplace.repository

import com.jdagnogo.myplace.model.Resource
import com.jdagnogo.myplace.model.Venue
import com.jdagnogo.myplace.model.VenueDetails
import com.jdagnogo.myplace.repository.api.VenueRemoteData
import com.jdagnogo.myplace.repository.data.VenueDao
import com.jdagnogo.myplace.repository.data.VenueDetailsDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class VenueRepository @Inject constructor(
    private val remoteData: VenueRemoteData,
    private val dao: VenueDao,
    private val venueDetailsDao: VenueDetailsDao
) {

    fun getData(query: String): Flow<Resource<List<Venue>>> {
        return resourceAsFlow(
            fetchFromLocal = { dao.getAll(query) },
            networkCall = { remoteData.getVenues(query) },
            saveCallResource = { venues -> dao.insertAll(venues) })
    }

    fun getVenueDetails(id : String) :Flow<Resource<VenueDetails?>>{
        return resourceAsFlow(
                fetchFromLocal = { venueDetailsDao.getVenueDetails(id) },
                networkCall = { remoteData.getVenueDetails(id) },
                saveCallResource = { venues -> venueDetailsDao.insert(venues) })
    }
}