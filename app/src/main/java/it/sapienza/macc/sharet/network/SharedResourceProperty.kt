package it.sapienza.macc.sharet.network

data class SharedResourceProperty (
            val id: String,
            val name: String,
            val owner: UserProperty,
            val group: List<UserProperty>
)