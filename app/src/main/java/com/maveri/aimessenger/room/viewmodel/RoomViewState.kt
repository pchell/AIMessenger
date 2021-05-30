package com.maveri.aimessenger.room.viewmodel

import com.maveri.aimessenger.model.Message
import com.maveri.aimessenger.model.Room

interface RoomViewState {
    data class State(
        val room: Room? = null,
        val message: Message? = null
    )
}