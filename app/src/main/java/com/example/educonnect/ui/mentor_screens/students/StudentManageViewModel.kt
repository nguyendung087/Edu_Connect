package com.example.educonnect.ui.mentor_screens.students

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.educonnect.data.database.repositories.CourseRepository
import com.example.educonnect.data.model.courses.EnrollmentWithStudent
import com.example.educonnect.ui.mentor_screens.planning.PlanningDestination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class StudentManageViewModel(
    savedStateHandle: SavedStateHandle,
    private val courseRepository: CourseRepository
) : ViewModel() {
    private var _studentUiState = MutableStateFlow(StudentManageUiState())
    val studentUiState : StateFlow<StudentManageUiState> = _studentUiState.asStateFlow()

    private val courseId : String = checkNotNull(savedStateHandle[PlanningDestination.courseIdArg])

    init {
        loadStudentEnrolled()
    }

    private fun loadStudentEnrolled() {
        viewModelScope.launch {
            try {
                courseRepository.getStudentsByCourseStream(courseId).collect { student ->
                    _studentUiState.update { currentState ->
                        currentState.copy(
                            students = student
                        )
                    }
                }
            } catch (e : Exception) {
                Log.e("STUDENT_MANAGE", "Lỗi khi lấy students ", e)
            }
        }
    }
}

data class StudentManageUiState(
    val students : List<EnrollmentWithStudent> = emptyList()
)