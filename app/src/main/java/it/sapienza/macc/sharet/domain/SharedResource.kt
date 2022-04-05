package it.sapienza.macc.sharet.domain


/**
 * Domain objects are plain Kotlin data classes that represent the things in our app.
 * These are the objects that should be displayed on screen, or manipulated by the app.
 */
class SharedResource (
    val id: Long,
    val name: String,
    val owner_id: Long
    /*val group: List<UserProperty>*/
)