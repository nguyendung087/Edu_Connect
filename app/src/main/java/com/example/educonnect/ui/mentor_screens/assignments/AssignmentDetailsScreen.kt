package com.example.educonnect.ui.mentor_screens.assignments

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.educonnect.R
import com.example.educonnect.data.model.courses.Assignment
import com.example.educonnect.data.model.courses.Lesson
import com.example.educonnect.data.model.courses.Submission
import com.example.educonnect.data.model.courses.SubmissionWithStudent
import com.example.educonnect.ui.EduViewModelProvider
import com.example.educonnect.ui.authentication.LocalAuthState
import com.example.educonnect.ui.components.CustomAppBar
import com.example.educonnect.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch
import java.time.LocalDateTime

object AssignmentDetailsDestination : NavigationDestination {
    override val route = "assignment_details"
    override val titleRes = R.string.assignment
    const val assignmentIdArg = "assignmentId"
    val routeWithArgs = "$route/{$assignmentIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssignmentDetailsScreen(
    navigateUp : () -> Unit,
    viewModel : AssignmentDetailsViewModel = viewModel(
        factory = EduViewModelProvider.Factory
    )
) {
    val uiState = viewModel.assignmentUiState.collectAsState()
    val coroutineScope = rememberCoroutineScope()

    val currentAssignment = uiState.value.assignment
    var title by remember {
        mutableStateOf("")
    }
    var description by remember {
        mutableStateOf("")
    }
    var category by remember {
        mutableStateOf("")
    }
    var selectedDateTime by remember { mutableStateOf<LocalDateTime?>(null) }
    var expanded by remember { mutableStateOf(false) }

    var showAddBottomSheet by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    var assignmentToEdit by remember {
        mutableStateOf<Assignment?>(null)
    }
    var assignmentToDelete by remember {
        mutableStateOf<Assignment?>(null)
    }

    Scaffold (
        topBar = {
            CustomAppBar(
                title = uiState.value.assignment.title,
                hasActions = true,
                navigationOnClick = navigateUp,
                icon = R.drawable.more_vertical_svgrepo_com,
                actionOnClick = {
                    showAddBottomSheet = true
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 20.dp,
                        vertical = 16.dp
                    ),
            )
        }
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
                .padding(
                    start = 20.dp,
                    end = 20.dp,
                    top = it.calculateTopPadding(),
                    bottom = it.calculateBottomPadding()
                )
        ) {
            LazyColumn {
                items(
                    items = uiState.value.submissionWithStudent,
                    key = { it.submission.submissionId }
                ) { submission ->
                    StudentSubmissionItem(
                        submissionWithStudent = submission
                    )
                }
            }
        }
    }

    if (showAddBottomSheet) {
        ModalBottomSheet(
            containerColor = Color.White,
            onDismissRequest = { assignmentToEdit = null },
            sheetState = sheetState,
        ) {
            OptionsBottomSheet(
                option1 = "Edit Assignment",
                option2 = "Delete Assignment",
                onOption1Click = {
                    assignmentToEdit = currentAssignment
                    title = currentAssignment.title
                    category = currentAssignment.type
                    description = currentAssignment.description
                    selectedDateTime = currentAssignment.deadline
                    showAddBottomSheet = false
                },
                onOption2Click = {
                    assignmentToDelete = currentAssignment
                    showAddBottomSheet = false
                }
            )
        }
    }

    if (assignmentToDelete != null) {
        AlertDialog(
            onDismissRequest = { assignmentToDelete = null },
            title = { Text("Xác nhận xóa") },
            text = { Text("Bạn có chắc chắn muốn xóa bài tập này không?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.deleteAssignment()
                        navigateUp()
                    },
                ) {
                    Text("Xóa")
                }
            },
            dismissButton = {
                TextButton(onClick = { assignmentToDelete = null }) {
                    Text("Hủy")
                }
            }
        )
    }

    if (assignmentToEdit != null) {
        ModalBottomSheet(
            containerColor = Color.White,
            onDismissRequest = { assignmentToEdit = null },
            sheetState = sheetState,
        ) {
            AddAssignmentBottomSheet(
                title = title,
                type = category,
                expanded = expanded,
                description = description,
                deadline = selectedDateTime,
                confirmText = "Lưu",
                onTitleChange = {
                    title = it
                },
                onTypeChange = {
                    category = it
                },
                onExpandedChange = {
                    expanded = !expanded
                },
                onDismissRequest = {
                    expanded = false
                },
                onClick = {
                    category = it
                    expanded = false
                },
                onDescriptionChange = {
                    description = it
                },
                onDeadlineChange = {
                    selectedDateTime = it
                },
                onConfirm = {
                    val updatedAssignment = assignmentToEdit!!.copy(
                        title = title,
                        type = category,
                        description = description,
                        deadline = selectedDateTime!!,
                    )
                    viewModel.updateUiState(updatedAssignment)
                    coroutineScope.launch {
                        viewModel.updateLesson()
                    }
                    assignmentToEdit = null
                },
                onCancel = {
                    assignmentToEdit = null
                }
            )
        }
    }
}

@Composable
private fun StudentSubmissionItem(
    submissionWithStudent: SubmissionWithStudent
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = 5.dp
            )
            .clickable {

            },
        border = BorderStroke(
            color = Color.Black.copy(
                alpha = 0.5f
            ),
            width = 1.dp,
        ),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = submissionWithStudent.student.avatarUrl.ifBlank { R.drawable.person_crop_circle_fill_svgrepo_com }
                ),
                contentDescription = submissionWithStudent.student.name,
                modifier = Modifier.weight(0.5f)
            )
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    submissionWithStudent.student.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W500,
                )
                Text(
                    submissionWithStudent.student.studentId,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W400,
                    color = Color.Black.copy(
                        alpha = 0.3f
                    )
                )
            }
        }
    }
}

@Composable
private fun OptionsBottomSheet(
    option1 : String,
    option2 : String,
    onOption1Click : () -> Unit,
    onOption2Click : () -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onOption1Click() },
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.Create,
                contentDescription = "Edit",
                tint = Color.Black
            )
            Text(
                option1,
                style = MaterialTheme.typography.titleMedium,
                fontSize = 16.sp,
                color = Color.Black
            )
        }

        Row (
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onOption2Click() },
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Default.Delete,
                contentDescription = "Delete",
                tint = Color.Black
            )
            Text(
                option2,
                style = MaterialTheme.typography.titleMedium,
                fontSize = 16.sp,
                color = Color.Black
            )
        }
    }
}