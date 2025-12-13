package com.mindorks.sample.whatsapp.screen.home

/*
// Deprecated: Replaced by MainFragment and ChatsView
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.fragment.findNavController
import com.mindorks.sample.whatsapp.R
import com.mindorks.sample.whatsapp.data.model.Conversation
import com.mindorks.sample.whatsapp.util.ImageLoader
import com.mindorks.sample.whatsapp.util.colorTopBar
import java.text.SimpleDateFormat
import java.util.*

class HomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                val viewModel: HomeViewModel = viewModel()
                val conversations by viewModel.conversations.collectAsState()
                
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("WhatsApp", color = Color.White) },
                            backgroundColor = colorTopBar()
                        )
                    },
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = { 
                                // Navigate to contacts screen to start a new chat
                                findNavController().navigate(R.id.action_mainFragment_to_contactsFragment) 
                            },
                            backgroundColor = Color(0xFF25D366)
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "New Chat", tint = Color.White)
                        }
                    }
                ) { padding ->
                    LazyColumn(modifier = Modifier.padding(padding)) {
                        items(conversations) { conversation ->
                            ConversationItem(conversation) {
                                val action = HomeFragmentDirections.actionHomeFragmentToChatFragment(conversation.userName, conversation.userImageUrl)
                                findNavController().navigate(action)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ConversationItem(conversation: Conversation, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(modifier = Modifier.size(48.dp).clip(CircleShape)) {
            ImageLoader(conversation.userImageUrl)
        }
        
        Spacer(modifier = Modifier.width(16.dp))
        
        Column(modifier = Modifier.weight(1f)) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(conversation.userName, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                Text(formatTime(conversation.lastMessageTimestamp?.toDate()), fontSize = 12.sp, color = Color.Gray)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                conversation.lastMessage,
                maxLines = 1, 
                overflow = TextOverflow.Ellipsis, 
                color = Color.Gray, 
                fontSize = 14.sp
            )
        }
    }
}

fun formatTime(date: Date?): String {
    return if (date != null) {
        SimpleDateFormat("HH:mm", Locale.getDefault()).format(date)
    } else {
        ""
    }
}
*/