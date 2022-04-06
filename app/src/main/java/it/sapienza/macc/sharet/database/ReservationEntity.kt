package it.sapienza.macc.sharet.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import it.sapienza.macc.sharet.domain.Reservation
import it.sapienza.macc.sharet.domain.User

@Entity(tableName = "reservation_table")
data class ReservationEntity(

        @PrimaryKey(autoGenerate = true)
        var id: Int = 0,

        @ColumnInfo(name = "idResource")
        var idResource: Int = 0,

        @ColumnInfo(name = "idOwner")
        var idOwner: String = "not_initialized",

        @ColumnInfo(name = "name")
        var name: String = "not_initialized",

        @ColumnInfo(name = "date")
        var date: String = "not_initialized",

        @ColumnInfo(name = "start_time")
        var startTime: String = "not_initialized",

        @ColumnInfo(name = "end_time")
        var endTime: String = "not_initialized"

)


/**
 * Converts from database object to domain object
 * */
fun List<ReservationEntity>.toDomainModel(): List<Reservation> {
        return map { dbInstance ->
                Reservation(
                        id = dbInstance.id,
                        idResource = dbInstance.idResource,
                        idOwner = dbInstance.idOwner,
                        name = dbInstance.name,
                        date = dbInstance.date,
                        startTime = dbInstance.startTime,
                        endTime = dbInstance.endTime
                )
        }

}