package com.example.educonnect.ui.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.navigation.compose.NavHost
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.educonnect.ui.authentication.AuthState
import com.example.educonnect.ui.students_screens.bookmark.BookmarkDestination
import com.example.educonnect.ui.students_screens.bookmark.BookmarkScreen
import com.example.educonnect.ui.chat.ChatDestination
import com.example.educonnect.ui.chat.ChatScreen
import com.example.educonnect.ui.students_screens.courses.CourseDestination
import com.example.educonnect.ui.students_screens.courses.CourseDetails
import com.example.educonnect.ui.students_screens.courses.CourseDetailsDestination
import com.example.educonnect.ui.students_screens.courses.CourseScreen
import com.example.educonnect.ui.students_screens.home.HomeDestination
import com.example.educonnect.ui.students_screens.home.HomeScreen
import com.example.educonnect.ui.students_screens.mentor.MentorDetails
import com.example.educonnect.ui.students_screens.mentor.MentorDetailsDestination
import com.example.educonnect.ui.students_screens.mentor.TopMentorDestination
import com.example.educonnect.ui.students_screens.mentor.TopMentorScreen
import com.example.educonnect.ui.notification.NotificationDestination
import com.example.educonnect.ui.notification.NotificationScreen
import com.example.educonnect.ui.information_form.InformationFormDestination
import com.example.educonnect.ui.information_form.InformationScreen
import com.example.educonnect.ui.information_form.StudentInformationFormDestination
import com.example.educonnect.ui.information_form.StudentInformationScreen
import com.example.educonnect.ui.login.LoginDestination
import com.example.educonnect.ui.login.LoginScreen
import com.example.educonnect.ui.mentor_screens.assignments.AssignmentDetailsDestination
import com.example.educonnect.ui.mentor_screens.assignments.AssignmentDetailsScreen
import com.example.educonnect.ui.mentor_screens.course_management.CourseManageDestination
import com.example.educonnect.ui.mentor_screens.course_management.CourseManageScreen
import com.example.educonnect.ui.mentor_screens.home.MentorHomeDestination
import com.example.educonnect.ui.mentor_screens.home.MentorHomeScreen
import com.example.educonnect.ui.mentor_screens.lessons.LessonTabs
import com.example.educonnect.ui.mentor_screens.planning.PlanningDestination
import com.example.educonnect.ui.mentor_screens.planning.PlanningScreen
import com.example.educonnect.ui.mentor_screens.profile.MentorProfileEditDestination
import com.example.educonnect.ui.mentor_screens.profile.MentorProfileEditScreen
import com.example.educonnect.ui.permissions.AppScreen
import com.example.educonnect.ui.permissions.hasPermission
import com.example.educonnect.ui.profile.ProfileDestination
import com.example.educonnect.ui.students_screens.profile.ProfileEditDestination
import com.example.educonnect.ui.students_screens.profile.ProfileEditScreen
import com.example.educonnect.ui.profile.ProfileScreen
import com.example.educonnect.ui.search.StudentSearchDestination
import com.example.educonnect.ui.search.StudentSearchScreen
import com.example.educonnect.ui.signup.SignUpDestination
import com.example.educonnect.ui.signup.SignupScreen

@Composable
fun EduNavHost(
    navController: NavHostController,
    authState: AuthState,
    modifier: Modifier = Modifier,
) {
    NavHost(
        navController = navController,
        startDestination = AppScreen.LOGIN,
        modifier = modifier
    ) {
        composable(route = SignUpDestination.route) {
            SignupScreen(
                navigateToMentorInformationScreen = {
                    navController.navigate(AppScreen.TEACHER_INFORMATION_FORM)
                },
                navigateToStudentInformationScreen = {
                    navController.navigate(AppScreen.STUDENT_INFORMATION_FORM)
                },
                navigateToSignInScreen = {
                    navController.navigate(AppScreen.LOGIN)
                }
            )
        }

        composable(route = LoginDestination.route) {
            LoginScreen(
                navigateToSignUpScreen = {
                    navController.navigate(AppScreen.SIGN_UP)
                },
                navigateToHomeScreen = {
                    navController.navigate(
                        when (authState.userRole) {
                            "Học viên" -> AppScreen.STUDENT_HOME
                            "Giáo viên" -> AppScreen.TEACHER_HOME
                            else -> AppScreen.LOGIN
                        }
                    ) {
                        popUpTo(AppScreen.LOGIN) { inclusive = true }
                    }
                }
            )
        }

        composable(route = InformationFormDestination.route) {
            InformationScreen(
                navigateToHomeScreen = {
                    navController.navigate(AppScreen.TEACHER_HOME)
                }
            )
        }

        composable(
            route = StudentInformationFormDestination.route,
        ) {
            StudentInformationScreen(
                navigateToHomeScreen = {
                    navController.navigate(AppScreen.STUDENT_HOME)
                }
            )
        }

        composable(route = StudentSearchDestination.route) {
            if (hasPermission(AppScreen.STUDENT_SEARCH, authState.userRole)) {
                MainLayout(navController = navController) { innerPadding ->
                    StudentSearchScreen(
                        innerPadding = innerPadding,
                        onNavigateUp = {
                            navController.popBackStack()
                        },
                        navigateToMentorDetails = {
                            navController.navigate(AppScreen.MENTOR_DETAILS.replace("{mentorId}", it))
                        },
                        navigateToCourseDetails = {
                            navController.navigate(AppScreen.STUDENT_COURSE_DETAILS.replace("{courseId}", it))
                        }
                    )
                }
            } else {
                UnauthorizedScreen(navController)
            }
        }

        composable(route = HomeDestination.route) {
            if (hasPermission(AppScreen.STUDENT_HOME, authState.userRole)) {
                MainLayout(navController = navController) { innerPadding ->
                    HomeScreen(
                        innerPadding = innerPadding,
                        navigateToCourseScreen = {
                            navController.navigate(AppScreen.STUDENT_COURSES)
                        },
                        navigateToMentorScreen = {
                            navController.navigate(AppScreen.TOP_MENTOR)
                        },
                        navigateToNotificationScreen = {
                            navController.navigate(AppScreen.NOTIFICATION)
                        },
                        navigateToSearchScreen = {
                            navController.navigate(AppScreen.STUDENT_SEARCH)
                        },
                        navigateToCourseDetails = {
                            navController.navigate(AppScreen.STUDENT_COURSE_DETAILS.replace("{courseId}", it))
                        },
                        navigateToMentorDetails = {
                            navController.navigate(AppScreen.MENTOR_DETAILS.replace("{mentorId}", it))
                        }
                    )
                }
            } else {
                UnauthorizedScreen(navController)
            }
        }

        composable(route = TopMentorDestination.route) {
            if (hasPermission(AppScreen.TOP_MENTOR, authState.userRole)) {
                MainLayout(navController = navController) { innerPadding ->
                    TopMentorScreen(
                        innerPadding = innerPadding,
                        navigateBack = { navController.popBackStack() },
                        navigateToChatScreen = {
                            navController.navigate(AppScreen.CHAT.replace("{conversationId}", it))
                        },
                        navigateToMentorDetails = {
                            navController.navigate(AppScreen.MENTOR_DETAILS.replace("{mentorId}", it))
                        }
                    )
                }
            } else {
                UnauthorizedScreen(navController)
            }
        }

        composable(
            route = MentorDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(MentorDetailsDestination.mentorIdArg) {
                type = NavType.StringType
            })
        ) {
            if (hasPermission(AppScreen.MENTOR_DETAILS, authState.userRole)) {
                MainLayout(navController = navController) {
                    MentorDetails(
                        navigateBack = { navController.popBackStack() },
                        onNavigateUp = { navController.navigateUp() }
                    )
                }
            } else {
                UnauthorizedScreen(navController)
            }
        }

        composable(route = CourseDestination.route) {
            if (hasPermission(AppScreen.STUDENT_COURSES, authState.userRole)) {
                MainLayout(navController = navController) { innerPadding ->
                    CourseScreen(
                        innerPadding = innerPadding,
                        navigateToCourseDetails = {
                            navController.navigate(AppScreen.STUDENT_COURSE_DETAILS.replace("{courseId}", it))
                        }
                    )
                }
            } else {
                UnauthorizedScreen(navController)
            }
        }

        composable(
            route = CourseDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(CourseDetailsDestination.courseIdArg) {
                type = NavType.StringType
            })
        ) {
            if (hasPermission(AppScreen.STUDENT_COURSE_DETAILS, authState.userRole)) {
                CourseDetails(
                    navigateBack = { navController.popBackStack() },
                    navigateToMentorDetails = {
                        navController.navigate(AppScreen.MENTOR_DETAILS.replace("{mentorId}", it))
                    },
                    onNavigateUp = { navController.navigateUp() }
                )
            } else {
                UnauthorizedScreen(navController)
            }
        }

        composable(route = NotificationDestination.route) {
            if (hasPermission(AppScreen.NOTIFICATION, authState.userRole)) {
                MainLayout(navController = navController) {
                    NotificationScreen(
                        navigateBack = { navController.popBackStack() }
                    )
                }
            } else {
                UnauthorizedScreen(navController)
            }
        }

        composable(route = BookmarkDestination.route) {
            if (hasPermission(AppScreen.BOOKMARK, authState.userRole)) {
                MainLayout(navController = navController) { innerPadding ->
                    BookmarkScreen(innerPadding = innerPadding)
                }
            } else {
                UnauthorizedScreen(navController)
            }
        }

        composable(
            route = ChatDestination.routeWithArgs,
            arguments = listOf(navArgument(ChatDestination.conversationIdArg) {
                type = NavType.StringType
            })
        ) {
            if (hasPermission(AppScreen.CHAT, authState.userRole)) {
                MainLayout(navController = navController) {
                    ChatScreen()
                }
            } else {
                UnauthorizedScreen(navController)
            }
        }

        composable(route = ProfileDestination.route) {
            if (hasPermission(AppScreen.PROFILE, authState.userRole)) {
                MainLayout(navController = navController) { innerPadding ->
                    ProfileScreen(
                        innerPadding = innerPadding,
                        navigateToProfileEdits = {
                            when (authState.userRole) {
                                "Học viên" -> navController.navigate(AppScreen.PROFILE_EDIT)
                                else -> navController.navigate(AppScreen.TEACHER_PROFILE_EDIT)
                            }
                        },
                        navigateToSettings = {},
                        navigateToLogin = {
                            navController.navigate(AppScreen.LOGIN) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    inclusive = true
                                }
                                launchSingleTop = true
                                restoreState = false
                            }
                        }
                    )
                }
            } else {
                UnauthorizedScreen(navController)
            }
        }

        composable(route = ProfileEditDestination.route) {
            if (hasPermission(AppScreen.PROFILE_EDIT, authState.userRole)) {
                MainLayout(navController = navController) { innerPadding ->
                    ProfileEditScreen(
                        innerPadding = innerPadding,
                        onNavigateBack = { navController.popBackStack() }
                    )
                }
            } else {
                UnauthorizedScreen(navController)
            }
        }

        composable(route = MentorHomeDestination.route) {
            if (hasPermission(AppScreen.TEACHER_HOME, authState.userRole)) {
                TeacherMainLayout(navController = navController) { innerPadding ->
                    MentorHomeScreen(
                        innerPadding = innerPadding,
                        navigateToMentorDetails = {},
                        navigateToCourseDetails = {},
                        navigateToCourseScreen = {},
                        navigateToMentorScreen = {},
                        navigateToNotificationScreen = { }
                    )
                }
            } else {
                UnauthorizedScreen(navController)
            }
        }

        composable(route = CourseManageDestination.route) {
            if (hasPermission(AppScreen.TEACHER_COURSE_MANAGEMENT, authState.userRole)) {
                TeacherMainLayout(navController = navController) { innerPadding ->
                    CourseManageScreen(
                        innerPadding = innerPadding,
                        navigateToCourseManageDetails = {
                            navController.navigate(AppScreen.TEACHER_PLANNING.replace("{courseId}", it))
                        }
                    )
                }
            } else {
                UnauthorizedScreen(navController)
            }
        }

        composable(
            route = PlanningDestination.routeWithArgs,
            arguments = listOf(navArgument(PlanningDestination.courseIdArg) {
                type = NavType.StringType
            })
        ) {
            if (hasPermission(AppScreen.TEACHER_PLANNING, authState.userRole)) {
                TeacherMainLayout(navController = navController) { innerPadding ->
                    PlanningScreen(
                        innerPadding = innerPadding,
                        navigateToAssignmentDetails = {
                            navController.navigate(AppScreen.TEACHER_ASSIGNMENT_DETAILS.replace("{assignmentId}", it))
                        }
                    )
                }
            } else {
                UnauthorizedScreen(navController)
            }
        }

        composable(
            route = AssignmentDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(AssignmentDetailsDestination.assignmentIdArg) {
                type = NavType.StringType
            })
        ) {
            if (hasPermission(AppScreen.TEACHER_ASSIGNMENT_DETAILS, authState.userRole)) {
                AssignmentDetailsScreen(
                    navigateUp = {
                        navController.popBackStack()
                    }
                )
            } else {
                UnauthorizedScreen(navController)
            }
        }

        composable(route = MentorProfileEditDestination.route) {
            if (hasPermission(AppScreen.TEACHER_PROFILE_EDIT, authState.userRole)) {
                TeacherMainLayout(navController = navController) { innerPadding ->
                    MentorProfileEditScreen(
                        innerPadding = innerPadding,
                        onNavigateBack = { navController.popBackStack() }
                    )
                }
            } else {
                UnauthorizedScreen(navController)
            }
        }
    }
}

@Composable
fun UnauthorizedScreen(navController: NavHostController) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Bạn không có quyền truy cập màn hình này",
            style = MaterialTheme.typography.bodyLarge,
            color = Color.Red
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = { navController.popBackStack() }) {
            Text("Quay lại")
        }
    }
}