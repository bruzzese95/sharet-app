package it.sapienza.macc.sharet.domain

import java.time.LocalDate

class Reservation(
    val id: Int,
    val idResource: Int,
    val idOwner: String,
    val name: String,
    val date: String,
    val startTime: String,
    val endTime: String
)
