package com.example.educonnect.ui.courses

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.educonnect.data.database.repositories.CourseRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CourseViewModel(
    private val courseRepository: CourseRepository
) : ViewModel() {
    private var _courseUiState = MutableStateFlow(CourseUiState())
    val courseUiState : StateFlow<CourseUiState> = _courseUiState.asStateFlow()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            getCoursesWithTeachersList()
        }
    }

    private suspend fun getCoursesWithTeachersList() {
        courseRepository.getCoursesWithTeachers().collect { courses ->
            _courseUiState.update { currentState ->
                currentState.copy(
                    courseList = courses
                )
            }
        }
    }

    private suspend fun getAllBookmarkedCourses() {

    }
}