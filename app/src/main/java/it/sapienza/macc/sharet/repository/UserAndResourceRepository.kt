package it.sapienza.macc.sharet.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import it.sapienza.macc.sharet.database.SharedResourceDatabaseDao
import it.sapienza.macc.sharet.database.UserAndResourceDatabaseDao
import it.sapienza.macc.sharet.database.toDomainModel
import it.sapienza.macc.sharet.domain.SharedResource
import it.sapienza.macc.sharet.domain.UserAndResource
import it.sapienza.macc.sharet.network.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class UserAndResourceRepository(private val dao: UserAndResourceDatabaseDao, private val daoResource: SharedResourceDatabaseDao) {
    val userAndResourceList: LiveData<List<UserAndResource>> = Transformations.map(dao.getAll()) { userAndResourceEntityList ->
        userAndResourceEntityList.toDomainModel()

    }
    val sharedResourceList: LiveData<List<SharedResource>> = Transformations.map(daoResource.getAll()) { sharedResourceEntityList ->
        sharedResourceEntityList.toDomainModel()
    }


    suspend fun refreshUserAndResourceList(user_id: String) {
        withContext(Dispatchers.IO) {
            val userAndResourceDtoList = SharedResourceApi.retrofitService.getUserAndResourcesAsync(user_id).await()
            dao.insertAll(*userAndResourceDtoList.toDbObject())
            getResourceInfo(userAndResourceDtoList.userAndResourceDtoList)

        }
    }


    suspend fun getResourceInfo(userAndResourceDtoList: List<UserAndResourceDto>) {

        val listOfSharedResource = mutableListOf<SharedResourceDto>()
        for(resource in userAndResourceDtoList) {
            val resourceDB = SharedResourceApi.retrofitService.getResourceAsync(resource.idResource).await()
            listOfSharedResource.add(resourceDB)
        }

        var sharedResourceDtoContainer = SharedResourceDtoContainer(listOfSharedResource)

        daoResource.insertAll(*sharedResourceDtoContainer.toDbObject())

    }
}