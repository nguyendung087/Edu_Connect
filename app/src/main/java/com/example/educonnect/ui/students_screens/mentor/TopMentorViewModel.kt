package com.example.educonnect.ui.students_screens.mentor

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.educonnect.data.database.repositories.UserRepository
import com.example.educonnect.data.model.chat.Conversation
import com.google.firebase.Firebase
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.UUID

class TopMentorViewModel(
    private val supabase: SupabaseClient,
    private val userRepository: UserRepository
) : ViewModel() {

    val mentorUiState : StateFlow<MentorUiState> =
        userRepository.getAllTeacherProfileStream().map { MentorUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = MentorUiState()
            )

    fun checkAndCreateConversation(studentId: String, teacherId: String, onComplete: (String) -> Unit) {
        viewModelScope.launch {
            try {
                val existingConversation = supabase.from("conversations")
                    .select {
                        filter {
                            eq("student_id", studentId)
                            eq("teacher_id", teacherId)
                        }
                    }
                    .decodeSingleOrNull<Conversation>()

                if (existingConversation != null) {
                    onComplete(existingConversation.conversationId)
                } else {
                    val newConversationId = UUID.randomUUID().toString()
                    val newConversation = Conversation(
                        conversationId = newConversationId,
                        studentId = studentId,
                        teacherId = teacherId,
                        createdAt = LocalDateTime.now(),
                        updatedAt = LocalDateTime.now()
                    )
                    supabase.from("conversations").insert(newConversation)
                    onComplete(newConversationId)
                }
            } catch (e: Exception) {
                Log.e("TopMentorViewModel", "Lỗi: ${e.message}")
            }
        }
    }

    fun getOrCreateConversation(studentId: String, teacherId: String, onComplete: (String) -> Unit) {
        val db = FirebaseDatabase.getInstance()
        if (studentId.isEmpty() || teacherId.isEmpty()) {
            Log.e("EduConnectDB", "studentId hoặc teacherId rỗng") // Thay tag log để dễ phân biệt
            return
        }

        Log.d("EduConnectDB", "Bắt đầu truy vấn conversation cho studentId: $studentId")

        db.getReference("conversation")
            .orderByChild("studentId")
            .equalTo(studentId)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val snapshot = task.result

                    Log.d("EduConnectDB", "Truy vấn hoàn thành. task.isSuccessful: ${task.isSuccessful}")
                    Log.d("EduConnectDB", "Snapshot exists: ${snapshot.exists()}")
                    // Log dữ liệu snapshot để kiểm tra cấu trúc và giá trị
                    Log.d("EduConnectDB", "Snapshot value: ${snapshot.value}")

                    // Xử lý khi truy vấn thành công
                    if (snapshot.exists()) {
                        // Duyệt qua các con một cách an toàn
                        val conversation = snapshot.children.firstOrNull { childSnapshot ->
                            // Kiểm tra null an toàn cho childSnapshot và giá trị teacherId
                            childSnapshot.child("teacherId").value == teacherId
                        }

                        if (conversation != null) {
                            val conversationId = conversation.key
                            if (conversationId != null) {
                                Log.d("EduConnectDB", "Tìm thấy cuộc trò chuyện cũ với ID: $conversationId")
                                onComplete(conversationId)
                            } else {
                                // Trường hợp cực hiếm key bị null, vẫn nên xử lý
                                Log.e("EduConnectDB", "Tìm thấy snapshot nhưng key bị null")
                                createNewConversation(studentId, teacherId, onComplete)
                            }
                        } else {
                            // Không tìm thấy cuộc trò chuyện khớp với teacherId
                            Log.d("EduConnectDB", "Không tìm thấy cuộc trò chuyện khớp teacherId. Tạo mới...")
                            createNewConversation(studentId, teacherId, onComplete)
                        }
                    } else {
                        // Không có dữ liệu cho studentId này → Tạo mới
                        Log.d("EduConnectDB", "Snapshot không tồn tại cho studentId này. Tạo mới...")
                        createNewConversation(studentId, teacherId, onComplete)
                    }
                } else {
                    // Truy vấn thất bại → Log lỗi và tạo mới
                    Log.e("EduConnectDB", "Lỗi truy vấn conversation: ${task.exception?.message}")
                    // Vẫn tạo mới trong trường hợp lỗi truy vấn
                    createNewConversation(studentId, teacherId, onComplete)
                }
            }
            .addOnFailureListener { e ->
                Log.e("EduConnectDB", "addOnFailureListener được gọi. Lỗi khi truy vấn conversation: ${e.message}", e)
                // Có thể gọi createNewConversation ở đây nếu bạn muốn thử lại trong trường hợp lỗi này,
                // nhưng nếu lỗi xảy ra liên tục thì sẽ gây loop. Cần cân nhắc logic retry.
                // Tạm thời chỉ log lỗi để debug.
                // createNewConversation(studentId, teacherId, onComplete) // Bỏ comment nếu muốn thử tạo mới khi có lỗi truy vấn
            }
    }

    fun createNewConversation(studentId: String, teacherId: String, onComplete: (String) -> Unit) {
        viewModelScope.launch {
            val db = Firebase.database
            Log.d("EduConnectDB", "Chuẩn bị tạo cuộc trò chuyện mới giữa $studentId và $teacherId")
            val newConversation = mapOf(
                "studentId" to studentId,
                "teacherId" to teacherId,
                "createdAt" to LocalDateTime.now().toString(),
                "updatedAt" to LocalDateTime.now().toString()
            )
            val key = db.getReference("conversation").push().key
            if (key == null) {
                Log.e("EduConnectDB", "Không tạo được key mới cho cuộc trò chuyện")
                return@launch
            }
            Log.d("EduConnectDB", "Đang tạo cuộc trò chuyện mới với key: $key")
            db.getReference("conversation").child(key).setValue(newConversation)
                .addOnSuccessListener {
                    Log.d("EduConnectDB", "Tạo cuộc trò chuyện thành công: $key")
                    onComplete(key)
                }
                .addOnFailureListener { e ->
                    Log.e("EduConnectDB", "Lỗi khi GHI cuộc trò chuyện mới với key $key: ${e.message}", e)
                }
        }
    }


    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}