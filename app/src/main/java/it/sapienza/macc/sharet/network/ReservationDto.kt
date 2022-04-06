package it.sapienza.macc.sharet.network

import com.squareup.moshi.JsonClass
import it.sapienza.macc.sharet.database.ReservationEntity
import it.sapienza.macc.sharet.domain.Reservation

/**
 * DataTransferObjects go in this file.
 * These are responsible for parsing responses from the server or formatting objects to send to the server.
 * You should convert these to domain objects before using them.
 */

@JsonClass(generateAdapter = true)
data class ReservationDto (
    val id: Int,
    val idResource: Int,
    val idOwner: String,
    val name: String,
    val date: String,
    val startTime: String,
    val endTime: String
)

@JsonClass(generateAdapter = true)
data class ReservationDtoContainer (
    val reservationDtoList: List<ReservationDto>
)

/**
 * Convert DTO to domain object
 */
fun ReservationDtoContainer.toDomainObject(): List<Reservation> {
    return reservationDtoList.map { dto ->
        Reservation(
            id = dto.id,
            idResource = dto.idResource,
            idOwner = dto.idOwner,
            name = dto.name,
            date = dto.date,
            startTime = dto.startTime,
            endTime = dto.endTime
        )
    }
}

/**
 * Convert DTO to domain object
 */
fun ReservationDto.toDomainObject(): Reservation {
    return Reservation(
        id = id,
        idResource = idResource,
        idOwner = idOwner,
        name = name,
        date = date,
        startTime = startTime,
        endTime = endTime
    )
}




/**
 * Convert DTO to database object
 */
fun ReservationDtoContainer.toDbObject(): Array<ReservationEntity> {
    return reservationDtoList.map { dto ->
        ReservationEntity(
            id = dto.id,
            idResource = dto.idResource,
            idOwner = dto.idOwner,
            name = dto.name,
            date = dto.date,
            startTime = dto.startTime,
            endTime = dto.endTime
        )
    }.toTypedArray()
}

