package com.example.sharet.sharedresource

import android.app.Application
import androidx.lifecycle.*
import com.example.sharet.database.SharedResource
import com.example.sharet.database.SharedResourceDatabaseDao
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


    //Variable that tells the Fragment to navigate to a specific [SharedResource]
    private val _navigateToCustomDialog = MutableLiveData<Boolean>()
    val navigateToCustomDialog: LiveData<Boolean>
        get() = _navigateToCustomDialog


    fun doneNavigating() {
        _navigateToCustomDialog.value = null
    }

    private val _navigateToSharedResourceDetail = MutableLiveData<Long>()
    val navigateToSharedResourceDetail
        get() = _navigateToSharedResourceDetail

    fun onAddButtonClicked(id: Long) {
        _navigateToSharedResourceDetail.value = id
    }

    fun onSharedResourceDetailNavigated() {
        _navigateToSharedResourceDetail.value = null
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

    fun onAddResourceButton() {
        viewModelScope.launch {

            _navigateToCustomDialog.value = true
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
}