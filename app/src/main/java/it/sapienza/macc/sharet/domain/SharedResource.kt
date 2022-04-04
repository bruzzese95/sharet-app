package it.sapienza.macc.sharet.domain

import it.sapienza.macc.sharet.network.UserProperty


/**
 * Domain objects are plain Kotlin data classes that represent the things in our app.
 * These are the objects that should be displayed on screen, or manipulated by the app.
 */
class SharedResource (
    val id: Long,
    val name: String,
    /*val owner: UserProperty,
    val group: List<UserProperty>*/
)