package it.sapienza.macc.sharet.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(

    @PrimaryKey(autoGenerate = true)
    var userId: Long = 0L,

    @ColumnInfo(name = "user_name")
    var userName: String = "not_initialized",

    @ColumnInfo(name = "user_email")
    var email: String = "not_initialized"
)