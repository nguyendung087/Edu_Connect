package com.example.educonnect.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.educonnect.R
import com.example.educonnect.ui.mentor_screens.course_management.CourseManageDestination
import com.example.educonnect.ui.mentor_screens.home.MentorHomeDestination
import com.example.educonnect.ui.profile.ProfileDestination
import com.example.educonnect.ui.students_screens.home.HomeDestination

@Composable
fun TeacherMainLayout(
    navController: NavHostController,
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        bottomBar = {
            TeacherBottomNavigationBar(navController)
        }
    ) { innerPadding ->
        content(innerPadding)
    }
}

@Composable
fun TeacherBottomNavigationBar(navController: NavHostController) {
    val items = listOf(
        MentorNavigationItem.Home,
        MentorNavigationItem.Classes,
        MentorNavigationItem.Planning,
        MentorNavigationItem.Schedule,
        MentorNavigationItem.Profile
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

sealed class MentorNavigationItem(val route: String, @StringRes val title: Int, @DrawableRes val icon: Int) {
    object Home : MentorNavigationItem(MentorHomeDestination.route, R.string.home_title, R.drawable.home_svgrepo_com)
    object Classes : MentorNavigationItem(CourseManageDestination.route, R.string.course_title, R.drawable.library_svgrepo_com)
    object Planning : MentorNavigationItem(CourseManageDestination.route, R.string.course_manage, R.drawable.library_svgrepo_com)
    object Schedule : MentorNavigationItem(CourseManageDestination.route, R.string.chat_title, R.drawable.chat_svgrepo_com)
    object Profile : MentorNavigationItem(ProfileDestination.route, R.string.profile_title, R.drawable.person_svgrepo_com_rounded)
}