package com.ofppt.chatflow.screen.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.ofppt.chatflow.R
import com.ofppt.chatflow.ui.WhatsAppTheme

class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val auth = Firebase.auth
        return ComposeView(requireContext()).apply {
            setContent {
                WhatsAppTheme {
                    SplashView {
                        if (auth.currentUser != null) {
                            findNavController().navigate(R.id.action_splashFragment_to_mainFragment)
                        } else {
                            findNavController().navigate(R.id.action_splashFragment_to_onboardingFragment)
                        }
                    }
                }
            }
        }
    }
}
