package it.sapienza.macc.sharet.customdialogresource

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import it.sapienza.macc.sharet.database.SharedResourceDatabaseDao

class CustomDialogResourceViewModelFactory(
    private val sharedPreferences: SharedPreferences?,
    private val dataSource: SharedResourceDatabaseDao) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CustomDialogResourceViewModel::class.java)) {
            return CustomDialogResourceViewModel(sharedPreferences,dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}