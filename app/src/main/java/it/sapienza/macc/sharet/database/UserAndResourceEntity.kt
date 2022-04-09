package it.sapienza.macc.sharet.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import it.sapienza.macc.sharet.domain.SharedResource
import it.sapienza.macc.sharet.domain.UserAndResource

@Entity(tableName = "user_and_resource_table")
data class UserAndResourceEntity(

    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    @ColumnInfo(name = "id_user")
    var idUser: String = "not_initialized",

    @ColumnInfo(name = "id_resource")
    var idResource: Int = 0
)


/**
 * Converts from database object to domain object
 * */
fun List<UserAndResourceEntity>.toDomainModel(): List<UserAndResource> {
    return map { dbInstance ->
        UserAndResource(
            id = dbInstance.id,
            idUser = dbInstance.idUser,
            idResource = dbInstance.idResource
        )
    }

}