package com.maveri.aimessenger.main.viewmodel

import com.maveri.aimessenger.model.Room

interface MainViewState {
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
