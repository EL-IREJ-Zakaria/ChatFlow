package com.mindorks.sample.whatsapp.screen.chat

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.google.firebase.Timestamp
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.mindorks.sample.whatsapp.data.model.Message
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.UUID

class ChatViewModel(private val otherUserId: String) : ViewModel() {

    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> = _messages

    private val _userStatus = MutableStateFlow<String>("offline")
    val userStatus: StateFlow<String> = _userStatus

    private val db = Firebase.firestore
    private val auth = Firebase.auth
    private val storage = Firebase.storage

    private var messagesListenerRegistration: ListenerRegistration? = null
    private var statusListenerRegistration: ListenerRegistration? = null

    init {
        listenForMessages()
        listenForUserStatus()
    }

    private fun getChatId(uid1: String, uid2: String): String {
        return if (uid1 < uid2) {
            "${uid1}_${uid2}"
        } else {
            "${uid2}_${uid1}"
        }
    }

    private fun listenForMessages() {
        val currentUser = auth.currentUser ?: return
        val chatId = getChatId(currentUser.uid, otherUserId)

        messagesListenerRegistration = db.collection("chats")
            .document(chatId)
            .collection("messages")
            .orderBy("timestamp")
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    val messageList = snapshot.documents.mapNotNull { doc ->
                        doc.toObject(Message::class.java)?.copy(id = doc.id)
                    }
                    _messages.value = messageList
                }
            }
    }

    private fun listenForUserStatus() {
        // Assuming there is a "users" collection where status is stored
        statusListenerRegistration = db.collection("users")
            .document(otherUserId)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    return@addSnapshotListener
                }

                if (snapshot != null && snapshot.exists()) {
                    val status = snapshot.getString("status") ?: "offline"
                    _userStatus.value = status
                } else {
                    _userStatus.value = "offline"
                }
            }
    }

    fun updateMyStatus(status: String) {
        val currentUser = auth.currentUser ?: return
        db.collection("users").document(currentUser.uid)
            .update("status", status)
    }

    fun sendMessage(text: String) {
        val currentUser = auth.currentUser ?: return
        val chatId = getChatId(currentUser.uid, otherUserId)
        
        val message = Message(
            senderId = currentUser.uid,
            receiverId = otherUserId,
            text = text,
            timestamp = Timestamp.now(),
            status = "sent"
        )

        db.collection("chats")
            .document(chatId)
            .collection("messages")
            .add(message)
    }

    fun sendImageMessage(uri: Uri) {
        val currentUser = auth.currentUser ?: return
        val chatId = getChatId(currentUser.uid, otherUserId)
        val imageRef = storage.reference.child("chat_images/${UUID.randomUUID()}")

        imageRef.putFile(uri).addOnSuccessListener {
            imageRef.downloadUrl.addOnSuccessListener { downloadUrl ->
                val message = Message(
                    senderId = currentUser.uid,
                    receiverId = otherUserId,
                    text = "",
                    imageUrl = downloadUrl.toString(),
                    timestamp = Timestamp.now(),
                    status = "sent"
                )

                db.collection("chats")
                    .document(chatId)
                    .collection("messages")
                    .add(message)
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        messagesListenerRegistration?.remove()
        statusListenerRegistration?.remove()
    }
}

class ChatViewModelFactory(private val otherUserId: String) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ChatViewModel(otherUserId) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
