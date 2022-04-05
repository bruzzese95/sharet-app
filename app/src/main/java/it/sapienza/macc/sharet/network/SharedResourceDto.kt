package it.sapienza.macc.sharet.network

import com.squareup.moshi.JsonClass
import it.sapienza.macc.sharet.database.SharedResourceEntity
import it.sapienza.macc.sharet.domain.SharedResource

/**
 * DataTransferObjects go in this file.
 * These are responsible for parsing responses from the server or formatting objects to send to the server.
 * You should convert these to domain objects before using them.
 */

@JsonClass(generateAdapter = true)
data class SharedResourceDto (
    val id: Int,
    val name: String,
    val owner_id: String
)

@JsonClass(generateAdapter = true)
data class SharedResourceDtoContainer (
    val sharedResourceDtoList: List<SharedResourceDto>
)

/**
 * Convert DTO to domain object
 */
fun SharedResourceDtoContainer.toDomainObject(): List<SharedResource> {
    return sharedResourceDtoList.map { dto ->
        SharedResource(
            id = dto.id,
            name = dto.name,
            owner_id = dto.owner_id
        )
    }
}

/**
 * Convert DTO to domain object
 */
fun SharedResourceDto.toDomainObject(): SharedResource {
    return SharedResource(
            id = id,
            name = name,
            owner_id = owner_id
        )
}




/**
 * Convert DTO to database object
 */
fun SharedResourceDtoContainer.toDbObject(): Array<SharedResourceEntity> {
    return sharedResourceDtoList.map { dto ->
        SharedResourceEntity(
            name = dto.name
        )
    }.toTypedArray()
}

