package com.example.educonnect.ui.mentor_screens.course_management

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonColors
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.educonnect.R
import com.example.educonnect.data.model.courses.Lesson
import com.example.educonnect.ui.EduViewModelProvider
import com.example.educonnect.ui.authentication.LocalAuthState
import com.example.educonnect.ui.components.CustomAppBar
import com.example.educonnect.ui.navigation.NavigationDestination
import com.example.educonnect.ui.signup.CustomTextField
import com.example.educonnect.ui.students_screens.courses.CourseDetailsDestination
import kotlinx.coroutines.launch

object CourseManageDetailsDestination : NavigationDestination {
    override val route = "course_management_details"
    override val titleRes = R.string.course_manage
    const val courseIdArg = "courseId"
    val routeWithArgs = "$route/{$courseIdArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseManageDetailsScreen(
    innerPadding : PaddingValues,
    onNavigateBack : () -> Unit,
    viewModel: CourseManageDetailsViewModel = viewModel(
        factory = EduViewModelProvider.Factory
    )
) {
    val uiState = viewModel.courseUiState.collectAsState()
    val authState = LocalAuthState.current
    val coroutineScope = rememberCoroutineScope()

    if (!authState.isLoggedIn) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Vui lòng đăng nhập để tiếp tục",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 18.sp,
                color = Color.Black
            )
        }
        return
    }

    var showAddLessonConfirmation by remember { mutableStateOf(false) }
    var sheetState = rememberModalBottomSheetState()

    var title by remember {
        mutableStateOf("")
    }
    var content by remember {
        mutableStateOf("")
    }

    var isExpanded by remember {
        mutableStateOf(false)
    }
    var lessonToEdit by remember {
        mutableStateOf<Lesson?>(null)
    }
    var lessonToDelete by remember {
        mutableStateOf<Lesson?>(null)
    }

    val isFormValid = title.isNotBlank() && content.isNotBlank()

    Scaffold (
        topBar = {
            CustomAppBar(
                title = "",
                hasActions = true,
                navigationOnClick = onNavigateBack,
                actionOnClick = {



                },
                icon = R.drawable.more_vertical_svgrepo_com,
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
                .fillMaxSize()
                .padding(
                    top = it.calculateTopPadding(),
                    bottom = it.calculateBottomPadding()
                )
                .padding(innerPadding)
                .background(Color.White),
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "${uiState.value.lessons.size} Lessons",
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 16.sp
                )
                TextButton(
                    onClick = {
                        showAddLessonConfirmation = true
                    }
                ) {
                    Icon(
                        Icons.Default.Add,
                        contentDescription = "Add Lesson",
                        tint = Color(0xFF0961F5)
                    )
                    Text(
                        " Add Lesson",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color(0xFF0961F5),
                        fontSize = 16.sp
                    )
                }
            }
            LazyColumn(
                modifier = Modifier
                    .animateContentSize(
                        animationSpec = spring(
                            dampingRatio = Spring.DampingRatioNoBouncy,
                            stiffness = Spring.StiffnessMedium
                        )
                    )
            ) {
                items(
                    items = uiState.value.lessons,
                    key = { it.lessonId },

                ) { lesson ->
                    val index = uiState.value.lessons.indexOf(lesson) + 1
                    LessonItem(
                        index = index.inc(),
                        lesson = lesson,
                        onExpandClick = {
                            isExpanded = !isExpanded
                        }
                    )
                    if(isExpanded) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            TextButton(onClick = {
                                lessonToEdit = lesson
                                title = lesson.title
                                content = lesson.content
                                isExpanded = false
                            }) {
                                Text("Edit")
                            }
                            TextButton(onClick = {
                                lessonToDelete = lesson
                                isExpanded = false
                            }) {
                                Text("Delete")
                            }
                        }
                    }
                }
            }
        }
    }

    if (showAddLessonConfirmation) {
        ModalBottomSheet(
            containerColor = Color.White,
            onDismissRequest = {
                showAddLessonConfirmation = false
            },
            sheetState = sheetState,
        ) {
            AddLessonBottomSheet(
                title = title,
                content = content,
                isFormValid = isFormValid,
                onTitleChange = {
                    title = it
                },
                onContentChange = {
                    content = it
                },
                onConfirm = {
                    viewModel.addLesson(
                        title = title,
                        content = content
                    )
                    showAddLessonConfirmation = false
                },
                onCancel = {
                    showAddLessonConfirmation = false
                }
            )
        }
    }

    if (lessonToDelete != null) {
        AlertDialog(
            onDismissRequest = { lessonToDelete = null },
            title = { Text("Xác nhận xóa") },
            text = { Text("Bạn có chắc chắn muốn xóa bài học này không?") },
            confirmButton = {
                TextButton(onClick = {
                    viewModel.removeLesson(lessonToDelete!!.lessonId)
                    lessonToDelete = null
                }) {
                    Text("Xóa")
                }
            },
            dismissButton = {
                TextButton(onClick = { lessonToDelete = null }) {
                    Text("Hủy")
                }
            }
        )
    }

    if (lessonToEdit != null) {
        ModalBottomSheet(
            containerColor = Color.White,
            onDismissRequest = { lessonToEdit = null },
            sheetState = sheetState,
        ) {
            EditLessonBottomSheet(
                title = title,
                content = content,
                onTitleChange = {
                    title = it
                },
                onContentChange = {
                    content = it
                },
                onConfirm = {
                    val updatedLesson = lessonToEdit!!.copy(
                        title = title,
                        content = content
                    )
                    viewModel.updateUiState(updatedLesson)
                    coroutineScope.launch {
                        viewModel.updateLesson()
                    }
                    lessonToEdit = null
                },
                onCancel = { lessonToEdit = null }
            )
        }
    }
}

@Composable
private fun LessonItem(
    index : Int,
    lesson: Lesson,
    onExpandClick : () -> Unit
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
            val unit = "Lesson $index: "
            val courseTitle = lesson.title
            Text(
                text = buildAnnotatedString {
                    append(unit)
                    pushStringAnnotation(tag = "course_title", annotation = "course_title")
                    withStyle(style = SpanStyle(
                        color = Color(0xFF0961F5),
                        fontWeight = FontWeight.W500
                    )) {
                        append(courseTitle)
                    }
                    pop()
                },
                modifier = Modifier.weight(1f)
            )
            Icon(
                painter = painterResource(id = R.drawable.more_vertical_svgrepo_com),
                contentDescription = "More Vertical",
                Modifier
                    .weight(0.2f)
                    .size(25.dp)
                    .clickable {
                    onExpandClick()
                },
            )
        }
    }
}

@Composable
private fun AddLessonBottomSheet(
    title : String,
    content : String,
    isFormValid : Boolean,
    onTitleChange : (String) -> Unit,
    onContentChange : (String) -> Unit,
    onConfirm : () -> Unit,
    onCancel : () -> Unit
) {
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

        CustomTextField(
            title = R.string.lesson_content,
            value = content,
            onValueChange = onContentChange,
            visualTransformation = VisualTransformation.None,
            placeholder = {
                Text(
                    "Mời nhập nội dung",
                    style = MaterialTheme.typography.titleSmall,
                    color = Color.Black.copy(
                        alpha = 0.3f
                    )
                )
            },
            leadingIcon = { },
            trailingIcon = { }
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
                    "Thêm",
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

@Composable
private fun EditLessonBottomSheet(
    title: String,
    content: String,
    onTitleChange : (String) -> Unit,
    onContentChange : (String) -> Unit,
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    val isFormValid = title.isNotBlank() && content.isNotBlank()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, bottom = 24.dp)
            .background(Color.White),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomTextField(
            title = R.string.lesson_title,
            value = title,
            onValueChange = onTitleChange,
            visualTransformation = VisualTransformation.None,
            placeholder = {},
            leadingIcon = {},
            trailingIcon = {}
        )

        CustomTextField(
            title = R.string.lesson_content,
            value = content,
            onValueChange = onContentChange,
            visualTransformation = VisualTransformation.None,
            placeholder = {},
            leadingIcon = {},
            trailingIcon = {}
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp),
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
                    "Lưu",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }

            TextButton(
                colors = ButtonColors(
                    containerColor = Color.Black.copy(alpha = 0.3f),
                    contentColor = Color.Black.copy(alpha = 0.8f),
                    disabledContainerColor = Color.Black.copy(alpha = 0.3f),
                    disabledContentColor = Color.Black.copy(alpha = 0.8f)
                ),
                onClick = onCancel,
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    "Hủy",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(vertical = 8.dp)
                )
            }
        }
    }
}