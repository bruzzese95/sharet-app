package it.sapienza.macc.sharet.domain


/**
 * Domain objects are plain Kotlin data classes that represent the things in our app.
 * These are the objects that should be displayed on screen, or manipulated by the app.
 */
class User(
    val id: Long,
    val name: String
)