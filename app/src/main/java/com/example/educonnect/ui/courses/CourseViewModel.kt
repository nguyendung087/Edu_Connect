package com.example.educonnect.ui.courses

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.educonnect.data.database.repositories.CourseRepository
import com.example.educonnect.data.database.repositories.UserRepository
import com.example.educonnect.data.model.courses.CourseWithTeacher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CourseViewModel(
    private val courseRepository: CourseRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    private var _courseUiState = MutableStateFlow(CourseUiState())
    val courseUiState : StateFlow<CourseUiState> = _courseUiState.asStateFlow()

    fun getCurrentUserEnrollment(userId: String?) {
        if (userId == null) {
            _courseUiState.update { currentState ->
                currentState.copy(currentUserId = null)
            }
            return
        }

        viewModelScope.launch {
            try {
                courseRepository.getEnrollmentsWithCourseAndTeacherStream(userId).collect { enrollment ->
                    _courseUiState.update { currentState ->
                        currentState.copy(
                            enrollment = enrollment,
                        )
                    }
                }
            } catch (e: Exception) {
                Log.e("COURSE_VIEWMODEL", "Lỗi khi lấy enrollment: $e")
            }
        }
    }
}