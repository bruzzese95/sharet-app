package it.sapienza.macc.sharet.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserAndResourceDatabaseDao {

    @Insert
    suspend fun insert(userAndResourceEntity: UserAndResourceEntity)


    /**
     * Implementing offline cache
     * */
    @Query("SELECT * FROM user_and_resource_table")
    fun getAll(): LiveData<List<UserAndResourceEntity>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg userAndResourceEntities: UserAndResourceEntity)
}