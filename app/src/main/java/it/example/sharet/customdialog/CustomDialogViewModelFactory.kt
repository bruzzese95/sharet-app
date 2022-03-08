package it.example.sharet.customdialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import it.example.sharet.database.SharedResourceDatabaseDao

class CustomDialogViewModelFactory(
    private val dataSource: SharedResourceDatabaseDao) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CustomDialogViewModel::class.java)) {
            return CustomDialogViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}