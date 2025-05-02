package com.example.educonnect.ui.information_form

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.educonnect.data.database.repositories.UserRepository
import com.example.educonnect.data.model.users.StudentProfile
import com.example.educonnect.data.model.users.TeacherProfile
import com.example.educonnect.data.model.users.User
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Exception
import java.time.LocalDate

class StudentInformationViewModel(
    savedStateHandle: SavedStateHandle,
    private val userRepository: UserRepository
) : ViewModel() {
    private var _studentUiState = MutableStateFlow(StudentProfileUiState())
    val studentUiState : StateFlow<StudentProfileUiState> = _studentUiState.asStateFlow()

    suspend fun saveStudentProfile(studentProfile: StudentProfile) {
        if (!validateInput(studentProfile)) {
            Log.e("INFORMATION_RESULT", "Mời nhập đầy đủ thông tin")
            return
        }

        viewModelScope.launch {
            try {
                userRepository.insertStudentProfileStream(studentProfile)
                _studentUiState.update { currentState ->
                    currentState.copy(
                        isFilled = true
                    )
                }
            } catch (e: Exception) {
                Log.e("INFORMATION_RESULT", "Lỗi: $e")
            }
        }
    }

    private fun validateInput(studentProfile: StudentProfile) : Boolean {
        return studentProfile.name.isNotBlank() &&
                studentProfile.gender.isNotEmpty() &&
                studentProfile.number.isNotBlank() &&
                studentProfile.dateOfBirth.isBefore(LocalDate.now()) &&
                studentProfile.address.isNotBlank() &&
                studentProfile.major.isNotEmpty() &&
                studentProfile.school.isNotBlank()
    }
}

data class StudentProfileUiState(
    val currentUserId : String = "",
    val isFilled : Boolean = false,
    val studentProfile : StudentProfile = StudentProfile()
)