package it.sapienza.macc.sharet.network

import java.time.LocalDate

data class ReservationProperty(
        val id: String,
        val owner: UserProperty,
        val resource: SharedResourceProperty,
        val date: LocalDate,
        val startTime: String,
        val endTime: String
)
