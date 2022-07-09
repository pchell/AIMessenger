package com.maveri.aimessenger.room.presentation

import com.maveri.aimessenger.model.Message
import com.maveri.aimessenger.model.Room

class RoomViewState private constructor() {
    data class State(
        val room: Room? = null,
        val message: List<Message>? = null
    )
}