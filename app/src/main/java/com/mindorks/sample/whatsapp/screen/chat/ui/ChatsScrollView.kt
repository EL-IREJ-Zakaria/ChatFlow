package com.mindorks.sample.whatsapp.screen.chat.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.mindorks.sample.whatsapp.data.model.Message
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun ChatsScrollView(messages: List<Message>) {
    val listState = rememberLazyListState()
    val currentUser = Firebase.auth.currentUser

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

    // Ajout d'une couleur de fond par défaut pour la zone de chat si nécessaire (gris clair WhatsApp)
    Box(modifier = Modifier.fillMaxSize().background(Color(0xFFECE5DD))) {
        LazyColumn(
            modifier = Modifier.fillMaxWidth().padding(bottom = 8.dp),
            state = listState
        ) {
            items(messages) { message ->
                if (message.senderId == currentUser?.uid) {
                    MyChat(message)
                } else {
                    OtherChat(message)
                }
            }
        }
    }
}

@Composable
fun MyChat(message: Message) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Column(
            modifier = Modifier
                .shadow(1.dp, RoundedCornerShape(8.dp))
                .clip(RoundedCornerShape(8.dp))
                .background(Color(0xFFE7FFDB)) // Vert clair WhatsApp moderne
                .padding(top = 5.dp, bottom = 5.dp, start = 10.dp, end = 10.dp)
                .widthIn(max = 300.dp, min = 80.dp)
        ) {
            Text(
                text = message.text,
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            
            Row(
                modifier = Modifier.align(Alignment.End),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = formatTime(message.timestamp?.toDate()),
                    fontSize = 11.sp,
                    color = Color.Gray,
                    modifier = Modifier.padding(end = 4.dp)
                )
                
                Icon(
                    imageVector = when (message.status) {
                        "read" -> Icons.Default.DoneAll
                        "delivered" -> Icons.Default.DoneAll
                        else -> Icons.Default.Check
                    },
                    contentDescription = "Status",
                    tint = if (message.status == "read") Color(0xFF53BDEB) else Color.Gray, // Bleu WhatsApp
                    modifier = Modifier.size(16.dp)
                )
            }
        }
    }
}

@Composable
fun OtherChat(message: Message) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Column(
            modifier = Modifier
                .shadow(1.dp, RoundedCornerShape(8.dp))
                .clip(RoundedCornerShape(8.dp))
                .background(Color.White)
                .padding(top = 5.dp, bottom = 5.dp, start = 10.dp, end = 10.dp)
                .widthIn(max = 300.dp, min = 80.dp)
        ) {
            Text(
                text = message.text,
                fontSize = 16.sp,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            
            Text(
                text = formatTime(message.timestamp?.toDate()),
                fontSize = 11.sp,
                color = Color.Gray,
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}

fun formatTime(date: Date?): String {
    return if (date != null) {
        val format = SimpleDateFormat("HH:mm", Locale.getDefault())
        format.format(date)
    } else {
        ""
    }
}
