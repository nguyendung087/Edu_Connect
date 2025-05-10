package com.example.educonnect.ui.mentor_screens.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.educonnect.data.database.repositories.CourseRepository
import com.example.educonnect.data.database.repositories.UserRepository
import com.example.educonnect.data.model.courses.CourseWithTeacher
import com.example.educonnect.data.model.users.TeacherProfile
import com.example.educonnect.ui.students_screens.home.HomeUiState
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MentorHomeViewModel(
    private val userRepository: UserRepository,
    private val courseRepository: CourseRepository,
) : ViewModel() {

    private var _homeUiState = MutableStateFlow(MentorHomeUiState())
    val homeUiState : StateFlow<MentorHomeUiState> = _homeUiState.asStateFlow()

    fun loadUser(userId: String?) {
        if (userId == null) {
            _homeUiState.update { currentState ->
                currentState.copy(currentUser = null)
            }
            return
        }

        viewModelScope.launch {
            try {
                userRepository.getTeacherProfileStream(userId).collect { mentorProfile ->
                    _homeUiState.update { currentState ->
                        currentState.copy(currentUser = mentorProfile)
                    }
                }
            } catch (e: Exception) {
                Log.e("MENTOR_HOME_VIEWMODEL", "Lỗi khi lấy student profile: $e")
                _homeUiState.update { currentState ->
                    currentState.copy(currentUser = null)
                }
            }
        }
    }

    fun getCoursesWithTeachersList(teacherId : String?) {
        viewModelScope.launch {
            courseRepository.getCourseWithTeacherByTeacher(teacherId).collect { courses ->
                _homeUiState.update { currentState ->
                    currentState.copy(
                        courseWithTeacherList = courses
                    )
                }
            }
        }
    }

    fun getEnrollmentsByCourse() {

    }
}

data class MentorHomeUiState(
    val currentUser : TeacherProfile? = TeacherProfile(),
    val courseWithTeacherList : List<CourseWithTeacher> = emptyList()
)