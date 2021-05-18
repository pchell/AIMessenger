package com.maveri.aimessenger.ui

data class MainViewState (
    val authStatus: AuthFirebaseStatus? = null,
    val token: String? = null
)

sealed class AuthFirebaseStatus {
    object Success : AuthFirebaseStatus()
    object Error : AuthFirebaseStatus()
}