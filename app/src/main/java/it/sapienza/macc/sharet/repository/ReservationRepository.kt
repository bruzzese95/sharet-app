package it.sapienza.macc.sharet.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import it.sapienza.macc.sharet.database.ReservationDatabaseDao
import it.sapienza.macc.sharet.database.SharedResourceDatabaseDao
import it.sapienza.macc.sharet.database.toDomainModel
import it.sapienza.macc.sharet.domain.Reservation
import it.sapienza.macc.sharet.domain.SharedResource
import it.sapienza.macc.sharet.network.SharedResourceApi
import it.sapienza.macc.sharet.network.toDbObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ReservationRepository(private val dao: ReservationDatabaseDao) {
    val reservationList: LiveData<List<Reservation>> = Transformations.map(dao.getAll()) { reservationEntityList ->
        reservationEntityList.toDomainModel()
    }

    /*
    * To refresh the offline cache.
    */
    suspend fun refreshAllSharedResourceList() {
        withContext(Dispatchers.IO) {
            /*val sharedResourceDtoList = SharedResourceApi.retrofitService.getSharedResourcesAsync().await()
            dao.insertAll(*sharedResourceDtoList.toDbObject())*/ //Note the asterisk * is the spread operator. It allows you to pass in an array to a function that expects varargs.
        }
    }


    suspend fun refreshReservationList(idOwnerToDB: String, idResourceToDB: Int, dateToDB: String) {
        withContext(Dispatchers.IO) {
            val reservationDtoList = SharedResourceApi.retrofitService.getReservation(idResourceToDB, dateToDB).await()
            dao.insertAll(*reservationDtoList.toDbObject())

        }
    }
}