package com.example.educonnect.ui.mentor_screens.home

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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.educonnect.R
import com.example.educonnect.ui.EduViewModelProvider
import com.example.educonnect.ui.authentication.LocalAuthState
import com.example.educonnect.ui.navigation.NavigationDestination
import com.example.educonnect.ui.students_screens.home.BasicTitle
import com.example.educonnect.ui.students_screens.home.CategoryButton
import com.example.educonnect.ui.students_screens.home.CourseList
import com.example.educonnect.ui.students_screens.home.HomeViewModel
import com.example.educonnect.ui.students_screens.home.MentorButton

object MentorHomeDestination : NavigationDestination {
    override val route = "mentor_home"
    override val titleRes = R.string.home_title
}

@Composable
fun MentorHomeScreen(
    innerPadding : PaddingValues,
    navigateToMentorDetails : (String) -> Unit,
    navigateToCourseDetails : (String) -> Unit,
    navigateToCourseScreen : () -> Unit,
    navigateToMentorScreen : () -> Unit,
    viewModel: MentorHomeViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = EduViewModelProvider.Factory
    ),
    navigateToNotificationScreen : () -> Unit,
) {
    val authState = LocalAuthState.current
    val uiState = viewModel.homeUiState.collectAsState()
    LaunchedEffect(authState.currentUserId) {
        viewModel.loadUser(authState.currentUserId)
        viewModel.getCoursesWithTeachersList(authState.currentUserId)
    }

    if (!authState.isLoggedIn) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
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
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = 16.dp,
                        horizontal = 20.dp
                    ),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                MentorBasicTitle(
                    title = "Recent Classes"
                )
                if (uiState.value.courseWithTeacherList.isEmpty()) {
                    CustomYetCreated(
                        icon = R.drawable.library_svgrepo_com,
                        announceText = "No Courses Yet",
                        buttonText = "Create a course"
                    )
                }
                else {
                    LazyColumn {
                        items(
                            items = uiState.value.courseWithTeacherList,
                            key = { it.course.courseId }
                        ) { course ->
                            ClassesList(
                                courseWithTeacher = course
                            )
                        }
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
                MentorBasicTitle(
                    title = "Recent Planning",
                )
                CustomYetCreated(
                    icon = R.drawable.person_svgrepo_com_rounded,
                    announceText = "No Classes Yet",
                    buttonText = "Create a class"
                )
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
                        text = "Have a great teaching day!",
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