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
import com.example.educonnect.ui.bookmark.BookmarkDestination
import com.example.educonnect.ui.bookmark.BookmarkScreen
import com.example.educonnect.ui.chat.ChatDestination
import com.example.educonnect.ui.chat.ChatScreen
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
import com.example.educonnect.ui.information_form.InformationFormDestination
import com.example.educonnect.ui.information_form.InformationScreen
import com.example.educonnect.ui.signup.SignUpDestination
import com.example.educonnect.ui.signup.SignupScreen

@Composable
fun EduNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = HomeDestination.route,
        modifier = modifier
    ) {
        composable(route = SignUpDestination.route) {
            SignupScreen(
                navigateToInformationScreen = {
                    navController.navigate(
                        "${InformationFormDestination.route}/$it"
                    )
                }
            )
        }

        composable(
            route = InformationFormDestination.routeWithArgs,
            arguments = listOf(navArgument(InformationFormDestination.userIdArg) {
                type = NavType.StringType
            })
        ) {
            InformationScreen(
                navigateToHomeScreen = {
                    navController.navigate(
                        HomeDestination.route
                    )
                }
            )
        }

        composable(route = HomeDestination.route) {
            MainLayout(
                navController = navController,
            ) { innerPadding ->
                HomeScreen(
                    innerPadding = innerPadding,
                    navigateToCourseScreen = {
                        navController.navigate(
                            CourseDestination.route
                        )
                    },
                    navigateToMentorScreen = {
                        navController.navigate(
                            TopMentorDestination.route
                        )
                    },
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
        }
        composable(route = TopMentorDestination.route) {
            MainLayout(
                navController = navController
            ) { innerPadding ->
                TopMentorScreen(
                    innerPadding = innerPadding,
                    navigateBack = { navController.popBackStack() },
                    navigateToMentorDetails = {
                        navController.navigate(
                            "${MentorDetailsDestination.route}/$it"
                        )
                    },
//                navigateBack = { navController.popBackStack() },
//                onNavigateUp = { navController.navigateUp() }
                )
            }
        }
        composable(
            route = MentorDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(MentorDetailsDestination.mentorIdArg) {
                type = NavType.StringType
            })
        ) {
            MainLayout(navController = navController) {
                MentorDetails(
                    navigateBack = { navController.popBackStack() },
                    onNavigateUp = { navController.navigateUp() }
                    //                navigateToEditItem = { navController.navigate("${ItemEditDestination.route}/$it") },
                    //                navigateBack = { navController.navigateUp() }
                )
            }
        }
        composable(route = CourseDestination.route) {
            MainLayout(
                navController = navController
            ) { innerPadding ->
                CourseScreen(
                    innerPadding = innerPadding,
                    navigateToCourseDetails = {
                        navController.navigate(
                            "${CourseDetailsDestination.route}/$it"
                        )
                    },
//                navigateBack = { navController.popBackStack() },
//                onNavigateUp = { navController.navigateUp() }
                )
            }
        }
        composable(
            route = CourseDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(CourseDetailsDestination.courseIdArg) {
                type = NavType.StringType
            })
        ) {
            CourseDetails(
                navigateBack = { navController.popBackStack() },
                navigateToMentorDetails = {
                    navController.navigate("${MentorDetailsDestination.route}/$it")
                },
                onNavigateUp = { navController.navigateUp() }
//                navigateToEditItem = { navController.navigate("${ItemEditDestination.route}/$it") },
//                navigateBack = { navController.navigateUp() }
            )
        }
        composable(route = NotificationDestination.route) {
            MainLayout(navController = navController) {
                NotificationScreen(
                    navigateBack = { navController.popBackStack() }
//                onNavigateUp = { navController.navigateUp() }
                )
            }
        }

        composable(route = BookmarkDestination.route) {
            MainLayout(navController = navController) {
                BookmarkScreen(

//                onNavigateUp = { navController.navigateUp() }
                )
            }
        }

        composable(route = ChatDestination.route) {
            MainLayout(navController = navController) {
                ChatScreen(

//                onNavigateUp = { navController.navigateUp() }
                )
            }
        }


    }
}

