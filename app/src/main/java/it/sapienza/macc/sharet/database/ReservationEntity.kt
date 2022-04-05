package it.sapienza.macc.sharet.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import it.sapienza.macc.sharet.domain.Reservation
import it.sapienza.macc.sharet.domain.User

@Entity(tableName = "reservation_table")
data class ReservationEntity(

        @PrimaryKey(autoGenerate = true)
        var id: Long = 0L,

        @ColumnInfo(name = "reservation_name")
        var name: String = "not_initialized"

)


/**
 * Converts from database object to domain object
 * */
fun List<ReservationEntity>.toDomainModel(): List<Reservation> {
        return map { dbInstance ->
                Reservation(
                        id = dbInstance.id,
                        name = dbInstance.name
                )
        }

}