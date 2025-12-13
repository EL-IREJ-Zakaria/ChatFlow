package com.mindorks.sample.whatsapp.screen.main.view.call

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mindorks.sample.whatsapp.data.local.calls

@Composable
fun CallsView() {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn {
            items(calls) { call ->
                CallItemView(call)
            }
        }
    }
}
