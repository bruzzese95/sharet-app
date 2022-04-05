package it.sapienza.macc.sharet.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import it.sapienza.macc.sharet.domain.SharedResource
import it.sapienza.macc.sharet.domain.User

@Entity(tableName = "user_table")
data class UserEntity(

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @ColumnInfo(name = "user_name")
    var name: String = "not_initialized"
)


/**
 * Converts from database object to domain object
 * */
fun List<UserEntity>.toDomainModel(): List<User> {
    return map { dbInstance ->
        User(
            id = dbInstance.id,
            name = dbInstance.name
        )
    }

}