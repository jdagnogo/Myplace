package com.jdagnogo.myplace.repository.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jdagnogo.myplace.model.Venue
import kotlinx.coroutines.flow.Flow

@Dao
interface VenueDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(repos: List<Venue>)

    @Query("SELECT * FROM venue")
    fun getAll(): Flow<List<Venue>>

    @Query("DELETE FROM venue")
    suspend fun clear()
}