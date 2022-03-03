package com.example.sharet.sharedresourcecalendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.sharet.database.SharedResourceDatabaseDao

class SharedResourceCalendarViewModelFactory(
        private val sharedResourceKey: Long,
        private val dataSource: SharedResourceDatabaseDao): ViewModelProvider.Factory{
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SharedResourceDetailViewModel::class.java)) {
            return SharedResourceDetailViewModel(sharedResourceKey, dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}