package com.mindorks.sample.whatsapp.screen.chat

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.mindorks.sample.whatsapp.data.model.User
import com.mindorks.sample.whatsapp.screen.chat.ui.ChatScreenView
import com.mindorks.sample.whatsapp.ui.WhatsAppTheme

@ExperimentalFoundationApi
class ChatFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val userName = arguments?.getString("userName") ?: ""
        val userImage = arguments?.getString("userImage") ?: ""

        // Nous utilisons userName comme ID pour l'instant car nous n'avons pas passé l'ID réel
        // Dans une vraie application, vous devriez passer l'ID unique de l'utilisateur
        
        return ComposeView(requireContext()).apply {
            setContent {
                WhatsAppTheme {
                    ChatScreenView(
                        user = User(id = userName, name = userName, imageUrl = userImage), 
                        onBackIconClick = {
                            findNavController().popBackStack()
                        }
                    )
                }
            }
        }
    }
}