package it.sapienza.macc.sharet.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ReservationDatabaseDao {

    @Insert
    suspend fun insert(reservationEntity: ReservationEntity)

    /**
     * Implementing offline cache
     * */
    @Query("SELECT * FROM reservation_table")
    fun getAll(): LiveData<List<ReservationEntity>>
}