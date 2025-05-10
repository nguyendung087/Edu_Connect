package com.example.educonnect.ui.mentor_screens.course_management

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.educonnect.data.database.repositories.CourseRepository
import com.example.educonnect.data.model.courses.Course
import com.example.educonnect.data.model.courses.CourseWithTeacher
import com.example.educonnect.data.model.courses.Lesson
import com.example.educonnect.data.model.users.TeacherProfile
import com.example.educonnect.ui.mentor_screens.profile.MentorProfileEditUiState
import com.example.educonnect.ui.students_screens.courses.CourseDetailsDestination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CourseManageDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val courseRepository: CourseRepository
) : ViewModel() {
    private var _courseUiState = MutableStateFlow(CourseManageDetailsUiState())
    val courseUiState : StateFlow<CourseManageDetailsUiState> = _courseUiState.asStateFlow()

    private val courseId : String = checkNotNull(savedStateHandle[CourseManageDetailsDestination.courseIdArg])

    init {
        loadLessonsByCourse()
    }

    private fun loadLessonsByCourse() {
        viewModelScope.launch {
            courseRepository.getAllLessonsByCourseStream(courseId).collect { lessons ->
                _courseUiState.update { currentState ->
                    currentState.copy(
                        lessons = lessons
                    )
                }
            }
        }
    }

    fun addLesson(
        title : String,
        content : String
    ) {
        viewModelScope.launch {
            try {
                val lessonNumber = getNextLessonNumber()
                val lessonId = "${courseId}-${lessonNumber}"
                val newLesson = Lesson(
                    lessonId = lessonId,
                    courseId = courseId,
                    title = title,
                    content = content,
                    type = "Documents",
                    fileUrl = ""
                )
                courseRepository.insertALessonStream(newLesson)
            } catch (e : Exception) {
                Log.e("ADD_LESSON", "Lỗi khi thêm lesson", e)
            }
        }
    }

    private suspend fun getNextLessonNumber(): Int {
        val currentLessons = courseRepository.getAllLessonsByCourseStream(courseId).first()
        return currentLessons.size + 1
    }

    fun removeLesson(lessonId : String) {
        viewModelScope.launch {
            try {
                courseRepository.deleteLessonStream(
                    Lesson(
                        lessonId = lessonId
                    )
                )
            } catch (e : Exception) {
                Log.e("REMOVE_LESSON", "Lỗi khi xóa lesson", e)
            }
        }
    }

    fun updateUiState(lesson : Lesson) {
        _courseUiState.update { currentState ->
            currentState.copy(
                lesson = lesson
            )
        }
    }

    suspend fun updateLesson() {
        _courseUiState.value.lesson.let {
            courseRepository.updateLessonStream(it)
        }
    }
}

data class CourseManageDetailsUiState(
    val lessons : List<Lesson> = emptyList(),
    val lesson : Lesson = Lesson()
)