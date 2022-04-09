package it.sapienza.macc.sharet.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import it.sapienza.macc.sharet.database.UserDatabaseDao
import it.sapienza.macc.sharet.database.toDomainModel
import it.sapienza.macc.sharet.domain.User
import it.sapienza.macc.sharet.network.SharedResourceApi
import it.sapienza.macc.sharet.network.UserDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserRepository(private val dao: UserDatabaseDao) {
    val userList: LiveData<List<User>> = Transformations.map(dao.getAll()) { userEntityList ->
        userEntityList.toDomainModel()
    }


    suspend fun getUserWithIdUser(idUser: Int)  {
        withContext(Dispatchers.IO) {
            val userDto = SharedResourceApi.retrofitService.getUserWithIdUserAsync(idUser).await()
        }
    }
}