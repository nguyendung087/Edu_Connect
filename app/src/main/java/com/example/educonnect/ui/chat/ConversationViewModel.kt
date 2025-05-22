package com.example.educonnect.ui.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.educonnect.data.database.repositories.ConversationRepository
import com.example.educonnect.data.database.repositories.UserRepository
import com.example.educonnect.data.model.chat.Conversation
import com.example.educonnect.data.model.chat.Message
import com.example.educonnect.data.model.users.TeacherProfile
import com.google.firebase.Firebase
import com.google.firebase.auth.auth
import com.google.firebase.database.database
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ConversationViewModel(
    private val userRepository: UserRepository,
) : ViewModel() {
    private var _conversationUiState = MutableStateFlow(ConversationUiState())
    val conversationUiState: StateFlow<ConversationUiState> = _conversationUiState.asStateFlow()

    private val db = Firebase.database

    init {
        getConversationForStudent()
        getConversationForTeacher()
    }

    private fun getConversationForStudent() {
        val currentUserId = Firebase.auth.currentUser?.uid ?: return
        db.getReference("conversation")
            .orderByChild("studentId")
            .equalTo(currentUserId)
            .get()
            .addOnSuccessListener { studentSnapshot ->
                viewModelScope.launch {
                    val conversationList = mutableListOf<Conversation>()
                    studentSnapshot.children.forEach { data ->
                        val conversation = data.getValue(Conversation::class.java)?.copy(conversationId = data.key!!)
                        conversation?.let { conversationList.add(it) }
                        val teacherName = getTeacherName(conversation!!.teacherId)
                        _conversationUiState.update { currentState ->
                            currentState.copy(
                                conversationList.distinctBy { it.conversationId },
                                conversationName = teacherName
                            )
                        }
                    }
                }
            }
    }

    private fun getConversationForTeacher() {
        val currentUserId = Firebase.auth.currentUser?.uid ?: return
        db.getReference("conversation")
            .orderByChild("teacherId")
            .equalTo(currentUserId)
            .get()
            .addOnSuccessListener { teacherSnapshot ->
                viewModelScope.launch {
                    val conversationList = mutableListOf<Conversation>()
                    teacherSnapshot.children.forEach { data ->
                        val conversation = data.getValue(Conversation::class.java)?.copy(conversationId = data.key!!)
                        conversation?.let { conversationList.add(it) }
                        _conversationUiState.update { it.copy() }
                        val studentName = getStudentName(conversation!!.studentId)
                        _conversationUiState.update { currentState ->
                            currentState.copy(
                                conversation = conversationList.distinctBy { it.conversationId },
                                conversationName = studentName
                            )
                        }
                    }
                }
            }
    }

    private suspend fun getTeacherName(teacherId: String): String {
        return userRepository.getTeacherProfileStream(teacherId).first().name
    }

    private suspend fun getStudentName(studentId: String): String {
        return userRepository.getStudentProfileStream(studentId).first()!!.name
    }

//    private fun syncConversationsToRoom(conversations: List<Conversation>) {
//        viewModelScope.launch {
//            conversations.forEach { conversationRepository (it) }
//        }
//    }

    fun addConversation(name: String) {
        val key = db.getReference("conversation").push().key
        db.getReference("conversation").child(key!!).setValue(name).addOnSuccessListener {
//            getConversation()
        }
    }
}

data class ConversationUiState(
    val conversation: List<Conversation> = emptyList(),
    val conversationName : String = ""
)