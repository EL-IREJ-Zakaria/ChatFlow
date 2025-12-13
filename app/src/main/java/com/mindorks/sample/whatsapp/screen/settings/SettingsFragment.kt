package com.mindorks.sample.whatsapp.screen.settings

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Scaffold
import androidx.compose.material.Switch
import androidx.compose.material.SwitchDefaults
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import com.google.firebase.storage.ktx.storage
import com.mindorks.sample.whatsapp.R
import com.mindorks.sample.whatsapp.ui.WhatsAppTheme
import com.mindorks.sample.whatsapp.util.ImageLoader
import com.mindorks.sample.whatsapp.util.colorTopBar
import java.util.UUID

class SettingsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val auth = Firebase.auth
        val db = Firebase.firestore
        val storage = Firebase.storage
        val currentUser = auth.currentUser

        return ComposeView(requireContext()).apply {
            setContent {
                var isDarkTheme by remember { mutableStateOf(false) } // This would typically come from preferences
                
                // Dialog states
                var showNameDialog by remember { mutableStateOf(false) }
                var newName by remember { mutableStateOf("") }
                
                WhatsAppTheme(darkTheme = isDarkTheme) {
                    var name by remember { mutableStateOf(currentUser?.displayName ?: "User") }
                    var photoUrl by remember { mutableStateOf(currentUser?.photoUrl?.toString() ?: "") }
                    var notificationsEnabled by remember { mutableStateOf(true) }
                    var loading by remember { mutableStateOf(false) }

                    // Image Picker
                    val launcher = rememberLauncherForActivityResult(
                        contract = ActivityResultContracts.GetContent()
                    ) { uri: Uri? ->
                        uri?.let {
                            loading = true
                            val ref = storage.reference.child("profile_images/${currentUser?.uid}/${UUID.randomUUID()}")
                            ref.putFile(it).addOnSuccessListener {
                                ref.downloadUrl.addOnSuccessListener { downloadUrl ->
                                    // Update Firestore
                                    currentUser?.uid?.let { uid ->
                                        db.collection("users").document(uid)
                                            .update("imageUrl", downloadUrl.toString())
                                            .addOnSuccessListener {
                                                photoUrl = downloadUrl.toString()
                                                loading = false
                                                Toast.makeText(context, "Photo mise à jour", Toast.LENGTH_SHORT).show()
                                            }
                                    }
                                }
                            }.addOnFailureListener {
                                loading = false
                                Toast.makeText(context, "Erreur upload", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                    
                    // Fetch user details from Firestore if needed for realtime sync
                    LaunchedEffect(Unit) {
                        currentUser?.uid?.let { uid ->
                            db.collection("users").document(uid).get()
                                .addOnSuccessListener { document ->
                                    if (document.exists()) {
                                        name = document.getString("name") ?: name
                                        photoUrl = document.getString("imageUrl") ?: photoUrl
                                        // notificationsEnabled = document.getBoolean("notifications") ?: true
                                    }
                                }
                        }
                    }

                    if (showNameDialog) {
                        AlertDialog(
                            onDismissRequest = { showNameDialog = false },
                            title = { Text("Modifier le nom") },
                            text = {
                                OutlinedTextField(
                                    value = newName,
                                    onValueChange = { newName = it },
                                    label = { Text("Nom d'utilisateur") }
                                )
                            },
                            confirmButton = {
                                Button(
                                    onClick = {
                                        if (newName.isNotEmpty()) {
                                            currentUser?.uid?.let { uid ->
                                                db.collection("users").document(uid)
                                                    .update("name", newName)
                                                    .addOnSuccessListener {
                                                        name = newName
                                                        showNameDialog = false
                                                        Toast.makeText(context, "Nom mis à jour", Toast.LENGTH_SHORT).show()
                                                    }
                                            }
                                        }
                                    }
                                ) {
                                    Text("Enregistrer")
                                }
                            },
                            dismissButton = {
                                Button(onClick = { showNameDialog = false }) {
                                    Text("Annuler")
                                }
                            }
                        )
                    }

                    Scaffold(
                        topBar = {
                            TopAppBar(
                                title = { Text("Paramètres", color = Color.White) },
                                backgroundColor = colorTopBar(),
                                navigationIcon = {
                                    IconButton(onClick = { findNavController().popBackStack() }) {
                                        Icon(Icons.Default.ArrowBack, contentDescription = "Back", tint = Color.White)
                                    }
                                }
                            )
                        }
                    ) { padding ->
                        Box(modifier = Modifier.padding(padding)) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(16.dp)
                            ) {
                                // Profile Section
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .size(80.dp)
                                            .clip(CircleShape)
                                            .background(Color.Gray)
                                            .clickable {
                                                launcher.launch("image/*")
                                            }
                                    ) {
                                        if (photoUrl.isNotEmpty()) {
                                            ImageLoader(photoUrl)
                                        } else {
                                            Icon(
                                                Icons.Default.Person, 
                                                contentDescription = null, 
                                                modifier = Modifier.fillMaxSize().padding(16.dp),
                                                tint = Color.White
                                            )
                                        }
                                    }
                                    
                                    Spacer(modifier = Modifier.width(16.dp))
                                    
                                    Column {
                                        Text(text = name, fontSize = 20.sp, fontWeight = FontWeight.Bold)
                                        Text(text = "Modifier le profil", color = Color.Gray, fontSize = 14.sp)
                                    }
                                    
                                    Spacer(modifier = Modifier.weight(1f))
                                    
                                    IconButton(onClick = {
                                        newName = name
                                        showNameDialog = true
                                    }) {
                                        Icon(Icons.Default.Edit, contentDescription = "Edit Name", tint = colorTopBar())
                                    }
                                }

                                Divider()

                                // Notifications
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Icon(Icons.Default.Notifications, contentDescription = null, tint = Color.Gray)
                                    Spacer(modifier = Modifier.width(16.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(text = "Notifications", fontSize = 16.sp)
                                        Text(text = "Activer/Désactiver", fontSize = 12.sp, color = Color.Gray)
                                    }
                                    Switch(
                                        checked = notificationsEnabled,
                                        onCheckedChange = { notificationsEnabled = it },
                                        colors = SwitchDefaults.colors(checkedThumbColor = colorTopBar())
                                    )
                                }

                                // Dark Mode
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    // You might want a Moon icon here
                                    Icon(Icons.Default.Person, contentDescription = null, tint = Color.Gray) // Placeholder icon
                                    Spacer(modifier = Modifier.width(16.dp))
                                    Column(modifier = Modifier.weight(1f)) {
                                        Text(text = "Mode sombre", fontSize = 16.sp)
                                        Text(text = "Thème de l'application", fontSize = 12.sp, color = Color.Gray)
                                    }
                                    Switch(
                                        checked = isDarkTheme,
                                        onCheckedChange = { isDarkTheme = it },
                                        colors = SwitchDefaults.colors(checkedThumbColor = colorTopBar())
                                    )
                                }
                                
                                Spacer(modifier = Modifier.weight(1f))

                                // Logout Button
                                Button(
                                    onClick = {
                                        auth.signOut()
                                        // Navigate back to Auth screen and clear backstack
                                        findNavController().navigate(R.id.action_settingsFragment_to_authFragment)
                                    },
                                    modifier = Modifier.fillMaxWidth(),
                                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
                                ) {
                                    Text("Se déconnecter", color = Color.White)
                                }
                            }
                            
                            if (loading) {
                                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                            }
                        }
                    }
                }
            }
        }
    }

    private fun Unit.launch(string: String) {
        TODO("Not yet implemented")
    }

    private fun ComposeView.rememberLauncherForActivityResult(
        contract: ActivityResultContracts.GetContent,
        function: (Uri?) -> StorageTask<UploadTask.TaskSnapshot>?
    ) {
        TODO("Not yet implemented")
    }
}
