package com.example.educonnect.ui.mentor

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.educonnect.data.database.repositories.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class TopMentorViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    val mentorUiState : StateFlow<MentorUiState> =
        userRepository.getAllTeacherProfile().map { MentorUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = MentorUiState()
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}