package com.maveri.aimessenger.ui

data class MainViewState (
    val authStatus: AuthFirebaseStatus
)

sealed class AuthFirebaseStatus {
    object Success : AuthFirebaseStatus()
    object Error : AuthFirebaseStatus()
}