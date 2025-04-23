package com.example.educonnect.ui.profile

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.educonnect.data.database.repositories.UserRepository
import com.example.educonnect.data.model.users.StudentProfile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate

class ProfileEditViewModel(
//    savedStateHandle: SavedStateHandle,
    private val userRepository: UserRepository
) : ViewModel() {
//    private var _userProfileUiState = MutableStateFlow(ProfileEditUiState())
//    val userProfileUiState : StateFlow<ProfileEditUiState> = _userProfileUiState.asStateFlow()
    var userProfileUiState by mutableStateOf(ProfileEditUiState())
        private set
//    private val userId : String = checkNotNull(savedStateHandle[ProfileEditDestination.profileIdArg])

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
}

data class ProfileEditUiState(
    val currentUser : StudentProfile? = StudentProfile(),
    val currentUserId : String? = "",
    val isFormValid : Boolean = false
)
