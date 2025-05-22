package com.example.educonnect.ui.mentor_screens.assignments

import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerState
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.educonnect.R
import com.example.educonnect.data.LocalAssignmentCategoryData
import com.example.educonnect.data.LocalCourseCategoryData
import com.example.educonnect.data.model.courses.Assignment
import com.example.educonnect.data.model.courses.Lesson
import com.example.educonnect.ui.EduViewModelProvider
import com.example.educonnect.ui.navigation.NavigationDestination
import com.example.educonnect.ui.permissions.AppScreen
import com.example.educonnect.ui.signup.CustomTextField
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

object AssignmentManageDestination : NavigationDestination {
    override val route = "assignments"
    override val titleRes = R.string.assignment
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AssignmentsTabs(
    navigateToAssignmentDetails : (String) -> Unit,
    viewModel: AssignmentViewModel = viewModel(
        factory = EduViewModelProvider.Factory
    )
) {
    val uiState = viewModel.assignmentUiState.collectAsState()


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

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "${uiState.value.assignments.size} " + if (uiState.value.assignments.size > 1) "Assignments" else "Assignment",
                style = MaterialTheme.typography.titleMedium,
                fontSize = 16.sp
            )
            TextButton(
                onClick = {
                    showAddBottomSheet = true
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.note_text_plus_line_svgrepo_com),
                    contentDescription = "Add Assignment",
                    tint = Color(0xFF0961F5),
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    " New Assignment",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFF0961F5),
                    fontSize = 16.sp
                )
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(
                items = uiState.value.assignments,
                key = { it.assignmentId },
            ) { assignment ->
                AssignmentItem(
                    assignment = assignment,
                    onClick = navigateToAssignmentDetails
                )
            }
        }
    }

    if (showAddBottomSheet) {
        ModalBottomSheet(
            containerColor = Color.White,
            onDismissRequest = {
                showAddBottomSheet = false
            },
            sheetState = sheetState,
        ) {
            AddAssignmentBottomSheet(
                title = title,
                type = category,
                expanded = expanded,
                description = description,
                deadline = selectedDateTime,
                confirmText = "Thêm",
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
                    viewModel.addAssignment(
                        title = title,
                        type = category,
                        deadline = selectedDateTime,
                        description = description
                    )
                    showAddBottomSheet = false
                },
                onCancel = {
                    showAddBottomSheet = false
                }
            )
        }
    }
}

@Composable
private fun AssignmentItem(
    assignment: Assignment,
    onClick : (String) -> Unit,
) {
    val color = when (assignment.type) {
        "Quiz" -> Color(0xFF0961F5)
        "Homework" -> Color(0xFF08AC6C)
        "Test" -> Color(0xFF8C00BF)
        else -> Color(0xFFFF800D)
    }

    val displayAssignTime = assignment.assignTime.format(DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy")) ?: ""
    val displayDeadline = assignment.deadline.format(DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy")) ?: ""

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = 5.dp
            )
            .clickable {
                onClick(assignment.assignmentId)
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
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            Text(
                assignment.title,
                style = MaterialTheme.typography.titleMedium,
                fontSize = 16.sp,
                fontWeight = FontWeight.W700
            )
            Text(
                assignment.type,
                fontWeight = FontWeight.W700,
                color = color,
                fontSize = 14.sp,
                modifier = Modifier
                    .background(
                        color = color.copy(
                            alpha = 0.15f
                        ),
                        shape = RoundedCornerShape(100.dp),
                    )
                    .padding(
                        vertical = 5.dp,
                        horizontal = 8.dp
                    )
            )

            Text(
                "Assigned: $displayAssignTime",
                style = MaterialTheme.typography.titleMedium,
                fontSize = 14.sp,
                color = Color.LightGray,
            )
            Text(
                "Deadline: $displayDeadline",
                style = MaterialTheme.typography.titleMedium,
                fontSize = 14.sp,
                color = Color.LightGray,
            )

            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth(),
                trackColor = Color.LightGray,
                color = Color(0xFF0961F5)
            )

            Text(
                "0 of 0 students submitted",
                style = MaterialTheme.typography.titleMedium,
                color = Color.LightGray,
                fontSize = 14.sp
            )
        }

        IconButton(onClick = { /*TODO*/ }) {
            Icons.Default.MoreVert
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddAssignmentBottomSheet(
    title : String,
    type : String,
    expanded : Boolean,
    description : String,
    deadline : LocalDateTime?,
    onTitleChange : (String) -> Unit,
    onTypeChange : (String) -> Unit,
    onExpandedChange : (Boolean) -> Unit,
    onDismissRequest : () -> Unit,
    onClick : (String) -> Unit,
    onDescriptionChange : (String) -> Unit,
    onDeadlineChange : (LocalDateTime?) -> Unit,
    onConfirm : () -> Unit,
    onCancel : () -> Unit,
    confirmText : String
) {
    val isFormValid = title.isNotBlank() &&
            type.isNotEmpty() &&
            description.isNotBlank()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = 16.dp,
                end = 16.dp,
                bottom = 24.dp
            )
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomTextField(
            title = R.string.lesson_title,
            value = title,
            onValueChange = onTitleChange,
            visualTransformation = VisualTransformation.None,
            placeholder = {
                Text(
                    "Mời nhập tiêu đề",
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.Black.copy(
                        alpha = 0.3f
                    )
                )
            },
            leadingIcon = { },
            trailingIcon = { }
        )

        AssignmentCategoryDropdown(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 16.dp
                ),
            title = R.string.category,
            expanded = expanded,
            selectedCategory = type,
            onExpandedChange = onExpandedChange,
            onValueChange = onTypeChange,
            placeholder = {
                Text(
                    stringResource(id = R.string.category),
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.Gray
                )
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            onDismissRequest = onDismissRequest,
            onClick = onClick
        )

        CustomTextField(
            title = R.string.description,
            value = description,
            onValueChange = onDescriptionChange,
            visualTransformation = VisualTransformation.None,
            placeholder = {
                Text(
                    "Mời nhập mô tả",
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.Black.copy(
                        alpha = 0.3f
                    )
                )
            },
            leadingIcon = { },
            trailingIcon = { }
        )

        DeadlineTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
            title = R.string.deadline,
            selectedDateTime = deadline,
            onValueChange = onDeadlineChange,
            visualTransformation = VisualTransformation.None,
            placeholder = {
                Text(
                    stringResource(id = R.string.deadline),
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.Gray,
                )
            },
            leadingIcon = {
                Icon(
                    Icons.Filled.DateRange,
                    contentDescription = null,
                    tint = Color.Black.copy(alpha = 0.7f)
                )
            },
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 16.dp
                ),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            TextButton(
                colors = ButtonColors(
                    containerColor = Color(0xFF0961F5),
                    contentColor = Color.White,
                    disabledContainerColor = Color(0xFF0961F5),
                    disabledContentColor = Color.White
                ),
                enabled = isFormValid,
                onClick = onConfirm,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    confirmText,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(
                        vertical = 8.dp
                    )
                )
            }

            TextButton(
                colors = ButtonColors(
                    containerColor = Color.Black.copy(
                        alpha = 0.3f
                    ),
                    contentColor = Color.Black.copy(
                        alpha = 0.8f
                    ),
                    disabledContainerColor = Color.Black.copy(
                        alpha = 0.3f
                    ),
                    disabledContentColor = Color.Black.copy(
                        alpha = 0.8f
                    )
                ),
                onClick = onCancel,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    "Hủy",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(
                        vertical = 8.dp
                    )
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeadlineTextField(
    @StringRes title: Int,
    selectedDateTime: LocalDateTime?,
    onValueChange: (LocalDateTime?) -> Unit,
    visualTransformation: VisualTransformation,
    placeholder: @Composable () -> Unit,
    leadingIcon: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    val displayDateTime = selectedDateTime?.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) ?: ""

    val initialDateMillis = selectedDateTime?.atZone(ZoneId.systemDefault())?.toInstant()?.toEpochMilli()
        ?: System.currentTimeMillis()
    val datePickerState = rememberDatePickerState(initialSelectedDateMillis = initialDateMillis)

    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(title),
            style = MaterialTheme.typography.titleSmall,
            color = Color.Black.copy(alpha = 0.7f)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = displayDateTime,
                singleLine = true,
                onValueChange = {},
                placeholder = placeholder,
                leadingIcon = leadingIcon,
                visualTransformation = visualTransformation,
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.colors(
                    unfocusedContainerColor = Color(0xFFF4F6F9),
                    disabledTextColor = Color.Gray,
                    focusedContainerColor = Color(0xFFF4F6F9),
                    errorLabelColor = Color.Red.copy(alpha = 0.2f),
                    unfocusedTextColor = Color.Gray,
                    focusedTextColor = Color.Black.copy(alpha = 0.8f),
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                modifier = Modifier.weight(1f)
            )
            IconButton(onClick = { showDatePicker = true }) {
                Icon(
                    imageVector = Icons.Default.DateRange,
                    contentDescription = "Chọn ngày"
                )
            }
            IconButton(onClick = { showTimePicker = true }) {
                Icon(
                    painter = painterResource(id = R.drawable.clock_svgrepo_com),
                    contentDescription = "Chọn giờ",
                    modifier = Modifier.size(25.dp)
                )
            }
        }

        if (showDatePicker) {
            AlertDialog(
                onDismissRequest = { showDatePicker = false },
                title = { Text("Chọn ngày") },
                text = {
                    DatePicker(state = datePickerState)
                },
                confirmButton = {
                    TextButton(onClick = {
                        val selectedDate = datePickerState.selectedDateMillis?.let {
                            Instant.ofEpochMilli(it).atZone(ZoneId.systemDefault()).toLocalDate()
                        }
                        if (selectedDate != null) {
                            val newDateTime = selectedDateTime?.with(selectedDate)
                                ?: LocalDateTime.of(selectedDate, LocalTime.MIDNIGHT)
                            onValueChange(newDateTime)
                        }
                        showDatePicker = false
                    }) {
                        Text("Xác nhận")
                    }
                },
                dismissButton = {
                    TextButton(onClick = { showDatePicker = false }) {
                        Text("Hủy")
                    }
                }
            )
        }

        if (showTimePicker) {
            TimeWheelPickerDialog(
                initialHour = selectedDateTime?.hour ?: LocalTime.now().hour,
                initialMinute = selectedDateTime?.minute ?: LocalTime.now().minute,
                onConfirm = { hour, minute ->
                    val selectedTime = LocalTime.of(hour, minute)
                    val newDateTime = selectedDateTime?.with(selectedTime)
                        ?: LocalDateTime.of(LocalDate.now(), selectedTime)
                    onValueChange(newDateTime)
                    showTimePicker = false
                },
                onDismiss = { showTimePicker = false }
            )
        }
    }
}

@Composable
fun TimeWheelPickerDialog(
    initialHour: Int,
    initialMinute: Int,
    onConfirm: (Int, Int) -> Unit,
    onDismiss: () -> Unit
) {
    var selectedHour by remember { mutableStateOf(initialHour) }
    var selectedMinute by remember { mutableStateOf(initialMinute) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Chọn giờ") },
        text = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .height(150.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items((0..23).toList()) { hour ->
                        Text(
                            text = hour.toString().padStart(2, '0'),
                            color = if (hour == selectedHour) Color.Black else Color.Gray,
                            fontSize = 20.sp,
                            modifier = Modifier
                                .clickable { selectedHour = hour }
                                .padding(8.dp)
                        )
                    }
                }

                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .height(150.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items((0..59).toList()) { minute ->
                        Text(
                            text = minute.toString().padStart(2, '0'),
                            color = if (minute == selectedMinute) Color.Black else Color.Gray,
                            fontSize = 20.sp,
                            modifier = Modifier
                                .clickable { selectedMinute = minute }
                                .padding(8.dp)
                        )
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = {
                onConfirm(selectedHour, selectedMinute)
            }) {
                Text("Xác nhận")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Hủy")
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun AssignmentCategoryDropdown(
    @StringRes title : Int,
    expanded : Boolean,
    selectedCategory : String,
    onExpandedChange : (Boolean) -> Unit,
    onValueChange : (String) -> Unit,
    placeholder : @Composable () -> Unit,
    trailingIcon : @Composable () -> Unit,
    onDismissRequest : () -> Unit,
    onClick : (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            stringResource(title),
            style = MaterialTheme.typography.titleSmall,
            color = Color.Black.copy(
                alpha = 0.7f
            ),
        )
        ExposedDropdownMenuBox(
            expanded = expanded,
            onExpandedChange = onExpandedChange
        ) {
            TextField(
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                placeholder = placeholder,
                singleLine = true,
                value = selectedCategory,
                onValueChange = onValueChange,
                trailingIcon = trailingIcon,
                colors = ExposedDropdownMenuDefaults.textFieldColors()
            )

            ExposedDropdownMenu(
                expanded = expanded,
                onDismissRequest = onDismissRequest
            ) {

                LocalAssignmentCategoryData.assignmentCategories.forEach { assignmentType ->
                    DropdownMenuItem(
                        leadingIcon = {
                            Icon(
                                Icons.Default.Star,
                                contentDescription = null,
                                tint = assignmentType.second
                            )
                        },
                        text = { Text(assignmentType.first) },
                        onClick = {
                            onClick(assignmentType.first)
                        }
                    )
                }
            }
        }
    }
}

//enum class AssignmentType(
//    type : String,
//    color: Color
//) {
//    QUIZ("Quiz", Color(0xFF0961F5)),
//    HOMEWORK("Homework", Color(0xFF08AC6C)),
//    TEST("Test", Color(0xFF8C00BF)),
//    COURSEWORK("Coursework", Color(0xFFFF800D))
//}