package com.example.educonnect.ui.profile

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.educonnect.data.database.repositories.UserRepository
import com.example.educonnect.data.model.users.StudentProfile
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val userRepository: UserRepository
) : ViewModel() {
    private val auth = FirebaseAuth.getInstance()

    private var _profileUiState = MutableStateFlow(ProfileUiState())
    val profileUiState : StateFlow<ProfileUiState> = _profileUiState.asStateFlow()

    fun getCurrentUser(userId: String?) {
        if (userId == null) {
            _profileUiState.update { currentState ->
                currentState.copy(currentUser = null)
            }
            return
        }

        viewModelScope.launch {
            try {
                userRepository.getStudentProfileStream(userId).collect { studentProfile ->
                    _profileUiState.update { currentState ->
                        currentState.copy(currentUser = studentProfile)
                    }
                }
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Lỗi khi lấy student profile: $e")
                _profileUiState.update { currentState ->
                    currentState.copy(currentUser = null)
                }
            }
        }
    }

    fun logout() {
        auth.signOut()
        _profileUiState.value = ProfileUiState()


    }
}

data class ProfileUiState(
    val currentUser : StudentProfile? = StudentProfile()
)