package com.mindorks.sample.whatsapp.screen.main.view.status

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mindorks.sample.whatsapp.data.local.statusList
import com.mindorks.sample.whatsapp.data.model.Status

@Composable
fun StatusView() {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn {
            items(statusList) { statusItem ->
                StatusItemView(statusItem)
            }
        }
    }
}
