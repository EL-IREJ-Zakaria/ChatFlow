package com.ofppt.chatflow.screen.onboarding

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.ofppt.chatflow.R
import com.ofppt.chatflow.ui.WhatsAppTheme

class OnboardingFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                WhatsAppTheme {
                    WelcomeScreen(onAgreeAndContinue = {
                        findNavController().navigate(R.id.action_onboardingFragment_to_authFragment)
                    })
                }
            }
        }
    }
}

@Composable
fun WelcomeScreen(onAgreeAndContinue: () -> Unit) {
    Surface(
        color = Color.White,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 24.dp, vertical = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            
            // Partie Supérieure : Titre
            Spacer(modifier = Modifier.height(24.dp))
            Text(
                text = "Welcome to ChatFlow",
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFF128C7E), // Couleur WhatsApp Teal
                textAlign = TextAlign.Center
            )

            // Partie Centrale : Illustration
            Box(
                modifier = Modifier
                    .size(280.dp)
                    .clip(CircleShape),
                contentAlignment = Alignment.Center
            ) {
                // Utilisation du logo existant comme illustration
                Image(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_whatsapp_logo),
                    contentDescription = "Welcome Image",
                    modifier = Modifier.size(150.dp),
                    // On applique une teinte verte si l'icône est blanche par défaut, sinon on laisse tel quel
                    // colorFilter = ColorFilter.tint(Color(0xFF128C7E)) 
                )
            }

            // Partie Inférieure : Texte légal et Bouton
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = buildAnnotatedString {
                        append("Read our ")
                        withStyle(style = SpanStyle(color = Color(0xFF00A884))) {
                            append("Privacy Policy")
                        }
                        append(". Tap \"Agree and continue\" to accept the ")
                        withStyle(style = SpanStyle(color = Color(0xFF00A884))) {
                            append("Terms of Service")
                        }
                        append(".")
                    },
                    fontSize = 13.sp,
                    color = Color.Gray,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 24.dp)
                )

                Button(
                    onClick = onAgreeAndContinue,
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF00A884)), // Vert vif WhatsApp
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp),
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(
                        text = "AGREE AND CONTINUE",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                }

                Spacer(modifier = Modifier.height(32.dp))

                // Footer "from Meta"
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(
                        text = "from",
                        fontSize = 12.sp,
                        color = Color.Gray
                    )
                    Text(
                        text = "Meta",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFF25D366) // Vert Meta/WhatsApp
                    )
                }
            }
        }
    }
}
