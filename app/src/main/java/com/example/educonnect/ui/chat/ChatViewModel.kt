package com.example.educonnect.ui.chat

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.educonnect.data.database.repositories.UserRepository
import com.example.educonnect.data.model.chat.Conversation
import com.example.educonnect.data.model.chat.Message
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.UUID

class ChatViewModel(
    savedStateHandle: SavedStateHandle,
    private val userRepository: UserRepository
) : ViewModel() {
    private var _chatUiState = MutableStateFlow(ChatUiState())
    val chatUiState : StateFlow<ChatUiState> = _chatUiState.asStateFlow()

    val db = Firebase.database

    private val conversationId : String = checkNotNull(savedStateHandle[ChatDestination.conversationIdArg])

    init {
        getReceiverInfo()
    }

    fun getReceiverInfo() {
        db.getReference("conversation").child(conversationId).get().addOnSuccessListener { snapshot ->
            val conversation = snapshot.getValue(Conversation::class.java)
            val currentUserId = Firebase.auth.currentUser?.uid
            if (conversation != null && currentUserId != null) {
                viewModelScope.launch {
                    if (currentUserId == conversation.studentId) {
                        val teacher = userRepository.getTeacherProfileStream(conversation.teacherId).first()
                        _chatUiState.update { it.copy(receiverName = teacher.name, receiverImage = teacher.avatarUrl) }
                    } else {
                        val student = userRepository.getStudentProfileStream(conversation.studentId).first()
//                        _chatUiState.update { it.copy(receiverName = student.name, receiverImage = student.avatarUrl) }
                    }
                }
            }
        }
    }

    fun sendMessage(messageText: String?) {
        val message = Message(
            messageId =  db.reference.push().key ?: UUID.randomUUID().toString(),
            senderId = Firebase.auth.currentUser?.uid ?: "",
            content = messageText,
            conversationId = conversationId,
            status = "Đã gửi"
        )

        db.reference.child("message").child(conversationId).push().setValue(message)
//            .addOnCompleteListener {
//                if (it.isSuccessful) {
//                    postNotificationToUsers(channelID, message.senderName, messageText ?: "")
//                }
//            }
    }

    fun getMessages() {
        db.getReference("message").child(conversationId).orderByChild("timestamp")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val list = mutableListOf<Message>()
                    snapshot.children.forEach { data ->
                        val message = data.getValue(Message::class.java)
                        message?.let {
                            list.add(it)
                        }
                    }
                    _chatUiState.update { currentState ->
                        currentState.copy(
                            messages = list
                        )
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    // Handle error
                }
            })
//        subscribeForNotification(channelID)
//        registerUserIdToConversation(channelID)
    }
}

data class ChatUiState(
    val messages : List<Message> = emptyList(),
    val receiverName: String = "",
    val receiverImage: String = ""
)