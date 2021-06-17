package com.maveri.aimessenger.model

sealed class Message(val message: String) {
    class User(message: String) : Message(message)
    class Other(message: String) : Message(message)
}

