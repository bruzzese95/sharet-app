package it.sapienza.macc.sharet.network

import com.squareup.moshi.JsonClass
import it.sapienza.macc.sharet.database.UserAndResourceEntity
import it.sapienza.macc.sharet.domain.UserAndResource

/**
 * DataTransferObjects go in this file.
 * These are responsible for parsing responses from the server or formatting objects to send to the server.
 * You should convert these to domain objects before using them.
 */

@JsonClass(generateAdapter = true)
data class UserAndResourceDto (
    val id: Int,
    val idUser: String,
    val idResource: Int
)

@JsonClass(generateAdapter = true)
data class UserAndResourceDtoContainer (
    val userAndResourceDtoList: List<UserAndResourceDto>
)

/**
 * Convert DTO to domain object
 */
fun UserAndResourceDtoContainer.toDomainObject(): List<UserAndResource> {
    return userAndResourceDtoList.map { dto ->
        UserAndResource(
            id = dto.id,
            idUser = dto.idUser,
            idResource = dto.idResource
        )
    }
}

/**
 * Convert DTO to domain object
 */
fun UserAndResourceDto.toDomainObject(): UserAndResource {
    return UserAndResource(
        id = id,
        idUser = idUser,
        idResource = idResource
    )
}




/**
 * Convert DTO to database object
 */
fun UserAndResourceDtoContainer.toDbObject(): Array<UserAndResourceEntity> {
    return userAndResourceDtoList.map { dto ->
        UserAndResourceEntity(
            id = dto.id,
            idUser = dto.idUser,
            idResource = dto.idResource
        )
    }.toTypedArray()
}

