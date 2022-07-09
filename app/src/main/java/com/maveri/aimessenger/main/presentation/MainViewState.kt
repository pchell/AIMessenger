package com.maveri.aimessenger.main.presentation

import com.maveri.aimessenger.model.Room

class MainViewState private constructor() {
    data class State(
        val authStatus: AuthFirebaseStatus? = null,
        val room: Room? = null,
        val token: String? = null
    )

    sealed class AuthFirebaseStatus {
        object Success : AuthFirebaseStatus()
        object Error : AuthFirebaseStatus()
    }
}
