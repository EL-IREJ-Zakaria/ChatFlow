package com.ofppt.chatflow.util

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.ofppt.chatflow.R

@Composable
fun ImageLoader(imageUrl: String, modifier: Modifier = Modifier) {
    var bitmap by remember { mutableStateOf<Bitmap?>(null) }
    val context = LocalContext.current

    DisposableEffect(imageUrl) {
        val target = object : CustomTarget<Bitmap>() {
            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                bitmap = resource
            }

            override fun onLoadCleared(placeholder: Drawable?) {
                bitmap = null
            }

            override fun onLoadFailed(errorDrawable: Drawable?) {
                // Handle error or set placeholder
            }
        }

        Glide.with(context)
            .asBitmap()
            .load(imageUrl)
            .placeholder(R.drawable.ic_launcher_foreground) // Placeholder image
            .error(R.drawable.ic_launcher_foreground) // Error image
            .into(target)

        onDispose {
            Glide.with(context).clear(target)
        }
    }

    Box(modifier = modifier) {
        bitmap?.let { btm ->
            Image(
                painter = BitmapPainter(btm.asImageBitmap()),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.matchParentSize()
            )
        }
    }
}
