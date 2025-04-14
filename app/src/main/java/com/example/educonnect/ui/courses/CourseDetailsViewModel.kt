package com.example.educonnect.ui.courses

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.educonnect.data.database.repositories.CourseRepository
import com.example.educonnect.data.model.courses.CourseWithTeacher
import com.example.educonnect.data.model.courses.Lesson
import com.example.educonnect.ui.home.HomeUiState
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CourseDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val courseRepository: CourseRepository
) : ViewModel() {

    private var _courseUiState = MutableStateFlow(CourseDetailsUiState())
    val courseUiState : StateFlow<CourseDetailsUiState> = _courseUiState.asStateFlow()

    private val courseId : String = checkNotNull(savedStateHandle[CourseDetailsDestination.courseIdArg])

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            try {
                val coursesDeferred = async { getCourseWithTeacherByCourse() }
                val lessonDeferred = async { getLessonsByCourse() }
                coursesDeferred.await()
                lessonDeferred.await()
            } catch (e: Exception) {
                Log.e("FETCH_DATA", "Lỗi khi lấy data: $e")
            }
        }
    }

    private suspend fun getCourseWithTeacherByCourse() {
        courseRepository.getCourseWithTeacherByCourse(courseId).collect { course ->
            _courseUiState.update { currentState ->
                currentState.copy(
                    courseDetails = course
                )
            }
        }
    }

    private suspend fun getLessonsByCourse() {
        courseRepository.getAllLessonsByCourseStream(courseId).collect { lessons ->
            _courseUiState.update { currentState ->
                currentState.copy(
                    lessons = lessons
                )
            }
        }
    }
}

data class CourseDetailsUiState(
    val courseDetails : CourseWithTeacher = CourseWithTeacher(),
    val lessons : List<Lesson> = listOf()
)