package it.sapienza.macc.sharet.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import it.sapienza.macc.sharet.domain.User

@Entity(tableName = "user_table")
data class UserEntity(

    @PrimaryKey(autoGenerate = false)
    var idToken: String = "not_initialized",

    @ColumnInfo(name = "user_name")
    var name: String = "not_initialized",

    @ColumnInfo(name = "email")
    var email: String = "not_initialized"
)


/**
 * Converts from database object to domain object
 * */
fun List<UserEntity>.toDomainModel(): List<User> {
    return map { dbInstance ->
        User(
            idToken = dbInstance.idToken,
            name = dbInstance.name,
            email = dbInstance.email
        )
    }

}