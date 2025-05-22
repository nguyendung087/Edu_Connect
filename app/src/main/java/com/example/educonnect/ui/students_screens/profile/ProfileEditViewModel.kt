package com.example.educonnect.ui.students_screens.profile

import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.educonnect.EduApplication
import com.example.educonnect.data.database.repositories.UserRepository
import com.example.educonnect.data.model.users.StudentProfile
import com.example.educonnect.services.supabase.FileRepository
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.launch
import java.time.LocalDate

class ProfileEditViewModel(
    private val application: EduApplication,
    private val supabase : SupabaseClient,
    private val userRepository: UserRepository,
    private val fileRepository: FileRepository
) : ViewModel() {
    var userProfileUiState by mutableStateOf(ProfileEditUiState())
        private set

    init {
        viewModelScope.launch {
            snapshotFlow { userProfileUiState.currentUserId }
                .collect { userId ->
                    userId?.takeIf { it.isNotBlank() }?.let { loadUserProfile(it) }
                }
        }
    }

    fun setCurrentUserId(userId: String?) {
        userProfileUiState = userProfileUiState.copy(
            currentUserId = userId ?: "",
            isLoadingAvatar = true
        )

        userId?.let { loadUserProfile(it) }
    }

    private fun loadUserProfile(userId: String) {
        viewModelScope.launch {
            userRepository.getStudentProfileStream(userId)
                .collect { studentProfile ->
                    userProfileUiState = userProfileUiState.copy(
                        currentUser = studentProfile,
                        isFormValid = validateInput(studentProfile)
                    )
                }
        }
    }

    suspend fun updateUserProfile() {
        if (validateInput(userProfileUiState.currentUser)) {
            userProfileUiState.currentUser?.let {
                userRepository.updateStudentProfileStream(
                    it
                )
            }
        }
    }

    fun updateUiState(profile: StudentProfile) {
        userProfileUiState = ProfileEditUiState(
            currentUser = profile,
            isFormValid = validateInput(profile)
        )
    }

    fun validateInput(studentProfile: StudentProfile?): Boolean {
        return studentProfile?.run {
            name.isNotBlank() &&
                    gender.isNotEmpty() &&
                    number.isNotBlank() &&
                    dateOfBirth.isBefore(LocalDate.now()) &&
                    address.isNotBlank() &&
                    major.isNotEmpty() &&
                    school.isNotBlank()
        } ?: false
    }

    fun uploadFile(contentUri: Uri) {
        viewModelScope.launch {
            try {
                userProfileUiState = userProfileUiState.copy(isLoadingAvatar = true)
                val inputStream = application.contentResolver.openInputStream(contentUri) ?: throw Exception("Không thể mở file")
                val userId = userProfileUiState.currentUserId ?: return@launch
                val url = fileRepository.uploadAvatar(inputStream, userId)
                val finalUrl = "$url?version=${System.currentTimeMillis()}"
                val updatedProfile = userProfileUiState.currentUser?.copy(
                    avatarUrl = finalUrl
                ) ?: return@launch

                supabase.from("student_profiles").update(
                    mapOf("avatar_url" to finalUrl)
                ) {
                    filter { eq("student_id", userId) }
                }

                userRepository.updateStudentProfileStream(updatedProfile)
                userProfileUiState = userProfileUiState.copy(
                    currentUser = updatedProfile,
                    isFormValid = validateInput(updatedProfile),
                    isLoadingAvatar = false
                )
            } catch (e: Exception) {
                userProfileUiState = userProfileUiState.copy(
                    isLoadingAvatar = false,
                    avatarError = e.message ?: "Lỗi khi upload file"
                )
            }
        }
    }

    fun downloadAvatar() {
        viewModelScope.launch {
            userProfileUiState = userProfileUiState.copy(isLoadingAvatar = true)
            try {
                val userId = userProfileUiState.currentUserId ?: return@launch
                val bytes = fileRepository.downloadFile("userfiles", userId)
                userProfileUiState = userProfileUiState.copy(
                    avatarData = bytes,
                    isLoadingAvatar = false
                )
            } catch (e: Exception) {
                Log.e("DOWNLOAD_FILE", "Lỗi khi tải file ", e)
                userProfileUiState = userProfileUiState.copy(
                    isLoadingAvatar = false,
                    avatarError = "Lỗi trong quá trình tải: ${e.message}"
                )
            }
        }
    }
}

data class ProfileEditUiState(
    val currentUser: StudentProfile? = StudentProfile(),
    val currentUserId: String? = "",
    val isFormValid: Boolean = false,
    val avatarData: ByteArray? = null,
    val isLoadingAvatar: Boolean = false,
    val avatarError: String? = null
)
