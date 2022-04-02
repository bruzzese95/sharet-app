package it.sapienza.macc.sharet.sharedresource

import android.app.Application
import androidx.lifecycle.*
import it.sapienza.macc.sharet.database.SharedResource
import it.sapienza.macc.sharet.database.SharedResourceDatabaseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * ViewModel for SharedResourceFragment
 */
class SharedResourceViewModel(
        val database: SharedResourceDatabaseDao, application: Application) : AndroidViewModel(application) {


    private var resource = MutableLiveData<SharedResource?>()

    val resources = database.getAllResources()





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






    private val _navigateToSharedResourceCalendar = MutableLiveData<Long>()
    val navigateToSharedResourceCalendar
        get() = _navigateToSharedResourceCalendar

    fun onSharedResourceButtonClicked(id: Long) {
        _navigateToSharedResourceCalendar.value = id
    }

    fun onSharedResourceCalendarNavigated() {
        _navigateToSharedResourceCalendar.value = null
    }







    init {
        initializeSharedResource()
    }

    private fun initializeSharedResource() {
        viewModelScope.launch {
            resource.value = getSharedResourceFromDatabase()
        }
    }

    /**
     *  Handling the case of the stopped app or forgotten recording,
     *  the start and end times will be the same.j
     *
     *  If the start time and end time are not the same, then we do not have an unfinished
     *  recording.
     */


    private suspend fun getSharedResourceFromDatabase(): SharedResource? {
        var resource = database.getResource()
        if (!resource?.resourceName.equals("not_initialized")) {
            resource = null
        }
        return resource
    }


    private suspend fun insert(resource: SharedResource) {
        withContext(Dispatchers.IO) {
            database.insert(resource)
        }
    }

    private suspend fun clear() {
        withContext(Dispatchers.IO) {
            database.clear()
        }
    }

    private suspend fun clearWithId(key: Long) {
        withContext(Dispatchers.IO) {
            database.clearWithId(key)
        }
    }


    /**
     * Executes when the CLEAR button is clicked.
     */
    fun onClear() {
        viewModelScope.launch {
            // Clear the database table.
            clear()

            // And clear tonight since it's no longer in the database
            resource.value = null
        }
    }

    fun onClearWithId(key: Long) {
        viewModelScope.launch {
            // Clear the database table.
            clearWithId(key)
        }
    }


}