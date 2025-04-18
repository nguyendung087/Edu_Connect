package com.example.educonnect.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.educonnect.R
import com.example.educonnect.data.SampleData
import com.example.educonnect.data.database.repositories.BookmarkRepository
import com.example.educonnect.data.database.repositories.CourseRepository
import com.example.educonnect.data.database.repositories.UserRepository
import com.example.educonnect.data.model.courses.Bookmark
import com.example.educonnect.data.model.courses.Course
import com.example.educonnect.data.model.users.StudentProfile
import com.example.educonnect.data.model.users.TeacherProfile
import com.example.educonnect.data.model.users.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalDateTime

class HomeViewModel(
    private val userRepository: UserRepository,
    private val courseRepository: CourseRepository,
) : ViewModel() {

    private var _homeUiState = MutableStateFlow(HomeUiState())
    val homeUiState : StateFlow<HomeUiState> = _homeUiState.asStateFlow()

    init {
        loadData()
    }

    fun loadUser(userId: String?) {
        if (userId == null) {
            _homeUiState.update { currentState ->
                currentState.copy(currentUser = null)
            }
            return
        }

        viewModelScope.launch {
            try {
                userRepository.getStudentProfileStream(userId).collect { studentProfile ->
                    _homeUiState.update { currentState ->
                        currentState.copy(currentUser = studentProfile)
                    }
                }
            } catch (e: Exception) {
                Log.e("HomeViewModel", "Lỗi khi lấy student profile: $e")
                _homeUiState.update { currentState ->
                    currentState.copy(currentUser = null)
                }
            }
        }
    }

    private fun loadData() {
        viewModelScope.launch {
            try {
                val coursesDeferred = async { getCoursesWithTeachersList() }
                val mentorDeferred = async { getMentorList() }
                coursesDeferred.await()
                mentorDeferred.await()
            } catch (e: Exception) {
                Log.e("FETCH_DATA", "Lỗi khi lấy data: $e")
            }
        }
    }

    private suspend fun getCoursesWithTeachersList() {
        courseRepository.getCoursesWithTeachers().collect { courses ->
            _homeUiState.update { currentState ->
                currentState.copy(
                    courseWithTeacherList = courses
                )
            }
        }
    }

    private suspend fun getMentorList() {
        userRepository.getAllTeacherProfile().collect { mentors ->
            _homeUiState.update { currentState ->
                currentState.copy(
                    mentorList = mentors
                )
            }
        }
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    fun insertBulkUsers() {
        val userList = SampleData.users
        viewModelScope.launch {
            try {
                userRepository.insertAllUserStream(userList)
            } catch (ex: Exception) {
                Log.e("HomeViewModel", "Lỗi khi chèn users", ex)
            }
        }
    }

    fun insertBulkTeacherProfiles() {
        val teacherList = SampleData.teachers
        viewModelScope.launch {
            try {
                userRepository.insertAllTeacherProfileStream(teacherList)
            } catch (ex: Exception) {
                Log.e("HomeViewModel", "Lỗi khi chèn teacher profiles", ex)
            }
        }
    }

    fun insertBulkCourses() {
        val courseList = SampleData.courses
        viewModelScope.launch {
            try {
                courseRepository.insertAllCoursesStream(courseList)
            } catch (ex: Exception) {
                Log.e("HomeViewModel", "Lỗi khi chèn course", ex)
            }
        }
    }

    fun insertBulkExperience() {
        val expList = SampleData.experiences
        viewModelScope.launch {
            try {
                userRepository.insertExperienceStream(expList)
            } catch (ex: Exception) {
                Log.e("HomeViewModel", "Lỗi khi chèn exp", ex)
            }
        }
    }
}
