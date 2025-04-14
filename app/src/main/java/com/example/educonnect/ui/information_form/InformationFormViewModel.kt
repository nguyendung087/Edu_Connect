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
import com.example.educonnect.data.model.users.TeacherProfile
import com.example.educonnect.ui.signup.SignupUiState
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.Exception
import java.time.LocalDate
import java.time.format.DateTimeParseException

class InformationFormViewModel(
    savedStateHandle: SavedStateHandle,
    private val userRepository: UserRepository
) : ViewModel() {
    var _teacherProfileState by mutableStateOf(InformationUiState())
        private set

    private val auth = FirebaseAuth.getInstance()

    private val userId : String = checkNotNull(savedStateHandle[InformationFormDestination.userIdArg])
//    private fun getCurrentUserId(userId : String) {
//        _teacherProfileState.value.copy(
//            teacherId = userId
//        )
//    }

    init {
        Log.d("AUTH_DEBUG", "Init Info VM - Current UID: ${auth.currentUser?.uid}")
//        _teacherProfileState.teacherProfile.copy(
//            teacherId = userId
//        )
    }

    fun updateUiState(teacherProfile: TeacherProfile) {
        _teacherProfileState = InformationUiState(
            teacherProfile = teacherProfile,
            isFormValid = validateInput(teacherProfile)
        )
    }

    suspend fun saveTeacherProfile() {
        val currentUser = auth.currentUser ?: return
        if (validateInput()) {
            userRepository.insertTeacherProfileStream(_teacherProfileState.teacherProfile.copy(
                teacherId = currentUser.uid
            ))
        }
    }

    fun saveTeacherProfile(
        name: String,
        specialization: String,
        dob: String,
        phone: String,
        gender: String
    ) {
        val teacherProfile = TeacherProfile(
            teacherId = userId,
            name = name,
            specialization = specialization,
            dateOfBirth = fromTimestampToLocalDate(dob),
            number = phone,
            gender = gender
        )

        viewModelScope.launch {
            userRepository.insertTeacherProfileStream(teacherProfile)
        }
    }

    private fun validateInput(uiState : TeacherProfile = _teacherProfileState.teacherProfile) : Boolean {
        return with(uiState) {
            name.isNotBlank() &&
            specialization.isNotEmpty() &&
            dateOfBirth.isBefore(LocalDate.now()) &&
            number.isNotBlank() &&
            gender.isNotEmpty()
        }
    }

    private fun fromTimestampToLocalDate(value: String?): LocalDate = value.let { LocalDate.parse(it) }

    private fun fromLocalDateToTimestamp(date: LocalDate?): String? = date?.toString()

//    suspend fun insertSampleData() {
//        viewModelScope.launch {
//            try {
//                SampleData.teachers.forEach { teacher ->
//                    userRepository.insertTeacherProfileStream(teacher)
//                }
//                _registerUiState.value = SignupUiState.Complete
//            } catch (e: Exception) {
//                _registerUiState.value = SignupUiState.Error("Database error: ${e.message}")
//            }
//        }
//
//        // Insert courses
////        SampleData.courses.forEach { course ->
////            courseDao.insertCourse(course)
////        }
//    }
}