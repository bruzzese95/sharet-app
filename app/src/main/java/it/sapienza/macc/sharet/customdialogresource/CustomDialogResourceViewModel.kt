package it.sapienza.macc.sharet.customdialogresource

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import it.sapienza.macc.sharet.database.SharedResourceEntity
import it.sapienza.macc.sharet.database.SharedResourceDatabaseDao
import it.sapienza.macc.sharet.domain.SharedResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CustomDialogResourceViewModel(
    val database: SharedResourceDatabaseDao) : ViewModel() {


    private val _navigateToSharedResource = MutableLiveData<Boolean?>()
    val navigateToSharedResource: LiveData<Boolean?>
        get() = _navigateToSharedResource

    fun doneNavigating() {
        _navigateToSharedResource.value = null
    }

    private suspend fun update(resource: SharedResourceEntity) {
        withContext(Dispatchers.IO) {
            database.update(resource)
        }
    }


    // In Kotlin, the return@label syntax is used for specifying which function among
    // several nested ones this statement returns from.
    // In this case, we are specifying to return from launch(),
    // not the lambda.
    fun onSetSharedResourceName(name: String) {
        viewModelScope.launch {
            val newResource = SharedResourceEntity()
            newResource.name = name
            database.insert(newResource)

            _navigateToSharedResource.value = true
        }
    }
}