package com.ofppt.chatflow.screen.main.view.call

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.ofppt.chatflow.R
import com.ofppt.chatflow.data.model.Call
import com.ofppt.chatflow.util.ImageLoader

@Composable
fun CallItemView(call: Call) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
        ) {
            ImageLoader(call.imageUrl)
        }

        Column(
            modifier = Modifier
                .padding(start = 16.dp)
                .weight(1f)
        ) {
            Text(
                text = call.name,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Default.Call,
                    contentDescription = null,
                    modifier = Modifier.size(12.dp),
                    tint = if (call.voiceStatus == R.drawable.ic_down_missed) Color.Red else Color.Green
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = call.time,
                    style = TextStyle(fontSize = 14.sp, color = Color.Gray)
                )
            }
        }
        
        Icon(
            imageVector = Icons.Default.Call,
            contentDescription = "Call",
            tint = Color(0xFF075E54)
        )
    }
    Divider(color = Color.LightGray, thickness = 0.5.dp, startIndent = 72.dp)
}
