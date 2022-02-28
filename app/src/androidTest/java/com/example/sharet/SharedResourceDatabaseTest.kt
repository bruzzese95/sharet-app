package com.example.sharet

import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.sharet.database.SharedResource
import com.example.sharet.database.SharedResourceDatabase
import com.example.sharet.database.SharedResourceDatabaseDao
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class SharedResourceDatabaseTest {


    private lateinit var resourceDatabaseDao: SharedResourceDatabaseDao
    private lateinit var db: SharedResourceDatabase

    @Before
    fun createDb() {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        // Using an in-memory database because the information stored here disappears when the
        // process is killed.
        db = Room.inMemoryDatabaseBuilder(context, SharedResourceDatabase::class.java)
            // Allowing main thread queries, just for testing.
            .allowMainThreadQueries()
            .build()
        resourceDatabaseDao = db.sharedResourceDatabaseDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndGetResource() = runBlocking {
        val resource = SharedResource()
        resourceDatabaseDao.insert(resource)
        val lastResource = resourceDatabaseDao.getResource()
        assertEquals(lastResource?.resourceName, "")
    }

}