package com.example.educonnect.ui.mentor_screens.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.educonnect.data.database.repositories.UserRepository
import com.example.educonnect.data.model.users.StudentProfile
import com.example.educonnect.data.model.users.TeacherProfile
import kotlinx.coroutines.launch
import java.time.LocalDate

class MentorProfileEditViewModel(
    private val userRepository: UserRepository
) : ViewModel() {
    var userProfileUiState by mutableStateOf(MentorProfileEditUiState())
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
            currentUserId = userId ?: ""
        )

        userId?.let { loadUserProfile(it) }
    }

    private fun loadUserProfile(userId: String) {
        viewModelScope.launch {
            userRepository.getTeacherProfileStream(userId)
                .collect { teacherProfile ->
                    userProfileUiState = userProfileUiState.copy(
                        currentUser = teacherProfile,
                        isFormValid = validateInput(teacherProfile)
                    )
                }
        }
    }

    suspend fun updateUserProfile() {
        if (validateInput(userProfileUiState.currentUser)) {
            userProfileUiState.currentUser?.let {
                userRepository.updateMentorProfileStream(
                    it
                )
            }
        }
    }

    fun updateUiState(profile: TeacherProfile) {
        userProfileUiState = MentorProfileEditUiState(
            currentUser = profile,
            isFormValid = validateInput(profile)
        )
    }

    fun validateInput(teacherProfile: TeacherProfile?): Boolean {
        return teacherProfile?.run {
            name.isNotBlank() &&
                    gender.isNotEmpty() &&
                    number.isNotBlank() &&
                    dateOfBirth.isBefore(LocalDate.now()) &&
                    specialization.isNotEmpty() &&
                    specialization.isNotEmpty()
        } ?: false
    }
}

data class MentorProfileEditUiState(
    val currentUser : TeacherProfile? = TeacherProfile(),
    val currentUserId : String? = "",
    val isFormValid : Boolean = false
)