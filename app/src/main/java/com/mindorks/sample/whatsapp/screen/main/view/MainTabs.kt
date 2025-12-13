package com.mindorks.sample.whatsapp.screen.main.view

import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.mindorks.sample.whatsapp.util.colorTopBar

data class ScreenState(var state: Screen = Screen.CHATS) {

    enum class Screen(
        val title: String = "Tab"
    ) {
        CALLS(title = "Calls"),
        CHATS(title = "Chats"),
        CONTACTS(title = "Contacts"),
        STATUS(title = "Status")
    }
}

@Composable
fun TabsPanel(
    screenState: ScreenState,
    onNavigate: (ScreenState.Screen) -> Unit,
) {
    val (selectedTab, setSelectedTab) = remember {
        mutableStateOf(
            ScreenState.Screen.values().indexOf(screenState.state)
        )
    }

    val tabs = ScreenState.Screen.values()

    TabRow(
        selectedTabIndex = selectedTab,
        backgroundColor = colorTopBar(),
        contentColor = Color.White,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTab]),
                color = Color.White,
                height = TabRowDefaults.IndicatorHeight
            )
        },
        tabs = {
            tabs.forEachIndexed { index, tab ->
                Tab(
                    text = { 
                        Text(
                            text = tab.title.uppercase(), 
                            color = if(selectedTab == index) Color.White else Color.White.copy(alpha = 0.7f),
                            maxLines = 1,
                            overflow = TextOverflow.Visible, // Laisser dépasser un peu plutôt que couper brutalement si possible, mais Ellipsis est mieux si vraiment contraint. Ici on force la taille.
                            fontSize = 12.sp, // Taille de police réduite
                            fontWeight = FontWeight.Bold,
                            softWrap = false // Empêcher le retour à la ligne
                        ) 
                    },
                    selected = index == selectedTab,
                    onClick = {
                        setSelectedTab(index)
                        onNavigate(tab)
                    }
                )
            }
        }
    )
}
