package com.example.educonnect.ui.permissions

import com.example.educonnect.ui.students_screens.bookmark.BookmarkDestination
import com.example.educonnect.ui.chat.ChatDestination
import com.example.educonnect.ui.students_screens.courses.CourseDestination
import com.example.educonnect.ui.students_screens.courses.CourseDetailsDestination
import com.example.educonnect.ui.students_screens.home.HomeDestination
import com.example.educonnect.ui.information_form.InformationFormDestination
import com.example.educonnect.ui.information_form.StudentInformationFormDestination
import com.example.educonnect.ui.login.LoginDestination
import com.example.educonnect.ui.mentor_screens.assignments.AssignmentDetailsDestination
import com.example.educonnect.ui.mentor_screens.assignments.AssignmentManageDestination
import com.example.educonnect.ui.mentor_screens.course_management.CourseManageDestination
import com.example.educonnect.ui.mentor_screens.home.MentorHomeDestination
import com.example.educonnect.ui.mentor_screens.lessons.LessonManageDestination
import com.example.educonnect.ui.mentor_screens.planning.PlanningDestination
import com.example.educonnect.ui.mentor_screens.profile.MentorProfileEditDestination
import com.example.educonnect.ui.students_screens.mentor.MentorDetailsDestination
import com.example.educonnect.ui.students_screens.mentor.TopMentorDestination
import com.example.educonnect.ui.notification.NotificationDestination
import com.example.educonnect.ui.profile.ProfileDestination
import com.example.educonnect.ui.search.StudentSearchDestination
import com.example.educonnect.ui.students_screens.profile.ProfileEditDestination
import com.example.educonnect.ui.signup.SignUpDestination

object AppScreen {
    val SIGN_UP = SignUpDestination.route
    val LOGIN = LoginDestination.route
    val TEACHER_INFORMATION_FORM = InformationFormDestination.route
    val STUDENT_INFORMATION_FORM = StudentInformationFormDestination.route

    val STUDENT_HOME = HomeDestination.route
    val STUDENT_SEARCH = StudentSearchDestination.route
    val STUDENT_COURSES = CourseDestination.route
    val STUDENT_COURSE_DETAILS = CourseDetailsDestination.routeWithArgs
    val TOP_MENTOR = TopMentorDestination.route
    val MENTOR_DETAILS = MentorDetailsDestination.routeWithArgs
    val NOTIFICATION = NotificationDestination.route
    val BOOKMARK = BookmarkDestination.route
    val CHAT = ChatDestination.route
    val PROFILE = ProfileDestination.route
    val PROFILE_EDIT = ProfileEditDestination.route

    val TEACHER_HOME = MentorHomeDestination.route
    val TEACHER_PROFILE_EDIT = MentorProfileEditDestination.route
    val TEACHER_COURSE_MANAGEMENT = CourseManageDestination.route
    val TEACHER_PLANNING = PlanningDestination.routeWithArgs
    val TEACHER_LESSON_MANAGEMENT = LessonManageDestination.route
    val TEACHER_ASSIGNMENT_MANAGEMENT = AssignmentManageDestination.route
    val TEACHER_ASSIGNMENT_DETAILS = AssignmentDetailsDestination.routeWithArgs
    val TEACHER_GRADE_MANAGEMENT = LessonManageDestination.route
    val TEACHER_STUDENT_MANAGEMENT = LessonManageDestination.route
}

val screenPermissions = mapOf(
    AppScreen.SIGN_UP to listOf(null, "Học viên", "Giáo viên"),
    AppScreen.LOGIN to listOf(null, "Học viên", "Giáo viên"),
    AppScreen.TEACHER_INFORMATION_FORM to listOf("Giáo viên"),
    AppScreen.STUDENT_INFORMATION_FORM to listOf("Học viên"),

    AppScreen.STUDENT_HOME to listOf("Học viên"),
    AppScreen.STUDENT_SEARCH to listOf("Học viên"),
    AppScreen.STUDENT_COURSES to listOf("Học viên"),
    AppScreen.STUDENT_COURSE_DETAILS to listOf("Học viên"),
    AppScreen.TOP_MENTOR to listOf("Học viên"),
    AppScreen.MENTOR_DETAILS to listOf("Học viên"),
    AppScreen.NOTIFICATION to listOf("Học viên"),
    AppScreen.BOOKMARK to listOf("Học viên"),
    AppScreen.CHAT to listOf("Học viên"),
    AppScreen.PROFILE to listOf("Học viên", "Giáo viên"),
    AppScreen.PROFILE_EDIT to listOf("Học viên"),

    AppScreen.TEACHER_HOME to listOf("Giáo viên"),
    AppScreen.TEACHER_PROFILE_EDIT to listOf("Giáo viên"),
    AppScreen.TEACHER_PLANNING to listOf("Giáo viên"),
    AppScreen.TEACHER_LESSON_MANAGEMENT to listOf("Giáo viên"),
    AppScreen.TEACHER_COURSE_MANAGEMENT to listOf("Giáo viên"),
    AppScreen.TEACHER_STUDENT_MANAGEMENT to listOf("Giáo viên"),
    AppScreen.TEACHER_GRADE_MANAGEMENT to listOf("Giáo viên"),
    AppScreen.TEACHER_ASSIGNMENT_MANAGEMENT to listOf("Giáo viên"),
    AppScreen.TEACHER_ASSIGNMENT_DETAILS to listOf("Giáo viên"),
)

fun hasPermission(screen: String, role: String?): Boolean {
    val allowedRoles = screenPermissions[screen] ?: return false
    return role in allowedRoles
}