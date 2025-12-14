package com.ofppt.chatflow.screen.chat.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Mic
import androidx.compose.material.icons.filled.Mood
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ofppt.chatflow.util.colorTopBar

@Composable
fun EditText(onMessageSend: (String) -> Unit) {
    var textState by remember { mutableStateOf(TextFieldValue()) }

    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.Bottom
    ) {
        // Text Field and icons
        Box(
            modifier = Modifier
                .weight(1f)
                .background(Color.White, RoundedCornerShape(24.dp))
                .padding(horizontal = 4.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { /* Emoji picker */ }) {
                    Icon(Icons.Default.Mood, contentDescription = "Emoji", tint = Color.Gray)
                }
                
                BasicTextField(
                    value = textState,
                    onValueChange = { textState = it },
                    modifier = Modifier
                        .weight(1f)
                        .padding(vertical = 12.dp),
                    textStyle = TextStyle(fontSize = 16.sp),
                    decorationBox = { innerTextField ->
                        if (textState.text.isEmpty()) {
                            Text("Message", color = Color.Gray)
                        }
                        innerTextField()
                    }
                )

                IconButton(onClick = { /* Attachment */ }) {
                    Icon(Icons.Default.AttachFile, contentDescription = "Attach", tint = Color.Gray)
                }
                
                if (textState.text.isEmpty()) {
                    IconButton(onClick = { /* Camera */ }) {
                        Icon(Icons.Default.CameraAlt, contentDescription = "Camera", tint = Color.Gray)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.width(8.dp))

        // Send/Mic Button
        Box(
            modifier = Modifier
                .size(48.dp)
                .background(colorTopBar(), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            if (textState.text.isNotEmpty()) {
                IconButton(onClick = {
                    onMessageSend(textState.text)
                    textState = TextFieldValue()
                }) {
                    Icon(Icons.Default.Send, contentDescription = "Send", tint = Color.White)
                }
            } else {
                IconButton(onClick = { /* Voice note */ }) {
                    Icon(Icons.Default.Mic, contentDescription = "Mic", tint = Color.White)
                }
            }
        }
    }
}
