package com.ofppt.chatflow.screen.home

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.ofppt.chatflow.data.model.Conversation
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class HomeViewModel : ViewModel() {

    private val _conversations = MutableStateFlow<List<Conversation>>(emptyList())
    val conversations: StateFlow<List<Conversation>> = _conversations

    private val db = Firebase.firestore
    private val auth = Firebase.auth

    private var conversationsListener: ListenerRegistration? = null

    init {
        listenForConversations()
    }

    private fun listenForConversations() {
        val currentUser = auth.currentUser ?: return

        conversationsListener = db.collection("conversations")
            .whereArrayContains("participants", currentUser.uid)
            .addSnapshotListener { snapshot, e ->
                if (e != null) {
                    return@addSnapshotListener
                }

                if (snapshot != null) {
                    val conversationList = snapshot.documents.mapNotNull { doc ->
                        doc.toObject(Conversation::class.java)?.copy(id = doc.id)
                    }
                    _conversations.value = conversationList
                }
            }
    }

    override fun onCleared() {
        super.onCleared()
        conversationsListener?.remove()
    }
}
