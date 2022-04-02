package it.sapienza.macc.sharet.database

import androidx.room.Dao
import androidx.room.Insert

@Dao
interface UserDatabaseDao {

    @Insert
    suspend fun insert(user: User)
}