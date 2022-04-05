package it.sapienza.macc.sharet.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDatabaseDao {

    @Insert
    suspend fun insert(userEntity: UserEntity)


    /**
     * Implementing offline cache
     * */
    @Query("SELECT * FROM user_table")
    fun getAll(): LiveData<List<UserEntity>>
}