package com.mindorks.sample.whatsapp.screen.main.view.chats

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mindorks.sample.whatsapp.data.local.chatsList
import com.mindorks.sample.whatsapp.data.model.Chat

@Composable
fun ChatsView(onChatClick: (Chat) -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn {
            items(chatsList) { chat ->
                ChatsItemView(chat, onChatClick)
            }
        }
    }
}
