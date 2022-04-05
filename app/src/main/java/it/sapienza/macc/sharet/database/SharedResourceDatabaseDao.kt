package it.sapienza.macc.sharet.database

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SharedResourceDatabaseDao {

    @Insert
    suspend fun insert(SharedResourceEntity: SharedResourceEntity)


    /**
     * When updating a row with a value already set in a column,
     * replaces the old value with the new one.
     *
     * @param SharedResourceEntity new value to write
     */
    @Update
    suspend fun update(SharedResourceEntity: SharedResourceEntity)

    /**
     * Selects and returns the row that matches the id of the resource, which is our key.
     *
     * @param key resource id to match
     */
    @Query("SELECT * from shared_resource_table WHERE id = :key")
    suspend fun get(key: Int): SharedResourceEntity?

    /**
     * Deletes all values from the table.
     *
     * This does not delete the table, only its contents.
     */
    @Query("DELETE FROM shared_resource_table")
    suspend fun clear()

    @Query("DELETE FROM shared_resource_table WHERE id = :key")
    suspend fun clearWithId(key: Int)


    /**
     * Selects and returns all rows in the table
     */
    @Query("SELECT * FROM shared_resource_table ORDER BY id DESC")
    fun getAllResources(): LiveData<List<SharedResourceEntity>>

    /**
     * Selects and returns the latest resource.
     */
    @Query("SELECT * FROM shared_resource_table ORDER BY id DESC LIMIT 1")
    suspend fun getResource(): SharedResourceEntity?

    /**
     * Selects and returns the resource with given name.
     */
    @Query("SELECT * from shared_resource_table WHERE id = :key")
    fun getResourceWithId(key: Int): LiveData<SharedResourceEntity>



    /**
     * Implementing offline cache
     * */
    @Query("SELECT * FROM shared_resource_table")
    fun getAll(): LiveData<List<SharedResourceEntity>>
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg sharedResourceEntities: SharedResourceEntity)
}