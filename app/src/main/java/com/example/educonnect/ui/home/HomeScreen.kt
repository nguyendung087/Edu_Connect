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
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.runtime.Composable
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
import com.example.educonnect.ui.navigation.NavigationDestination

object HomeDestination : NavigationDestination {
    override val route = "home"
    override val titleRes = R.string.home_title
}

@Composable
fun HomeScreen(
    innerPadding : PaddingValues = PaddingValues(0.dp),
    navigateToMentorDetails : () -> Unit,
    navigateToCourseDetails : () -> Unit,
    navigateToNotificationScreen : () -> Unit
) {
    Scaffold(
        topBar = {
            HomeAppBar(
                navigateToNotificationScreen = navigateToNotificationScreen
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .padding(
                    vertical = 12.dp,
                    horizontal = 12.dp
                )
                .background(Color.White),

            ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = 16.dp,
                        horizontal = 8.dp
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
                        horizontal = 8.dp
                    ),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                BasicTitle(
                    title = "Popular Courses"
                )
                LazyRow {
                    item {
                        CourseList(
                            navigateToCourseDetails = navigateToCourseDetails
                        )
                    }
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = 16.dp,
                        horizontal = 8.dp
                    ),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                BasicTitle(
                    title = "Top Mentor"
                )
                LazyRow {
                    item {
                        MentorButton(
                            modifier = Modifier
                                .padding(end = 24.dp)
                                .size(70.dp)
                                .clickable {
                                    navigateToMentorDetails()
                                },
                        )
                    }
                }

            }
        }
    }
}

@Composable
private fun HomeAppBar(
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
                    Text(
                        text = "Hi, John 👋",
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 26.sp,
                        color = Color.White,
                        fontWeight = FontWeight.W700
                    )
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
//    HomeScreen()
//}