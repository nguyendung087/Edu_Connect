package com.example.educonnect.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.navigation.compose.NavHost
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.educonnect.ui.courses.CourseDestination
import com.example.educonnect.ui.courses.CourseDetails
import com.example.educonnect.ui.courses.CourseDetailsDestination
import com.example.educonnect.ui.courses.CourseScreen
import com.example.educonnect.ui.home.HomeDestination
import com.example.educonnect.ui.home.HomeScreen
import com.example.educonnect.ui.mentor.MentorDetails
import com.example.educonnect.ui.mentor.MentorDetailsDestination
import com.example.educonnect.ui.mentor.TopMentorDestination
import com.example.educonnect.ui.mentor.TopMentorScreen
import com.example.educonnect.ui.notification.NotificationDestination
import com.example.educonnect.ui.notification.NotificationScreen
import com.example.educonnect.ui.signup.SignUpDestination
import com.example.educonnect.ui.signup.SignupScreen

//sealed class BottomNavItem(
//    val route: String,
//    val icon: androidx.compose.ui.graphics.vector.ImageVector,
//    val label: String
//) {
//    object Home : BottomNavItem("home", Icons.Default.Home, "Home")
//    object Chat : BottomNavItem("chat", Icons.Default.Star, "Chat")
//    object Profile : BottomNavItem("profile", Icons.Default.Person, "Profile")
//}

@Composable
fun EduNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = SignUpDestination.route,
        modifier = modifier
    ) {
        composable(route = SignUpDestination.route) {
            SignupScreen(
                navigateToHomeScreen = {
                    navController.navigate(
                        HomeDestination.route
                    )
                }
            )
        }

        composable(route = HomeDestination.route) {
            HomeScreen(
                innerPadding = PaddingValues(0.dp),
                navigateToNotificationScreen = {
                    navController.navigate(
                        NotificationDestination.route
                    )
                },
                navigateToCourseDetails = {
                    navController.navigate(
                        "${CourseDetailsDestination.route}/$it"
                    )
                },
                navigateToMentorDetails = {
                    navController.navigate(
                        "${MentorDetailsDestination.route}/$it"
                    )
                }
            )
        }
        composable(route = TopMentorDestination.route) {
            TopMentorScreen(
                navigateToMentorDetails = {
                    navController.navigate(
                        "${MentorDetailsDestination.route}/$it"
                    )
                },
//                navigateBack = { navController.popBackStack() },
//                onNavigateUp = { navController.navigateUp() }
            )
        }
        composable(
            route = MentorDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(MentorDetailsDestination.mentorIdArg) {
                type = NavType.IntType
            })
        ) {
            MentorDetails(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
//                navigateToEditItem = { navController.navigate("${ItemEditDestination.route}/$it") },
//                navigateBack = { navController.navigateUp() }
            )
        }
        composable(route = CourseDestination.route) {
            CourseScreen(
                navigateToCourseDetails = {
                    navController.navigate(
                        "${CourseDetailsDestination.route}/$it"
                    )
                },
//                navigateBack = { navController.popBackStack() },
//                onNavigateUp = { navController.navigateUp() }
            )
        }
        composable(
            route = CourseDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(CourseDetailsDestination.courseIdArg) {
                type = NavType.IntType
            })
        ) {
            CourseDetails(
                navigateBack = { navController.popBackStack() },
                onNavigateUp = { navController.navigateUp() }
//                navigateToEditItem = { navController.navigate("${ItemEditDestination.route}/$it") },
//                navigateBack = { navController.navigateUp() }
            )
        }
        composable(route = NotificationDestination.route) {
            NotificationScreen(
                navigateBack = { navController.popBackStack() }
//                onNavigateUp = { navController.navigateUp() }
            )
        }
    }
}

//@Composable
//fun BottomNavigationBar(navController: NavController) {
//    val items = listOf(
//        BottomNavItem.Home,
//        BottomNavItem.Chat,
//        BottomNavItem.Profile
//    )
//
//    NavigationBar(
////        backgroundColor = Color.White,
////        contentColor = Color.Black
//        containerColor = Color.White,
//        contentColor = Color.Black
//    ) {
//        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
//        items.forEach { item ->
//            NavigationBarItem(
//                icon = { Icon(imageVector = item.icon, contentDescription = item.label) },
//                label = { Text(item.label) },
//                selected = currentRoute == item.route,
//                onClick = {
//                    navController.navigate(item.route) {
//                        popUpTo(navController.graph.startDestinationId) { saveState = true }
//                        launchSingleTop = true
//                        restoreState = true
//                    }
//                },
//                colors = NavigationBarItemColors(
//                    selectedIconColor = Color(0xFF0961F5),
//                    unselectedIconColor = Color.Gray,
//                    unselectedTextColor = Color.Gray,
//                    selectedTextColor = Color(0xFF0961F5),
//                    disabledIconColor = Color.Gray,
//                    disabledTextColor = Color.Gray,
//                    selectedIndicatorColor = Color(0xFF0961F5)
//                ),
//            )
//        }
//    }
//}

