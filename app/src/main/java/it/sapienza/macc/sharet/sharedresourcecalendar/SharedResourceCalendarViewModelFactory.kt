package it.sapienza.macc.sharet.sharedresourcecalendar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import it.sapienza.macc.sharet.database.ReservationDatabaseDao
import it.sapienza.macc.sharet.database.SharedResourceDatabaseDao

class SharedResourceCalendarViewModelFactory(
        //private val sharedResourceKey: Long,
        private val dataSource: ReservationDatabaseDao): ViewModelProvider.Factory{
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SharedResourceCalendarViewModel::class.java)) {
            return SharedResourceCalendarViewModel(dataSource) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}