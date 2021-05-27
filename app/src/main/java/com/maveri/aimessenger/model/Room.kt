package com.maveri.aimessenger.model

data class Room(
    val roomId: String,
    val isReadyStart: Boolean = false,
    val isDisconnect: Boolean = false
)