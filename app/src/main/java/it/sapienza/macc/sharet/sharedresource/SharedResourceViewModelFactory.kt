package it.sapienza.macc.sharet.sharedresource

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import it.sapienza.macc.sharet.database.SharedResourceDatabaseDao
import it.sapienza.macc.sharet.database.UserAndResourceDatabaseDao

class SharedResourceViewModelFactory (private val sharedPreferences: SharedPreferences?,
                                      private val dataResource: SharedResourceDatabaseDao,
                                      private val dataUserAndResource: UserAndResourceDatabaseDao,
                                      private val application: Application) :  ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SharedResourceViewModel::class.java)) {
            return SharedResourceViewModel(sharedPreferences, dataResource, dataUserAndResource, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
        }
