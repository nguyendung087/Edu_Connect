package com.example.educonnect.ui.mentor

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.educonnect.data.database.repositories.UserRepository
import com.example.educonnect.data.model.users.Experience
import com.example.educonnect.data.model.users.TeacherProfile
import com.example.educonnect.ui.courses.CourseDetailsUiState
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

class MentorDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val userRepository: UserRepository
) : ViewModel() {

    private var _mentorUiState = MutableStateFlow(MentorDetailsUiState())
    val mentorUiState : StateFlow<MentorDetailsUiState> = _mentorUiState.asStateFlow()

    private val teacherId : String = checkNotNull(savedStateHandle[MentorDetailsDestination.mentorIdArg])

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            try {
                val mentorDeferred = async { getTeacherProfile() }
                val experienceDeferred = async { getExperience() }
                mentorDeferred.await()
                experienceDeferred.await()
            } catch (e: Exception) {
                Log.e("FETCH_DATA", "Lỗi khi lấy data: $e")
            }
        }
    }

    private suspend fun getTeacherProfile() {
        userRepository.getTeacherProfileStream(teacherId).collect { mentor ->
            _mentorUiState.update { currentState ->
                currentState.copy(
                    mentor = mentor
                )
            }
        }
    }

    private suspend fun getExperience() {
        userRepository.getExperiencesByTeacherStream(teacherId).collect { exp ->
            _mentorUiState.update { currentState ->
                currentState.copy(
                    exp = exp
                )
            }
        }
    }


    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class MentorDetailsUiState(
    val mentor : TeacherProfile = TeacherProfile(),
    val exp : List<Experience> = emptyList()
)