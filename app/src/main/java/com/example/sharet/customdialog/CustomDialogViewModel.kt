package com.example.sharet.customdialog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sharet.database.SharedResource
import com.example.sharet.database.SharedResourceDatabaseDao
import kotlinx.coroutines.launch

class CustomDialogViewModel(
    private val sharedResourceKey: Long = 0L,
    val database: SharedResourceDatabaseDao) : ViewModel() {

    private val _navigateToSharedResource = MutableLiveData<Boolean?>()
    val navigateToSharedResource: LiveData<Boolean?>
        get() = _navigateToSharedResource

    fun doneNavigating() {
        _navigateToSharedResource.value = null
    }

    fun onSetSharedResourceName(name: String) {
        viewModelScope.launch {
            val resource = database.get(sharedResourceKey) ?: return@launch
            resource.resourceName = name
            database.update(resource)

            _navigateToSharedResource.value = true
        }
    }
}