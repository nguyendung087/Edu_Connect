package com.example.educonnect.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.educonnect.R
import com.example.educonnect.data.model.users.StudentProfile
import com.example.educonnect.data.model.users.User
import com.example.educonnect.ui.EduViewModelProvider
import com.example.educonnect.ui.authentication.LocalAuthState
import com.example.educonnect.ui.navigation.NavigationDestination

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.home_title
}

@Composable
fun HomeScreen(
    innerPadding : PaddingValues,
    navigateToMentorDetails : (String) -> Unit,
    navigateToCourseDetails : (String) -> Unit,
    navigateToCourseScreen : () -> Unit,
    navigateToMentorScreen : () -> Unit,
    viewModel: HomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = EduViewModelProvider.Factory
    ),
    navigateToNotificationScreen : () -> Unit,
) {
    val authState = LocalAuthState.current
    val uiState = viewModel.homeUiState.collectAsState()
    LaunchedEffect(authState.currentUserId) {
        viewModel.loadUser(authState.currentUserId)
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
    Scaffold(
        topBar = {
            HomeAppBar(
                currentUser = "Hi, ${uiState.value.currentUser?.name ?: "Guest"} ",
                navigateToNotificationScreen = navigateToNotificationScreen
            )
        },
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = it.calculateTopPadding(),
                    bottom = it.calculateBottomPadding()
                )
                .padding(innerPadding)
                .background(Color.White),

            ) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            vertical = 16.dp,
                            horizontal = 20.dp
                        ),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    BasicTitle(
                        title = "Categories"
                    )
                    LazyRow {
                        item {
                            CategoryButton(
                                modifier = Modifier
                                    .padding(end = 24.dp)
                                    .background(
                                        color = Color(0xFFF4F6F9),
                                        shape = RoundedCornerShape(100.dp)
                                    )
                                    .size(70.dp),
                            )
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            vertical = 16.dp,
                            horizontal = 20.dp
                        ),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    BasicTitle(
                        title = "Popular Courses",
                        modifier = Modifier.clickable {
                            navigateToCourseScreen()
                        }
                    )
                    LazyRow {
                        items(
                            items = uiState.value.courseWithTeacherList,
                            key = { it.course.courseId }
                        ) { course ->
                            CourseList(
                                modifier = Modifier
                                    .padding(
                                        end = 16.dp
                                    )
                                    .clickable {
                                        navigateToCourseDetails(course.course.courseId)
                                    }
                                    .weight(1f),
                                courseWithTeacher = course,
                            )
                        }
                    }
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            vertical = 16.dp,
                            horizontal = 20.dp
                        ),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    BasicTitle(
                        title = "Top Mentor",
                        modifier = Modifier.clickable {
                            navigateToMentorScreen()
                        }
                    )
                    LazyRow {
                        items(
                            items = uiState.value.mentorList,
                            key = {
                                it.teacherId
                            }
                        ) { teacher ->
                            MentorButton(
                                modifier = Modifier
                                    .padding(end = 24.dp)
                                    .size(70.dp)
                                    .clickable {
                                        navigateToMentorDetails(teacher.teacherId)
                                    },
                                mentor = teacher,
                            )
                        }
                    }

                }
            }

        }
    }
}

@Composable
private fun HomeAppBar(
    currentUser : String,
    navigateToNotificationScreen: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = Color(0xFF0961F5),
                shape = RoundedCornerShape(
                    topStart = 0.dp,
                    topEnd = 0.dp,
                    bottomStart = 16.dp,
                    bottomEnd = 16.dp,
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 16.dp,
                    horizontal = 12.dp
                ),
            verticalArrangement = Arrangement.spacedBy(18.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Row {
                        Text(
                            text = currentUser,
                            style = MaterialTheme.typography.bodyLarge,
                            fontSize = 26.sp,
                            color = Color.White,
                            fontWeight = FontWeight.W700
                        )
                        Icon(
                            painter = painterResource(R.drawable.waving_hand_svgrepo_com),
                            tint = Color.Unspecified,
                            contentDescription = null,
                            modifier = Modifier.size(25.dp)
                        )
                    }
                    Text(
                        text = "Let's start learning!",
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 18.sp,
                        color = Color.White,
                        fontWeight = FontWeight.W400
                    )
                }
                NotificationButton(
                    navigateToNotificationScreen = navigateToNotificationScreen,
                    modifier = Modifier
                        .background(
                            color = Color.White.copy(
                                alpha = 0.2f
                            ),
                            shape = MaterialTheme.shapes.medium
                        )
                        .align(Alignment.Top)
                        .size(45.dp)
                )
            }
            SearchBar()
        }
    }
}


@Composable
private fun NotificationButton(
    modifier: Modifier = Modifier,
    navigateToNotificationScreen: () -> Unit
) {
    Box(
        modifier = modifier

    ) {
        IconButton(
            onClick = navigateToNotificationScreen
        ) {
            Icon(
                Icons.Rounded.Notifications,
                modifier = Modifier.size(28.dp),
                tint = Color.White,
                contentDescription = "Notification"
            )
        }
    }
}

@Composable
private fun SearchBar() {
    var searchText by remember { mutableStateOf("") }

    TextField(
        value = searchText,
        onValueChange = { searchText = it },

        placeholder = { Text("Search", color = Color.Gray) },
        leadingIcon = {
            Icon(
                Icons.Rounded.Search,
                tint = Color(0xFF0961F5),
                contentDescription = "Search"
            )
        },
        trailingIcon = {
            Icon(
                painter = painterResource(R.drawable.options_svgrepo_com),
                tint = Color(0xFF0961F5),
                contentDescription = "Search Filter"
            )
        },
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.DarkGray,
            focusedContainerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedContainerColor = Color.White,
            errorLabelColor = Color.Red,
            unfocusedIndicatorColor = Color.Transparent
        ),
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
            .height(68.dp)
            .padding(8.dp)
    )
}

//@Composable
//@Preview
//private fun HomePreview() {
//    HomeScreen(
//        navigateToMentorDetails = {},
//        navigateToCourseDetails = {},
//        navigateToNotificationScreen = {}
//    )
//}