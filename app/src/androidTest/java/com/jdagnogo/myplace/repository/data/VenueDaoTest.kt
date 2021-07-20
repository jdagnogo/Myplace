package com.jdagnogo.myplace.repository.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jdagnogo.myplace.model.Venue
import junit.framework.Assert
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith
import java.io.IOException


@RunWith(AndroidJUnit4::class)
class VenueDaoTest {
    private lateinit var sut: VenueDao
    private lateinit var db: MyPlaceDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, MyPlaceDatabase::class.java
        ).build()
        sut = db.getVenueDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertOneShouldCreateOnly() = runBlocking {
        val venues = listOf(Venue(query = FAKE_NAME))

        sut.insertAll(venues)
        val result = sut.getAll(FAKE_NAME).first()

        assertTrue("",result == venues)
    }

    @Test
    @Throws(Exception::class)
    fun gettingTheWrongKeyWillReturnEmpty() = runBlocking {
        val venues = listOf(Venue(query = FAKE_NAME))
        sut.insertAll(venues)

        val result = sut.getAll(FAKE_NAME2).first()

        assertTrue("",result.isEmpty())
    }

    companion object {
        const val FAKE_NAME = "FAKE_NAME"
        const val FAKE_NAME2 = "FAKE_NAME2"
    }
}