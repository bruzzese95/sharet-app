package it.sapienza.macc.sharet.customdialoguser

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import it.sapienza.macc.sharet.database.UserAndResourceEntity
import it.sapienza.macc.sharet.database.UserEntity
import it.sapienza.macc.sharet.database.UserDatabaseDao
import it.sapienza.macc.sharet.network.SharedResourceApi
import it.sapienza.macc.sharet.network.UserAndResourceDto
import it.sapienza.macc.sharet.network.toDomainObject
import it.sapienza.macc.sharet.repository.SharedResourceRepository
import it.sapienza.macc.sharet.repository.UserRepository
import kotlinx.coroutines.*
import java.lang.Exception
import kotlin.random.Random

class CustomDialogUserViewModel(
    val database: UserDatabaseDao
) : ViewModel() {


    private val userRepository = UserRepository(database)


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
    fun onAddedUsername(idUser: Int, idResource: Int) {
        GlobalScope.launch {
                val userDto = SharedResourceApi.retrofitService.getUserWithIdUserAsync(idUser).await()
                val user = userDto!!.idToken

                val newUserAndResource = UserAndResourceEntity()
                newUserAndResource.id = Random.nextInt(1, 2147483646)
                newUserAndResource.idUser = user
                newUserAndResource.idResource = idResource

                val userAndResourceDto = newUserAndResource.let { UserAndResourceDto(it.id, it.idUser, it.idResource) }
                val userAndResource = userAndResourceDto!!.toDomainObject()


                val retrofitUserAndResource = SharedResourceApi.retrofitService.addUserAndResourceAsync(userAndResource).await()

        }
    }


}