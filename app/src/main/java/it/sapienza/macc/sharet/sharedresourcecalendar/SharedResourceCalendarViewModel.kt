package it.sapienza.macc.sharet.sharedresourcecalendar

import androidx.lifecycle.*
import it.sapienza.macc.sharet.database.ReservationDatabaseDao
import it.sapienza.macc.sharet.database.ReservationEntity
import it.sapienza.macc.sharet.network.ReservationDto
import it.sapienza.macc.sharet.network.SharedResourceApi
import it.sapienza.macc.sharet.network.toDomainObject
import kotlinx.coroutines.*
import kotlin.random.Random

class SharedResourceCalendarViewModel(
    val reservationDatabase: ReservationDatabaseDao): ViewModel() {


    // The internal MutableLiveData String that stores the most recent response
    private val _response = MutableLiveData<String>()

    // The external immutable LiveData for the response String
    val response: LiveData<String>
        get() = _response

    //Create a coroutine Job and a CoroutineScope using the main dispatcher
    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main )

/*    init {
        //resource.addSource(database.getResourceWithId(sharedResourceKey), resource::setValue)
        getSharedResourceProperties()
    }

    private fun getSharedResourceProperties() {
        coroutineScope.launch {
            var getPropertiesDeferred = SharedResourceApi.retrofitService.getProperties()
            try {
                var listResult = getPropertiesDeferred.await()
                _response.value = "Name: ${listResult.name} - Id: ${listResult.id}"
            } catch (e: Exception) {
                _response.value = "Failure: ${e.message}"
            }
        }
    }*/


    fun insertReservationRoom(idResource: Int, idOwner: String, name: String, date: String, startTime: String, endTime: String) {
        viewModelScope.launch {
            val reservation = ReservationEntity()
            reservation.id = Random.nextInt(1, 2147483646)
            reservation.idResource = idResource
            reservation.idOwner = idOwner
            reservation.name = name
            reservation.date = date
            reservation.startTime = startTime
            reservation.endTime = endTime

            reservationDatabase.insert(reservation)


            val reservationDto = reservation.let { ReservationDto(it.id, it.idResource, it.idOwner, it.name, it.date, it.startTime, it.endTime) }

            val reservationDB = reservationDto!!.toDomainObject()
            val retrofit = SharedResourceApi.retrofitService.addReservationAsync(reservationDB)
        }
    }



    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}