package it.sapienza.macc.sharet.sharedresource

import android.app.Application
import androidx.lifecycle.*
import it.sapienza.macc.sharet.database.SharedResourceEntity
import it.sapienza.macc.sharet.database.SharedResourceDatabaseDao
import it.sapienza.macc.sharet.repository.SharedResourceRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * ViewModel for SharedResourceFragment
 */
class SharedResourceViewModel(val database: SharedResourceDatabaseDao, application: Application) : AndroidViewModel(application) {
    private val sharedResourceRepository = SharedResourceRepository(database)
    init {
        viewModelScope.launch { sharedResourceRepository.refreshSharedResourceList() }
    }
    val sharedResourcesList = sharedResourceRepository.sharedResourceList

    //Variable that tells the Fragment to navigate to a specific [SharedResource]
    private val _navigateToCustomDialog = MutableLiveData<Boolean>()
    val navigateToCustomDialog: LiveData<Boolean>
        get() = _navigateToCustomDialog
    fun doneNavigating() {
        _navigateToCustomDialog.value = false
    }

    private val _navigateToSharedResourceDetail = MutableLiveData<Long>()
    val navigateToSharedResourceDetail
        get() = _navigateToSharedResourceDetail

    fun onAddButtonClicked(id: Long) {
        _navigateToSharedResourceDetail.value = id
    }

    fun onSharedResourceDetailNavigated() {
        _navigateToSharedResourceDetail.value = 0
    }


    private suspend fun insert(resourceEntity: SharedResourceEntity) {
        withContext(Dispatchers.IO) {
            database.insert(resourceEntity)
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
        }
    }
}