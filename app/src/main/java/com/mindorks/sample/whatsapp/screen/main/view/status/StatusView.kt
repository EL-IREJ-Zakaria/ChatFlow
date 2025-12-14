package com.ofppt.chatflow.screen.main.view.status

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.ofppt.chatflow.data.local.statusList
import com.ofppt.chatflow.data.model.Status

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
