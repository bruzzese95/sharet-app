package it.sapienza.macc.sharet.customdialoguser

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import it.sapienza.macc.sharet.customdialogresource.CustomDialogResourceViewModel
import it.sapienza.macc.sharet.database.SharedResourceDatabaseDao
import it.sapienza.macc.sharet.database.UserDatabaseDao

class CustomDialogUserViewModelFactory(
    private val dataSource: UserDatabaseDao
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CustomDialogUserViewModel::class.java)) {
            return CustomDialogUserViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}