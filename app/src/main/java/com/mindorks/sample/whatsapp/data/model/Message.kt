package com.ofppt.chatflow.data.model

import com.google.firebase.Timestamp

data class Message(
    val id: String = "",
    val senderId: String = "",
    val receiverId: String = "",
    val text: String = "",
    val imageUrl: String? = null,
    val timestamp: Timestamp? = null,
    val status: String = "sent" // sent, delivered, read
)
