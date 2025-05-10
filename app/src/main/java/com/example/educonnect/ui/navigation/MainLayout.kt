package com.example.educonnect.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.educonnect.R
import com.example.educonnect.ui.students_screens.bookmark.BookmarkDestination
import com.example.educonnect.ui.chat.ChatDestination
import com.example.educonnect.ui.students_screens.courses.CourseDestination
import com.example.educonnect.ui.students_screens.home.HomeDestination
import com.example.educonnect.ui.profile.ProfileDestination
import com.example.educonnect.ui.theme.EduConnectTheme

@Composable
fun MainLayout(
    navController: NavHostController,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        bottomBar = {
            if (shouldShowBottomBar(navController)) {
                BottomNavigationBar(navController)
            }
        }
    ) { innerPadding ->
        content(innerPadding)
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        NavigationItem.Home,
        NavigationItem.Courses,
        NavigationItem.Bookmarks,
        NavigationItem.Chat,
        NavigationItem.Profile
    )

    NavigationBar(
        containerColor = Color.White,
        modifier = Modifier
            .clip(
                RoundedCornerShape(
                    topStart = 14.dp,
                    topEnd = 14.dp
                )
            )
            .navigationBarsPadding(),

    ) {
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

        items.forEach { item ->
            NavigationBarItem(
                icon = {
                    Icon(
                        painter = painterResource(item.icon),
                        contentDescription = stringResource(item.title),
                        modifier = Modifier.size(35.dp)
                    )
                },
                colors = NavigationBarItemColors(
                    disabledIconColor = Color.Gray,
                    disabledTextColor = Color.Gray,
                    unselectedTextColor = Color.Gray,
                    unselectedIconColor = Color.Gray,
                    selectedTextColor = Color(0xFF0961F5),
                    selectedIconColor = Color(0xFF0961F5),
                    selectedIndicatorColor = Color(0xFF0961F5).copy(
                        alpha = 0.2f
                    )
                ),
                label = {
                    Text(
                        text = stringResource(item.title),
                        style = MaterialTheme.typography.titleSmall,
                        fontSize = 12.sp
                    )
                },
                selected = currentRoute == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
            )
        }
    }
}

private fun shouldShowBottomBar(navController: NavHostController): Boolean {
    val excludedRoutes = listOf("course_details", "filter")
    return navController.currentBackStackEntry?.destination?.route !in excludedRoutes
}

sealed class NavigationItem(val route: String, @StringRes val title: Int, @DrawableRes val icon: Int) {
    object Home : NavigationItem(HomeDestination.route, R.string.home_title, R.drawable.home_svgrepo_com)
    object Courses : NavigationItem(CourseDestination.route, R.string.course_title, R.drawable.library_svgrepo_com)
    object Bookmarks : NavigationItem(BookmarkDestination.route, R.string.bookmark_title, R.drawable.bookmark_svgrepo_com)
    object Chat : NavigationItem(ChatDestination.route, R.string.chat_title, R.drawable.chat_svgrepo_com)
    object Profile : NavigationItem(ProfileDestination.route, R.string.profile_title, R.drawable.person_svgrepo_com_rounded)
}