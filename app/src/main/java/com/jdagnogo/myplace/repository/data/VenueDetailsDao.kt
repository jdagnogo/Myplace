package com.jdagnogo.myplace.repository.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jdagnogo.myplace.model.VenueDetails
import kotlinx.coroutines.flow.Flow

@Dao
interface VenueDetailsDao {
    /**
     * this method will insert the data in the Room database
     * Important: in case that the data have the same id, it will replace the old one
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(venueDetails: VenueDetails)

    /**
     * this method will retrieve the data in the Room database
     * It will return only the data with the correct id
     */
    @Query("SELECT * FROM venue_details WHERE `id` LIKE :venueId")
    fun getVenueDetails(venueId: String): Flow<VenueDetails?>
}