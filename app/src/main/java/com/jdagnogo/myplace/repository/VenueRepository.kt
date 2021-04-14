package com.jdagnogo.myplace.repository

import com.jdagnogo.myplace.model.Resource
import com.jdagnogo.myplace.model.Venue
import com.jdagnogo.myplace.repository.api.VenueRemoteData
import com.jdagnogo.myplace.repository.data.VenueDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class VenueRepository @Inject constructor(
    private val remoteData: VenueRemoteData,
    private val dao: VenueDao

) {

    fun getData(query: String): Flow<Resource<List<Venue>>> {
        return resourceAsFlow(
            fetchFromLocal = { dao.getAll() },
            networkCall = { remoteData.getVenues() },
            saveCallResource = { venues -> dao.insertAll(venues) })
    }
}