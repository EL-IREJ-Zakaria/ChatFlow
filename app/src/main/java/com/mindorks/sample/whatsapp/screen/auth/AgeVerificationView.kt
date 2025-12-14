package com.ofppt.chatflow.screen.auth

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AgeVerificationView(
    onVerificationSuccess: () -> Unit
) {
    var age by remember { mutableStateOf("") }
    var error by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Vérification de l'âge",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = Color(0xFF075E54)
        )

        Spacer(modifier = Modifier.height(16.dp))
        
        Text(
            text = "Vous devez avoir au moins 18 ans pour utiliser WhatsApp.",
            color = Color.Gray,
            fontSize = 14.sp
        )

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = age,
            onValueChange = { 
                age = it 
                error = null
            },
            label = { Text("Votre âge") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
            isError = error != null
        )
        
        if (error != null) {
            Text(
                text = error!!,
                color = MaterialTheme.colors.error,
                style = MaterialTheme.typography.caption,
                modifier = Modifier.padding(start = 16.dp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = { 
                val ageInt = age.toIntOrNull()
                if (ageInt != null && ageInt >= 18) {
                    onVerificationSuccess()
                } else {
                    error = "Vous devez être majeur pour continuer."
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF25D366))
        ) {
            Text(text = "Terminer", color = Color.White)
        }
    }
}
