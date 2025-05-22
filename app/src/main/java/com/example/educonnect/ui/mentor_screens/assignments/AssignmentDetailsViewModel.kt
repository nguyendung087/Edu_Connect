package com.example.educonnect.ui.mentor_screens.assignments

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.educonnect.data.database.repositories.AssignmentRepository
import com.example.educonnect.data.database.repositories.SubmissionRepository
import com.example.educonnect.data.model.courses.Assignment
import com.example.educonnect.data.model.courses.Lesson
import com.example.educonnect.data.model.courses.SubmissionWithStudent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AssignmentDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val submissionRepository: SubmissionRepository,
    private val assignmentRepository: AssignmentRepository
) : ViewModel() {
    private var _assignmentUiState = MutableStateFlow(AssignmentDetailsUiState())
    val assignmentUiState : StateFlow<AssignmentDetailsUiState> = _assignmentUiState.asStateFlow()

    private val assignmentId : String = checkNotNull(savedStateHandle[AssignmentDetailsDestination.assignmentIdArg])

    init {
        loadAssignment()
        loadSubmissionWithStudents()
    }

    private fun loadAssignment() {
        viewModelScope.launch {
            try {
                assignmentRepository.getAssignmentByIdStream(assignmentId).collect { assignment ->
                    _assignmentUiState.update { currentUiState ->
                        currentUiState.copy(
                            assignment = assignment!!
                        )
                    }
                }
            } catch (e : Exception) {
                Log.e("ASSIGNMENT_DETAILS_VIEWMODEL", "Lỗi load assignment ", e)
            }
        }
    }

    private fun loadSubmissionWithStudents() {
        viewModelScope.launch {
            try {
                submissionRepository.getSubmissionWithStudentStream(assignmentId).collect { submissions ->
                    _assignmentUiState.update { currentState ->
                        currentState.copy(
                            submissionWithStudent = submissions
                        )
                    }
                }
            } catch (e : Exception) {
                Log.e("ASSIGNMENT_DETAILS_VIEWMODEL", "Lỗi load submission ", e)
            }
        }
    }

    private fun updateAssignment() {
        viewModelScope.launch {
            _assignmentUiState.value.let {
                assignmentRepository.updateAssignmentStream(it.assignment)
            }
        }
    }

    fun deleteAssignment() {
        viewModelScope.launch {
            try {
                assignmentRepository.deleteAssignmentStream(
                    Assignment(
                        assignmentId = assignmentId
                    )
                )
            } catch (e : Exception) {
                Log.e("REMOVE_ASSIGNMENT", "Lỗi khi xóa assignment", e)
            }
        }
    }

    fun updateUiState(assignment: Assignment) {
        _assignmentUiState.update { currentState ->
            currentState.copy(
                assignment = assignment
            )
        }
    }

    suspend fun updateLesson() {
        _assignmentUiState.value.assignment.let {
            assignmentRepository.updateAssignmentStream(it)
        }
    }
}

data class AssignmentDetailsUiState(
    val submissionWithStudent: List<SubmissionWithStudent> = emptyList() ,
    val assignment: Assignment = Assignment()
)