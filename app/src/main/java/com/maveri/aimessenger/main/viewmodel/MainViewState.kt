package com.maveri.aimessenger.main.viewmodel

interface MainViewState {
    data class State (
        val authStatus: AuthFirebaseStatus? = null,
        val room: Room? = null,
        val token: String? = null
    )

    data class Room(
        val roomId: String,
        val isMyRoom: Boolean
    )

    sealed class AuthFirebaseStatus {
        object Success : AuthFirebaseStatus()
        object Error : AuthFirebaseStatus()
    }

}
