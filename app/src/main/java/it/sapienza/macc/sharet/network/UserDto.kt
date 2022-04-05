package it.sapienza.macc.sharet.network

import com.squareup.moshi.JsonClass
import it.sapienza.macc.sharet.database.UserEntity
import it.sapienza.macc.sharet.domain.User

/**
 * DataTransferObjects go in this file.
 * These are responsible for parsing responses from the server or formatting objects to send to the server.
 * You should convert these to domain objects before using them.
 */

@JsonClass(generateAdapter = true)
data class UserDto (
    val idToken: String,
    val name: String
)

@JsonClass(generateAdapter = true)
data class UserDtoContainer (
    val UserDtoList: List<UserDto>
)



/**
 * Convert DTO to domain object
 */
fun UserDto.toDomainObject(): User {
    return User(
        idToken = idToken,
        name = name
    )
}


/**
 * Convert DTO to domain object
 */
fun UserDtoContainer.toDomainObject(): List<User> {
    return UserDtoList.map { dto ->
        User(
            idToken = dto.idToken,
            name = dto.name
        )
    }
}

/**
 * Convert DTO to database object
 */
fun UserDtoContainer.toDbObject(): Array<UserEntity> {
    return UserDtoList.map { dto ->
        UserEntity(
            name = dto.name
        )
    }.toTypedArray()
}

