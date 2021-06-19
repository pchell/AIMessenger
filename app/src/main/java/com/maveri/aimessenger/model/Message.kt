package com.maveri.aimessenger.model

sealed class Message(val message: String, val timestamp: Long) {
    class User(message: String, timestamp: Long) : Message(message, timestamp)
    class Other(message: String, timestamp: Long) : Message(message, timestamp)
}

