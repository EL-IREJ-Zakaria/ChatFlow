package com.mindorks.sample.whatsapp.screen.main.view.contacts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mindorks.sample.whatsapp.data.model.User
import com.mindorks.sample.whatsapp.util.ImageLoader

@Composable
fun ContactsView(
    onUserClick: (User) -> Unit
) {
    // Dummy data for users - In a real app, this would come from a database or API
    val allUsers = remember {
        listOf(
            User("1", "Alice", "https://randomuser.me/api/portraits/women/1.jpg"),
            User("2", "Bob", "https://randomuser.me/api/portraits/men/2.jpg"),
            User("3", "Charlie", "https://randomuser.me/api/portraits/men/3.jpg"),
            User("4", "Diana", "https://randomuser.me/api/portraits/women/4.jpg"),
            User("5", "Eve", "https://randomuser.me/api/portraits/women/5.jpg"),
            User("6", "Frank", "https://randomuser.me/api/portraits/men/6.jpg"),
            User("7", "Grace", "https://randomuser.me/api/portraits/women/7.jpg"),
            User("8", "Hank", "https://randomuser.me/api/portraits/men/8.jpg")
        )
    }

    var searchQuery by remember { mutableStateOf("") }
    
    val filteredUsers = if (searchQuery.isEmpty()) {
        allUsers
    } else {
        allUsers.filter { it.name.contains(searchQuery, ignoreCase = true) }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Search Bar
        TextField(
            value = searchQuery,
            onValueChange = { searchQuery = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            placeholder = { Text("Rechercher un contact...") },
            leadingIcon = { Icon(Icons.Default.Search, contentDescription = "Search") },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.White,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            ),
            shape = MaterialTheme.shapes.medium,
            singleLine = true,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search)
        )

        // Contact List
        LazyColumn {
            items(filteredUsers) { user ->
                ContactItem(user = user, onClick = { onUserClick(user) })
                Divider(color = Color.LightGray, thickness = 0.5.dp, startIndent = 72.dp)
            }
        }
    }
}

@Composable
fun ContactItem(user: User, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        // Profile Image
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
        ) {
            ImageLoader(user.imageUrl)
        }

        Spacer(modifier = Modifier.width(16.dp))

        // User Name
        Text(
            text = user.name,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black
        )
    }
}
