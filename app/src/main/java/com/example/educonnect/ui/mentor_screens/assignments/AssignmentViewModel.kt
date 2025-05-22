package com.example.educonnect.ui.mentor_screens.assignments

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.educonnect.data.database.repositories.AssignmentRepository
import com.example.educonnect.data.model.courses.Assignment
import com.example.educonnect.ui.mentor_screens.planning.PlanningDestination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class AssignmentViewModel(
    savedStateHandle: SavedStateHandle,
    private val assignmentRepository: AssignmentRepository
) : ViewModel() {
    private var _assignmentUiState = MutableStateFlow(AssignmentUiState())
    val assignmentUiState : StateFlow<AssignmentUiState> = _assignmentUiState.asStateFlow()

    private val courseId : String = checkNotNull(savedStateHandle[PlanningDestination.courseIdArg])

    init {
        loadAssignments()
    }

    private fun loadAssignments() {
        viewModelScope.launch {
            try {
                assignmentRepository.getAssignmentsByCourseStream(courseId).collect { assignments ->
                    _assignmentUiState.update { currentState ->
                        currentState.copy(
                            assignments = assignments
                        )
                    }
                }
            } catch (e : Exception) {
                Log.e("ASSIGNMENT_VIEWMODEL", "Lỗi load assignment ", e)
            }
        }
    }

    fun addAssignment(
        title : String,
        description : String,
        deadline : LocalDateTime?,
        type : String
    ) {
        viewModelScope.launch {
            try {
                val assignTime = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES)
                val index = getNextAssignmentNumber()
                assignmentRepository.insertAssignmentStream(
                    Assignment(
                        assignmentId = "$courseId-ASM-$type-$index",
                        courseId = courseId,
                        title = title,
                        description = description,
                        assignTime = assignTime,
                        deadline = deadline!!,
                        type = type
                    )
                )
            } catch (e : Exception) {
                Log.e("ASSIGNMENT_VIEWMODEL", "Lỗi add assignment ", e)
            }
        }
    }

    private suspend fun getNextAssignmentNumber(): Int {
        val currentAssignments = assignmentRepository.getAssignmentsByCourseStream(courseId).first()
        return currentAssignments.size + 1
    }
}

data class AssignmentUiState(
    val assignments : List<Assignment> = emptyList()
)