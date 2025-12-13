package com.mindorks.sample.whatsapp.screen.chat.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mindorks.sample.whatsapp.data.model.User
import com.mindorks.sample.whatsapp.screen.chat.ChatViewModel
import com.mindorks.sample.whatsapp.screen.chat.ChatViewModelFactory

@ExperimentalFoundationApi
@Composable
fun ChatScreenView(
    user: User, 
    onBackIconClick: () -> Unit
) {
    val viewModel: ChatViewModel = viewModel(factory = ChatViewModelFactory(user.name)) 
    
    val messages by viewModel.messages.collectAsState()
    val userStatus by viewModel.userStatus.collectAsState()

    Scaffold(
        topBar = {
            ChatTopBar(user, userStatus, onBackIconClick)
        },
        bottomBar = { 
            EditText { text ->
                viewModel.sendMessage(text)
            } 
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            ChatsScrollView(messages = messages)
        }
    }
}
