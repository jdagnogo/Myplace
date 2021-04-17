package com.jdagnogo.myplace.repository.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jdagnogo.myplace.model.VenueDetails
import junit.framework.Assert.assertNull
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class VenueDetailsDaoTest {
    private lateinit var sut: VenueDetailsDao
    private lateinit var db: MyPlaceDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, MyPlaceDatabase::class.java
        ).build()
        sut = db.getVenueDetailsDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertOneShouldCreateOnly() = runBlocking {
        val venues = VenueDetails(id = FAKE_NAME)
        sut.insert(venues)

        val result = sut.getVenueDetails(FAKE_NAME).first()

        assertTrue("", result == venues)
    }

    @Test
    @Throws(Exception::class)
    fun gettingTheWrongKeyWillReturnNull() = runBlocking {
        val venues = VenueDetails(id = FAKE_NAME)
        sut.insert(venues)

        val result = sut.getVenueDetails(FAKE_NAME2).first()

        assertNull( result)
    }

    companion object {
        const val FAKE_NAME = "FAKE_NAME"
        const val FAKE_NAME2 = "FAKE_NAME2"
    }
}