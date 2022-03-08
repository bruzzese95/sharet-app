package it.sapienza.macc.sharet.sharedresourcecalendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import it.sapienza.macc.sharet.database.SharedResource
import it.sapienza.macc.sharet.database.SharedResourceDatabaseDao

class SharedResourceDetailViewModel(
    private val sharedResourceKey: Long = 0L,
    dataSource: SharedResourceDatabaseDao): ViewModel() {

    /**
     * Hold a reference to SleepDatabase via its SleepDatabaseDao.
     */
    val database = dataSource
    /*
    /** Coroutine setup variables */

    /**
     * viewModelJob allows us to cancel all coroutines started by this ViewModel.
     */
    private val viewModelJob = Job()
    */
    private val resource = MediatorLiveData<SharedResource>()

    fun getResource() = resource

    init {
        resource.addSource(database.getResourceWithId(sharedResourceKey), resource::setValue)
    }

    /**
     * Variable that tells the fragment whether it should navigate to [SleepTrackerFragment].
     *
     * This is `private` because we don't want to expose the ability to set [MutableLiveData] to
     * the [Fragment]
     */
    private val _navigateToSharedResource = MutableLiveData<Boolean?>()

    /**
     * When true immediately navigate back to the [SleepTrackerFragment]
     */
    val navigateToSharedResource: LiveData<Boolean?>
        get() = _navigateToSharedResource


    /**
     * Call this immediately after navigating to [SleepTrackerFragment]
     */
    fun doneNavigating() {
        _navigateToSharedResource.value = null
    }

    fun onClose() {
        _navigateToSharedResource.value = true
    }
}