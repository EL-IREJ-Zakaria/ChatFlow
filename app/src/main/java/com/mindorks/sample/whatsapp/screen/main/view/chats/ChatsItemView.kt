package com.mindorks.sample.whatsapp.screen.main.view.chats

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mindorks.sample.whatsapp.data.model.Chat
import com.mindorks.sample.whatsapp.util.ImageLoader

@Composable
fun ChatsItemView(chat: Chat, onChatClick: (Chat) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onChatClick(chat) })
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
        ) {
            ImageLoader(chat.url)
        }

        Column(
            modifier = Modifier
                .padding(start = 16.dp)
                .weight(1f)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = chat.name,
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
                Text(
                    text = chat.time,
                    style = TextStyle(
                        fontSize = 12.sp,
                        color = if (chat.unreadCount != "0") Color(0xFF25D366) else Color.Gray
                    )
                )
            }
            
            Spacer(modifier = Modifier.height(4.dp))
            
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = chat.chat,
                    style = TextStyle(
                        fontSize = 14.sp,
                        color = Color.Gray
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.weight(1f)
                )
                
                if (chat.unreadCount != "0") {
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .background(Color(0xFF25D366), CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = chat.unreadCount,
                            style = TextStyle(
                                fontSize = 10.sp,
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        )
                    }
                }
            }
        }
    }
    Divider(color = Color.LightGray, thickness = 0.5.dp, startIndent = 72.dp)
}
