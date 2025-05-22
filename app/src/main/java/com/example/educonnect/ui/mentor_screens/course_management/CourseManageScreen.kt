package com.example.educonnect.ui.mentor_screens.course_management

import androidx.annotation.DrawableRes
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.educonnect.R
import com.example.educonnect.data.model.courses.CourseWithTeacher
import com.example.educonnect.data.model.courses.Lesson
import com.example.educonnect.ui.EduViewModelProvider
import com.example.educonnect.ui.authentication.LocalAuthState
import com.example.educonnect.ui.components.CustomAppBar
import com.example.educonnect.ui.navigation.NavigationDestination
import com.example.educonnect.ui.theme.EduConnectTheme

object CourseManageDestination : NavigationDestination {
    override val route = "course_management"
    override val titleRes = R.string.course_manage
}

@Composable
fun CourseManageScreen(
    innerPadding : PaddingValues,
    navigateToCourseManageDetails: (String) -> Unit,
    viewModel : CourseManageViewModel = viewModel(
        factory = EduViewModelProvider.Factory
    )
) {
    val uiState = viewModel.courseUiState.collectAsState()
    val authState = LocalAuthState.current
    LaunchedEffect(authState.currentUserId) {
        viewModel.loadCourseWithTeacher(authState.currentUserId)
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

    Scaffold (
        topBar = {
            CustomAppBar(
                title = "My Planning",
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
                .background(Color.White)
                .fillMaxSize()
                .padding(
                    start = 20.dp,
                    end = 20.dp,
                    top = it.calculateTopPadding(),
                    bottom = it.calculateBottomPadding()
                )
                .padding(innerPadding)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "${uiState.value.courseWithTeacherList.size} " + if (uiState.value.courseWithTeacherList.size > 1) "courses" else "course",
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 16.sp
                )
                TextButton(onClick = { /*TODO*/ }) {
                    Text(
                        "Sort : Name",
                        style = MaterialTheme.typography.titleMedium,
                        color = Color(0xFF0961F5),
                        fontSize = 16.sp
                    )
                }
            }
            LazyColumn {
                items(
                    items = uiState.value.courseWithTeacherList,
                    key = { it.course.courseId }
                ) { course ->
                    CourseItem(
                        courseWithTeacher = course,
                        lessonCount = uiState.value.lessonCounts,
                        navigateToCourseManageDetails = navigateToCourseManageDetails
                    )
                }
            }
        }
    }
}

@Composable
private fun CourseItem(
    courseWithTeacher: CourseWithTeacher,
    lessonCount : Int,
    navigateToCourseManageDetails : (String) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navigateToCourseManageDetails(courseWithTeacher.course.courseId)
            },
        border = BorderStroke(
            color = Color(0xFF0961F5),
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
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Box (
                modifier = Modifier
                    .background(
                        color = Color(0xFF0961F5).copy(
                            alpha = 0.1f
                        ),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(4.dp)
                    .weight(0.6f)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.graduate_cap_svgrepo_com),
                    contentDescription = "Course item",
                    tint = Color(0xFF0961F5),
                    modifier = Modifier.padding(8.dp)
                )
            }
            Text(
                courseWithTeacher.course.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.W700,
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.weight(1f))
            Text(
                "$lessonCount lessons",
                style = MaterialTheme.typography.titleMedium,
                fontSize = 16.sp
            )
        }
    }
}

//@Composable
//@Preview
//private fun CourseManagePreview() {
//    EduConnectTheme {
//        CourseManageScreen()
//    }
//}