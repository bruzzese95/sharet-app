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
    private val _navigateToCustomDialog = MutableLiveData<SharedResource?>()
    val navigateToCustomDialog: MutableLiveData<SharedResource?>
        get() = _navigateToCustomDialog


    fun doneNavigating() {
        _navigateToCustomDialog.value = null
    }

    private val _navigateToCustomDataDialog = MutableLiveData<Long?>()
    val navigateToCustomDataDialog
        get() = _navigateToCustomDataDialog

    fun onAddButtonClicked(id: Long) {
        _navigateToCustomDataDialog.value = id
    }

    fun onCustomDataDialogNavigated() {
        _navigateToCustomDataDialog.value = null
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
        if (resource?.resourceName == "") {
            resource = null
        }
        return resource
    }

    private suspend fun update(resource: SharedResource) {
        withContext(Dispatchers.IO) {
            database.update(resource)
        }
    }

    private suspend fun insert(resource: SharedResource) {
        withContext(Dispatchers.IO) {
            database.insert(resource)
        }
    }


    fun onAddResourceButton() {
        viewModelScope.launch {
            val newResource = SharedResource()

            insert(newResource)

            resource.value = getSharedResourceFromDatabase()

            _navigateToCustomDialog.value = newResource
        }
    }
}