package it.sapienza.macc.sharet.customdialoguser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import it.sapienza.macc.sharet.database.SharedResource
import it.sapienza.macc.sharet.database.SharedResourceDatabaseDao
import it.sapienza.macc.sharet.database.User
import it.sapienza.macc.sharet.database.UserDatabaseDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CustomDialogUserViewModel(
    val database: UserDatabaseDao
) : ViewModel() {


    private val _navigateToSharedResource = MutableLiveData<Boolean?>()
    val navigateToSharedResource: LiveData<Boolean?>
        get() = _navigateToSharedResource

    fun doneNavigating() {
        _navigateToSharedResource.value = null
    }


    // In Kotlin, the return@label syntax is used for specifying which function among
    // several nested ones this statement returns from.
    // In this case, we are specifying to return from launch(),
    // not the lambda.
    fun onAddedUsername(email: String) {
        viewModelScope.launch {
            val newUser = User()
            newUser.email = email
            database.insert(newUser)

            _navigateToSharedResource.value = true
        }
    }
}