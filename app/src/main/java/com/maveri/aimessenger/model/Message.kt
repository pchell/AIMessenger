package com.maveri.aimessenger.model

sealed class Message(val message: String, val timestamp: Long, val visibilityDate: Boolean = false) {
    class User(message: String, timestamp: Long, visibilityDate: Boolean = false) : Message(message, timestamp, visibilityDate)
    class Other(message: String, timestamp: Long, visibilityDate: Boolean = false) : Message(message, timestamp, visibilityDate)
}



