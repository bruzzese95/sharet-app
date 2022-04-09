package it.sapienza.macc.sharet.customdialogresource

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import it.sapienza.macc.sharet.database.SharedResourceEntity
import it.sapienza.macc.sharet.database.SharedResourceDatabaseDao
import it.sapienza.macc.sharet.database.UserAndResourceDatabaseDao
import it.sapienza.macc.sharet.database.UserAndResourceEntity
import it.sapienza.macc.sharet.network.SharedResourceApi
import it.sapienza.macc.sharet.network.SharedResourceDto
import it.sapienza.macc.sharet.network.UserAndResourceDto
import it.sapienza.macc.sharet.network.toDomainObject
import kotlinx.coroutines.*
import kotlin.random.Random

class CustomDialogResourceViewModel(
    val sharedPreferences: SharedPreferences?,
    val resourceDatabase: SharedResourceDatabaseDao,
    val userAndResourceDatabase: UserAndResourceDatabaseDao ) : ViewModel() {

    private val _navigateToSharedResource = MutableLiveData<Boolean?>()
    val navigateToSharedResource: LiveData<Boolean?>
        get() = _navigateToSharedResource

    fun doneNavigating() {
        _navigateToSharedResource.value = null
    }


    private suspend fun update(resource: SharedResourceEntity) {
        withContext(Dispatchers.IO) {
            resourceDatabase.update(resource)
        }
    }


    // In Kotlin, the return@label syntax is used for specifying which function among
    // several nested ones this statement returns from.
    // In this case, we are specifying to return from launch(),
    // not the lambda.
    fun onSetSharedResourceName(name: String) {
        GlobalScope.launch {
            val newResource = SharedResourceEntity()
            newResource.id = Random.nextInt(1, 2147483646)
            newResource.name = name

            val resourceDto = newResource.let { SharedResourceDto(it.id, it.name) }

            val resource = resourceDto!!.toDomainObject()
            val retrofit = SharedResourceApi.retrofitService.addResourceAsync(resource).await()


            val newUserAndResource = UserAndResourceEntity()
            newUserAndResource.id = Random.nextInt(1, 2147483646)
            newUserAndResource.idUser = sharedPreferences?.getString("user_uid", null)!!
            newUserAndResource.idResource = newResource.id

            val userAndResourceDto = newUserAndResource.let { UserAndResourceDto(it.id, it.idUser, it.idResource) }
            val userAndResource = userAndResourceDto!!.toDomainObject()


            val retrofitUserAndResource = SharedResourceApi.retrofitService.addUserAndResourceAsync(userAndResource).await()


            _navigateToSharedResource.postValue(true)
        }
    }

}