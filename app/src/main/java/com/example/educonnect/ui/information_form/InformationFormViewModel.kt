package com.example.educonnect.ui.information_form

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.TypeConverter
import com.example.educonnect.data.SampleData
import com.example.educonnect.data.database.repositories.UserRepository
import com.example.educonnect.data.model.users.StudentProfile
import com.example.educonnect.data.model.users.TeacherProfile
import com.example.educonnect.ui.signup.SignupUiState
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Exception
import java.time.LocalDate
import java.time.format.DateTimeParseException

class InformationFormViewModel(
    savedStateHandle: SavedStateHandle,
    private val userRepository: UserRepository
) : ViewModel() {
    private var _teacherProfileState = MutableStateFlow(InformationUiState())
    val teacherProfileState : StateFlow<InformationUiState> = _teacherProfileState.asStateFlow()

    private val currentUser = FirebaseAuth.getInstance().currentUser
    private val userId = currentUser?.uid ?: throw IllegalStateException("Người dùng chưa đăng nhập")

    init {
        Log.d("AUTH_DEBUG", "Init Info VM - Current UID: $userId")
        _teacherProfileState.update { currentState ->
            currentState.copy(
                currentUserId = userId
            )
        }
    }

    fun updateUiState(teacherProfile: TeacherProfile) {
        _teacherProfileState.update { currentState ->
            currentState.copy(
                currentUserId = userId,
                mentorProfile = teacherProfile
            )
        }
    }

    suspend fun saveTeacherProfile(teacherProfile: TeacherProfile) {
        if (!validateInput(teacherProfile)) {
            Log.e("INFORMATION_RESULT", "Mời nhập đầy đủ thông tin")
            return
        }

        viewModelScope.launch {
            try {
                userRepository.insertTeacherProfileStream(teacherProfile)
                Log.d("INFORMATION_RESULT", "UID: ${userId}")
                updateUiState(teacherProfile)
            } catch (e: Exception) {
                Log.e("INFORMATION_RESULT", "Lỗi: $e")
            }
        }
    }

    private fun validateInput(teacherProfile: TeacherProfile) : Boolean {
        return teacherProfile.name.isNotBlank() &&
                teacherProfile.gender.isNotEmpty() &&
                teacherProfile.number.isNotBlank() &&
                teacherProfile.dateOfBirth.isBefore(LocalDate.now()) &&
                teacherProfile.specialization.isNotEmpty()
    }

    private fun fromTimestampToLocalDate(value: String?): LocalDate = value.let { LocalDate.parse(it) }

    private fun fromLocalDateToTimestamp(date: LocalDate?): String? = date?.toString()
}