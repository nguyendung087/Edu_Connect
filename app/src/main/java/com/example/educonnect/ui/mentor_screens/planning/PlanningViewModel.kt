package com.example.educonnect.ui.mentor_screens.planning

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel

class PlanningViewModel(
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    var uiState by mutableStateOf(PlanningUiState())
        private set

    private val courseId : String = checkNotNull(savedStateHandle[PlanningDestination.courseIdArg])

    init {
        uiState.courseId = courseId
    }
}

data class PlanningUiState(
    var courseId : String = ""
)