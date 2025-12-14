package com.ofppt.chatflow.screen.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.ofppt.chatflow.R
import com.ofppt.chatflow.screen.main.MainFragmentDirections
import com.ofppt.chatflow.screen.main.view.MainViewModel
import com.ofppt.chatflow.screen.main.view.ScreenState
import com.ofppt.chatflow.screen.main.view.TabsPanel
import com.ofppt.chatflow.screen.main.view.call.CallsView
import com.ofppt.chatflow.screen.main.view.chats.ChatsView
import com.ofppt.chatflow.screen.main.view.contacts.ContactsView
import com.ofppt.chatflow.screen.main.view.status.StatusView
import com.ofppt.chatflow.ui.WhatsAppTheme
import com.ofppt.chatflow.util.colorTopBar

class MainFragment : Fragment() {

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                WhatsAppTheme {
                    // J'ai renommé la fonction pour respecter les conventions (Majuscule)
                    MainScreenContent { screen ->
                        viewModel.navigateTo(screen)
                    }
                }
            }
        }
    }

    @Composable
    private fun MainScreenContent(onNavigate: (ScreenState.Screen) -> Unit) {

        // Utilisation de 'by' pour simplifier l'accès à la valeur (optionnel mais plus propre)
        val screenState = viewModel.screenState.observeAsState(viewModel.screenState.value)
        val currentScreenState = screenState.value // Pour éviter les accès null safety répétitifs

        Column {
            TopAppBar(
                title = { Text(getString(R.string.whatsapp), color = Color.White) },
                backgroundColor = colorTopBar(),
                elevation = 0.dp,
                actions = {
                    Icon(
                        imageVector = Icons.Default.ExitToApp,
                        contentDescription = "Logout",
                        tint = Color.White,
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .clickable {
                                // Navigation simple via ID
                                findNavController().navigate(R.id.action_mainFragment_to_authFragment)
                            }
                    )
                }
            )

            // Affichage des onglets
            currentScreenState?.let { TabsPanel(it, onNavigate) }

            Surface {
                when (currentScreenState?.state) {
                    ScreenState.Screen.CALLS -> CallsView()

                    ScreenState.Screen.CHATS -> ChatsView { chat ->
                        // ICI : On utilise la classe générée automatiquement par Safe Args
                        val action = MainFragmentDirections.actionMainFragmentToChatFragment(
                            userName = chat.name,
                            userImage = chat.url
                        )
                        findNavController().navigate(action)
                    }

                    ScreenState.Screen.CONTACTS -> ContactsView { contact ->
                        val action = MainFragmentDirections.actionMainFragmentToChatFragment(
                            userName = contact.name,
                            userImage = contact.imageUrl
                        )
                        findNavController().navigate(action)
                    }

                    ScreenState.Screen.STATUS -> StatusView()

                    null -> {}
                }
            }
        }
    }
}
