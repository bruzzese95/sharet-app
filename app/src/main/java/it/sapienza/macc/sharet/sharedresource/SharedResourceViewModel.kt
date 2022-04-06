package it.sapienza.macc.sharet.sharedresource

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.*
import it.sapienza.macc.sharet.database.SharedResourceDatabaseDao
import it.sapienza.macc.sharet.database.SharedResourceEntity
import it.sapienza.macc.sharet.repository.SharedResourceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * ViewModel for SharedResourceFragment
 */
class SharedResourceViewModel(
    val sharedPreferences: SharedPreferences?,
    val resourceDatabase: SharedResourceDatabaseDao, application: Application) : AndroidViewModel(application) {

    private val sharedResourceRepository = SharedResourceRepository(resourceDatabase)



    init {
        viewModelScope.launch {
            resourceDatabase.clear()
            delay(200)
            val user_uid = sharedPreferences?.getString("user_uid", null)
            if(user_uid != null)    sharedResourceRepository.refreshSharedResourceList(user_uid) }
    }
    val sharedResourcesList = sharedResourceRepository.sharedResourceList






    private val _navigateToCustomDialogAddUser = MutableLiveData<Boolean>()
    val navigateToCustomDialogAddUser: LiveData<Boolean>
        get() = _navigateToCustomDialogAddUser


    fun onAddUserButtonClicked() {
        viewModelScope.launch {

            _navigateToCustomDialogAddUser.value = true
        }
    }

    fun onCustomDialogAddUserNavigated() {
        _navigateToCustomDialogAddUser.value = null
    }






    //Variable that tells the Fragment to navigate to a specific [SharedResource]
    private val _navigateToCustomDialogResource = MutableLiveData<Boolean>()
    val navigateToCustomDialogResource: LiveData<Boolean>
        get() = _navigateToCustomDialogResource


    fun onAddResourceButtonClicked() {
        viewModelScope.launch {

            _navigateToCustomDialogResource.value = true
        }
    }

    fun onCustomDialogResourceNavigated() {
        _navigateToCustomDialogResource.value = null
    }






    private val _navigateToSharedResourceCalendar = MutableLiveData<Int>()
    val navigateToSharedResourceCalendar
        get() = _navigateToSharedResourceCalendar

    fun onSharedResourceButtonClicked(id: Int) {
        _navigateToSharedResourceCalendar.value = id
    }

    fun onSharedResourceCalendarNavigated() {
        _navigateToSharedResourceCalendar.value = null
    }



    /**
     *  Handling the case of the stopped app or forgotten recording,
     *  the start and end times will be the same.j
     *
     *  If the start time and end time are not the same, then we do not have an unfinished
     *  recording.
     */


    private suspend fun getSharedResourceFromDatabase(): SharedResourceEntity? {
        var resource = resourceDatabase.getResource()
        if (!resource?.name.equals("not_initialized")) {
            resource = null
        }
        return resource
    }


    private suspend fun insert(resource: SharedResourceEntity) {
        withContext(Dispatchers.IO) {
            resourceDatabase.insert(resource)
        }
    }

    private suspend fun clear() {
        withContext(Dispatchers.IO) {
            resourceDatabase.clear()
        }
    }

    private suspend fun clearWithId(key: Int) {
        withContext(Dispatchers.IO) {
            resourceDatabase.clearWithId(key)
        }
    }

    fun onClearWithId(key: Int) {
        viewModelScope.launch {
            // Clear the database table.
            clearWithId(key)
        }
    }


}