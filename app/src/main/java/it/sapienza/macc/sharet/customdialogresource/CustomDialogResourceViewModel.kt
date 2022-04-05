package it.sapienza.macc.sharet.customdialogresource

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import it.sapienza.macc.sharet.database.SharedResourceEntity
import it.sapienza.macc.sharet.database.SharedResourceDatabaseDao
import it.sapienza.macc.sharet.network.SharedResourceApi
import it.sapienza.macc.sharet.network.SharedResourceDto
import it.sapienza.macc.sharet.network.toDomainObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.random.Random

class CustomDialogResourceViewModel(
    val sharedPreferences: SharedPreferences?,
    val resourceDatabase: SharedResourceDatabaseDao) : ViewModel() {

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
        viewModelScope.launch {
            val newResource = SharedResourceEntity()
            newResource.id = Random.nextInt(1, 2147483646)
            newResource.name = name
            newResource.owner_id = sharedPreferences?.getString("user_uid", null)!!
            /*resourceDatabase.insert(newResource)

            val values = resourceDatabase.getAll()

            val resourceRoom = resourceDatabase.getResource()

            Log.i("ResourceRoom id: ", resourceRoom?.id.toString())*/

            val resourceDto = newResource.let { SharedResourceDto(it.id, it.name, it.owner_id) }

            val resource = resourceDto!!.toDomainObject()
            val retrofit = SharedResourceApi.retrofitService.addResource(resource)
            _navigateToSharedResource.value = true
        }
    }
}