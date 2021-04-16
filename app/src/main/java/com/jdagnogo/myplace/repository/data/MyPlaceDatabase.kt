package com.jdagnogo.myplace.repository.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jdagnogo.myplace.model.Venue
import com.jdagnogo.myplace.model.VenueDetails

@Database(
    entities = [Venue::class, VenueDetails::class],
    version = 2,
    exportSchema = false
)
abstract class MyPlaceDatabase : RoomDatabase() {
    abstract fun getVenueDao(): VenueDao
    abstract fun getVenueDetailsDao(): VenueDetailsDao

    companion object {
        private const val DB_NAME = "MyPlace.db"

        @Volatile
        private var INSTANCE: MyPlaceDatabase? = null

        fun getInstance(context: Context): MyPlaceDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                MyPlaceDatabase::class.java, DB_NAME
            ).build()
    }
}