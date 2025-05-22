package com.example.educonnect.ui.mentor_screens.course_management

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.educonnect.data.database.repositories.CourseRepository
import com.example.educonnect.data.model.courses.CourseWithTeacher
import com.example.educonnect.data.model.courses.Lesson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CourseManageViewModel(
    private val courseRepository: CourseRepository
) : ViewModel() {
    private var _courseUiState = MutableStateFlow(CourseManageUiState())
    val courseUiState : StateFlow<CourseManageUiState> = _courseUiState.asStateFlow()

    fun loadCourseWithTeacher(teacherId : String?) {
        viewModelScope.launch {
            courseRepository.getCourseWithTeacherByTeacherStream(teacherId).collect { courses ->
                _courseUiState.update { currentState ->
                    currentState.copy(
                        courseWithTeacherList = courses
                    )
                }

                courses.forEach { course ->
                    courseRepository.getLessonCountByCourseStream(course.course.courseId).collect { count ->
                        _courseUiState.update { currentState ->
                            currentState.copy(
                                lessonCounts = count
                            )
                        }
                    }
                }
            }
        }
    }
}

data class CourseManageUiState(
    val courseWithTeacherList : List<CourseWithTeacher> = emptyList(),
    val lessonCounts: Int = 0
)