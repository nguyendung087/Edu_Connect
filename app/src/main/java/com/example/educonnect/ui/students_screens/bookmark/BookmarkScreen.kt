package com.example.educonnect.ui.students_screens.bookmark

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.educonnect.R
import com.example.educonnect.data.model.courses.CourseWithTeacher
import com.example.educonnect.ui.EduViewModelProvider
import com.example.educonnect.ui.authentication.LocalAuthState
import com.example.educonnect.ui.components.ConfirmationNotification
import com.example.educonnect.ui.components.CustomAppBar
import com.example.educonnect.ui.navigation.NavigationDestination

object BookmarkDestination : NavigationDestination {
    override val route = "bookmark"
    override val titleRes = R.string.bookmark_title
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BookmarkScreen(
    innerPadding : PaddingValues,
    viewModel: BookmarkViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = EduViewModelProvider.Factory
    )
) {
    val authState = LocalAuthState.current
    val uiState = viewModel.bookmarkUiState.collectAsState()
    LaunchedEffect(authState.currentUserId) {
//        viewModel.updateUserId(authState.currentUserId)
        viewModel.loadBookmarkedCourses(authState.currentUserId)
    }

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

    var showRemoveConfirmation by remember { mutableStateOf(false) }
    var courseToRemove by remember { mutableStateOf<CourseWithTeacher?>(null) }
    val sheetState = rememberModalBottomSheetState()
    Scaffold(
        topBar = {
            CustomAppBar(
                title = "Bookmark",
                hasActions = false,
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
                .padding(
//                    start = 12.dp,
//                    end = 12.dp,
//                    bottom = innerPadding.calculateBottomPadding(),
//                    top = innerPadding.calculateTopPadding()
                    innerPadding
                )
        ) {
            CourseTagList()
            LazyColumn(
                modifier = Modifier
                    .background(
                        Color.White
                    )
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                items(
                    items = uiState.value.bookmarkedCourses,
                    key = { it.course.courseId }
                ) { course ->
                    BookmarkItem(
                        bookmarkedCourse = course,
                        onShowRemoveConfirmation = { selectedCourse ->
                            courseToRemove = selectedCourse
                            showRemoveConfirmation = true
                        }
                    )
                }
            }
        }
    }

    if (showRemoveConfirmation && courseToRemove != null) {
        ModalBottomSheet(
            onDismissRequest = { showRemoveConfirmation = false },
            containerColor = Color.White,
            sheetState = sheetState
        ) {
            ConfirmationNotification(
                title = "Bạn xác nhận muốn xóa khỏi Bookmark?",
                course = courseToRemove!!,
                onConfirm = {
                    viewModel.removeBookmark(
                        authState.currentUserId!!,
                        courseToRemove!!.course.courseId
                    )
                    showRemoveConfirmation = false
                    courseToRemove = null
                },
                onCancel = {
                    showRemoveConfirmation = false
                    courseToRemove = null
                }
            )
        }
    }
}
