package com.example.educonnect.ui.navigation

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.navigation.compose.NavHost
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.educonnect.EduApplication
import com.example.educonnect.ui.authentication.LocalAuthState
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
import com.example.educonnect.ui.information_form.StudentInformationFormDestination
import com.example.educonnect.ui.information_form.StudentInformationScreen
import com.example.educonnect.ui.login.LoginDestination
import com.example.educonnect.ui.login.LoginScreen
import com.example.educonnect.ui.profile.ProfileDestination
import com.example.educonnect.ui.profile.ProfileEditDestination
import com.example.educonnect.ui.profile.ProfileEditScreen
import com.example.educonnect.ui.profile.ProfileScreen
import com.example.educonnect.ui.signup.SignUpDestination
import com.example.educonnect.ui.signup.SignupScreen

@Composable
fun EduNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
) {
//    val authState = LocalAuthState.current
//    val userRole = remember { mutableStateOf<String?>(null) }
//    LaunchedEffect(authState.currentUserId) {
//        authState.currentUserId?.let { userId ->
//            EduApplication().container.userRepository.getUserStream(userId).collect { user ->
//                userRole.value = user?.role
//            }
//        }
//    }

    NavHost(
        navController = navController,
        startDestination = SignUpDestination.route,
        modifier = modifier
    ) {
        composable(route = SignUpDestination.route) {
            SignupScreen(
                navigateToMentorInformationScreen = {
                    navController.navigate(
                        InformationFormDestination.route
                    )
                },
                navigateToStudentInformationScreen = {
                    navController.navigate(
                        StudentInformationFormDestination.route
                    )
                },
                navigateToSignInScreen = {
                    navController.navigate(
                        LoginDestination.route
                    )
                }
            )
        }

        composable(route = LoginDestination.route) {
            LoginScreen(
                navigateToSignUpScreen = {
                    navController.navigate(
                        SignUpDestination.route
                    )
                },
                navigateToHomeScreen = {
                    navController.navigate(HomeDestination.route) {
                        popUpTo(LoginDestination.route) { inclusive = true }
                    }
                }
            )
        }

        composable(route = InformationFormDestination.route) {
            InformationScreen(
                navigateToHomeScreen = {
                    navController.navigate(
                        HomeDestination.route
                    )
                }
            )
        }

        composable(
            route = StudentInformationFormDestination.route,
        ) {
            StudentInformationScreen(
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
            )
        }

        composable(route = NotificationDestination.route) {
            MainLayout(navController = navController) {
                NotificationScreen(
                    navigateBack = { navController.popBackStack() }
                )
            }
        }

        composable(route = BookmarkDestination.route) {
            MainLayout(navController = navController) { innerPadding ->
                BookmarkScreen(
                    innerPadding = innerPadding
                )
            }
        }

        composable(route = ChatDestination.route) {
            MainLayout(navController = navController) {
                ChatScreen()
            }
        }

        composable(route = ProfileDestination.route) {
            MainLayout(navController = navController) { innerPadding ->
                ProfileScreen(
                    innerPadding = innerPadding,
                    navigateToProfileEdits = {
                        navController.navigate(
                            ProfileEditDestination.route
                        )
                    },
                    navigateToSettings = {},
                    navigateToLogin = {
                        navController.navigate(LoginDestination.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                inclusive = true
                            }
                            launchSingleTop = true

                            restoreState = false
                        }
                    }
                )
            }
        }

        composable(route = ProfileEditDestination.route) {
            MainLayout(navController = navController) { innerPadding ->
                ProfileEditScreen(
                    innerPadding = innerPadding,
                    onNavigateBack = { navController.popBackStack() }
                )
            }
        }
    }
}

