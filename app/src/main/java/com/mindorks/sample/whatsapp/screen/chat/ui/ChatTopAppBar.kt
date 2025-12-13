package com.mindorks.sample.whatsapp.screen.chat.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mindorks.sample.whatsapp.R
import com.mindorks.sample.whatsapp.data.model.User
import com.mindorks.sample.whatsapp.util.ImageLoader
import com.mindorks.sample.whatsapp.util.colorTopBar

@Composable
fun ChatTopBar(user: User, status: String, onBackIconClick: () -> Unit) {

    Column {
        TopAppBar(
            backgroundColor = colorTopBar(),
            navigationIcon = {
                IconButton(onClick = {
                    onBackIconClick()
                }) {
                    Icon(
                        imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_back),
                        contentDescription = "Back",
                        modifier = Modifier.padding(start = 8.dp),
                        tint = Color.White
                    )
                }
            },
            actions = {
                Box(modifier = Modifier.size(40.dp).clip(CircleShape)) {
                    ImageLoader(user.imageUrl)
                }
            },
            title = {
                Column {
                    Text(
                        text = user.name,
                        color = Color.White,
                        style = TextStyle(fontSize = 18.sp, textAlign = TextAlign.Start)
                    )
                    Text(
                        text = status,
                        color = Color.White.copy(alpha = 0.7f),
                        style = TextStyle(fontSize = 12.sp, textAlign = TextAlign.Start)
                    )
                }
            }
        )
    }
}
