package com.mindorks.sample.whatsapp.data.model

import com.google.firebase.Timestamp

data class Conversation(
    val id: String = "",
    val userName: String = "",
    val userImageUrl: String = "",
    val lastMessage: String = "",
    val lastMessageTimestamp: Timestamp? = null,
    val participants: List<String> = emptyList()
)