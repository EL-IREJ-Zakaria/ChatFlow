package com.ofppt.chatflow.screen.auth

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.runtime.*
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ofppt.chatflow.R
import com.ofppt.chatflow.ui.WhatsAppTheme

class AuthFragment : Fragment() {

    enum class AuthScreen {
        LOGIN, REGISTER, FORGOT_PASSWORD, PROFILE_SETUP, AGE_VERIFICATION
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val auth = Firebase.auth
        val db = Firebase.firestore

        return ComposeView(requireContext()).apply {
            setContent {
                WhatsAppTheme {
                    var currentScreen by remember { mutableStateOf(AuthScreen.LOGIN) }
                    
                    when (currentScreen) {
                        AuthScreen.LOGIN -> LoginView(
                            onLoginClick = { email, password -> 
                                auth.signInWithEmailAndPassword(email, password)
                                    .addOnSuccessListener {
                                        db.collection("users").document(it.user!!.uid)
                                            .update("status", "online")
                                        findNavController().navigate(R.id.action_authFragment_to_mainFragment)
                                    }
                                    .addOnFailureListener {
                                        Toast.makeText(context, "Erreur: ${it.message}", Toast.LENGTH_SHORT).show()
                                    }
                            },
                            onRegisterClick = { currentScreen = AuthScreen.REGISTER },
                            onForgotPasswordClick = { currentScreen = AuthScreen.FORGOT_PASSWORD },
                            onGoogleLoginClick = {
                                // TODO: Implémenter Google Sign-In
                                // Nécessite l'ajout de la dépendance 'com.google.android.gms:play-services-auth' dans build.gradle
                                Toast.makeText(context, "Google Sign-In nécessite des dépendances supplémentaires", Toast.LENGTH_LONG).show()
                            }
                        )
                        AuthScreen.REGISTER -> RegisterView(
                            onRegisterSuccess = { currentScreen = AuthScreen.PROFILE_SETUP },
                            onLoginClick = { currentScreen = AuthScreen.LOGIN }
                        )
                        AuthScreen.FORGOT_PASSWORD -> ForgotPasswordView(
                            onSubmit = { email -> 
                                auth.sendPasswordResetEmail(email)
                                    .addOnSuccessListener {
                                        Toast.makeText(context, "Email envoyé", Toast.LENGTH_SHORT).show()
                                        currentScreen = AuthScreen.LOGIN 
                                    }
                                    .addOnFailureListener {
                                        Toast.makeText(context, "Erreur: ${it.message}", Toast.LENGTH_SHORT).show()
                                    }
                            }
                        )
                        AuthScreen.PROFILE_SETUP -> ProfileSetupView(
                            onNextClick = { username ->
                                currentScreen = AuthScreen.AGE_VERIFICATION 
                            }
                        )
                        AuthScreen.AGE_VERIFICATION -> AgeVerificationView(
                            onVerificationSuccess = {
                                findNavController().navigate(R.id.action_authFragment_to_mainFragment)
                            }
                        )
                    }
                }
            }
        }
    }
}
