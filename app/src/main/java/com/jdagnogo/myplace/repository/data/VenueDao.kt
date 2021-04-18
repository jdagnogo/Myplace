package com.jdagnogo.myplace.repository.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jdagnogo.myplace.model.Venue
import kotlinx.coroutines.flow.Flow

@Dao
interface VenueDao {
    /**
     * this method will insert the data in the Room database
     * Important: in case that the data have the same id, it will replace the old one
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(venues: List<Venue>)

    /**
     * this method will retrieve the data in the Room database
     * It will return only the data with the correct query
     */
    @Query("SELECT * FROM venue WHERE `query` LIKE :queryString")
    fun getAll(queryString: String): Flow<List<Venue>>
}