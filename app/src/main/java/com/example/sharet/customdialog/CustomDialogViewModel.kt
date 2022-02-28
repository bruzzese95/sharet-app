package com.example.sharet.customdialog

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sharet.database.SharedResource
import kotlinx.coroutines.launch

class CustomDialogViewModel: ViewModel() {

    // The current name of the resource
    private val _name = MutableLiveData<String>()
    val name: LiveData<String>
        get() = _name

    private val _navigateToSharedResource = MutableLiveData<Boolean?>()
    val navigateToSharedResource: LiveData<Boolean?>
        get() = _navigateToSharedResource

    fun doneNavigating() {
        _navigateToSharedResource.value = null
    }

    /*fun onSetSharedResource(name: String) {
        viewModelScope.launch {
            SharedResourceViewModel().resources.add(SharedResource(name))
            _navigateToSharedResource.value = true
        }
    }*/
}