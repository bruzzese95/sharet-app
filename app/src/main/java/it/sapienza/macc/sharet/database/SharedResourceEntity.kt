package it.sapienza.macc.sharet.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import it.sapienza.macc.sharet.domain.SharedResource
import it.sapienza.macc.sharet.domain.User

@Entity(tableName = "shared_resource_table")
data class SharedResourceEntity(

    @PrimaryKey(autoGenerate = true)
    var id: Long = 0L,

    @ColumnInfo(name = "resource_name")
    var name: String = "not_initialized",

    @ColumnInfo(name = "owner_id")
    var owner_id: Long = 0L
)


/**
 * Converts from database object to domain object
 * */
fun List<SharedResourceEntity>.toDomainModel(): List<SharedResource> {
    return map { dbInstance ->
        SharedResource(
            id = dbInstance.id,
            name = dbInstance.name,
            owner_id = dbInstance.owner_id
        )
    }

}