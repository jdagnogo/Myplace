package com.jdagnogo.myplace.repository.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jdagnogo.myplace.model.VenueDetails
import kotlinx.coroutines.flow.Flow

@Dao
interface VenueDetailsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(venueDetails: VenueDetails)

    @Query("SELECT * FROM venue_details WHERE `id` LIKE :venueId")
    fun getVenueDetails(venueId: String): Flow<VenueDetails>
}