package it.sapienza.macc.sharet.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import it.sapienza.macc.sharet.database.UserDatabaseDao
import it.sapienza.macc.sharet.database.toDomainModel
import it.sapienza.macc.sharet.domain.User
import it.sapienza.macc.sharet.network.SharedResourceApi
import it.sapienza.macc.sharet.network.toDbObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(private val dao: UserDatabaseDao) {
    val userList: LiveData<List<User>> = Transformations.map(dao.getAll()) { userEntityList ->
        userEntityList.toDomainModel()
    }

    /*
    * To refresh the offline cache.
    */
    suspend fun refreshSharedResourceList() {
        withContext(Dispatchers.IO) {
            val sharedResourceDtoList = SharedResourceApi.retrofitService.getSharedResourcesAsync().await()
//            dao.insertAll(*sharedResourceDtoList.toDbObject()) //Note the asterisk * is the spread operator. It allows you to pass in an array to a function that expects varargs.
        }
    }
}