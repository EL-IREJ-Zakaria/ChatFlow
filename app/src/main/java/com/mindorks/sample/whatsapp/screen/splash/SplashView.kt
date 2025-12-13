package com.mindorks.sample.whatsapp.screen.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.mindorks.sample.whatsapp.R
import kotlinx.coroutines.delay

private const val DELAY: Long = 2000

@Composable
fun SplashView(modifier: Modifier = Modifier, loadNextScreen: () -> Unit) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF25D366),
                        Color(0xFF075E54)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        LaunchedEffect(Unit) {
            delay(DELAY)
            loadNextScreen()
        }
        Image(
            imageVector = ImageVector.vectorResource(id = R.drawable.ic_whatsapp_logo),
            contentDescription = "Logo"
        )
    }
}
