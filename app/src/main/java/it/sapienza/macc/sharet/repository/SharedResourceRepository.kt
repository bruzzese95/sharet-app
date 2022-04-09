package it.sapienza.macc.sharet.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import it.sapienza.macc.sharet.database.SharedResourceDatabaseDao
import it.sapienza.macc.sharet.database.toDomainModel
import it.sapienza.macc.sharet.domain.SharedResource
import it.sapienza.macc.sharet.network.SharedResourceApi
import it.sapienza.macc.sharet.network.toDbObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class SharedResourceRepository(private val dao: SharedResourceDatabaseDao) {
    val sharedResourceList: LiveData<List<SharedResource>> = Transformations.map(dao.getAll()) { sharedResourceEntityList ->
        sharedResourceEntityList.toDomainModel()
    }

    /*
    * To refresh the offline cache.
    */
    suspend fun refreshAllSharedResourceList() {
        withContext(Dispatchers.IO) {
            val sharedResourceDtoList = SharedResourceApi.retrofitService.getSharedResourcesAsync().await()
            dao.insertAll(*sharedResourceDtoList.toDbObject()) //Note the asterisk * is the spread operator. It allows you to pass in an array to a function that expects varargs.
        }
    }


    suspend fun refreshSharedResourceList(user_id: String) {
        withContext(Dispatchers.IO) {
            val sharedResourceDtoList = SharedResourceApi.retrofitService.getSharedResourcesAsync(user_id).await()
            dao.insertAll(*sharedResourceDtoList.toDbObject())

        }
    }
}