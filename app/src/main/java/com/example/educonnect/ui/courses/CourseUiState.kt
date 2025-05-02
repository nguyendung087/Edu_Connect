package com.example.educonnect.ui.courses

import com.example.educonnect.data.model.courses.Course
import com.example.educonnect.data.model.courses.CourseWithTeacher
import com.example.educonnect.data.model.courses.Enrollment
import com.example.educonnect.data.model.courses.EnrollmentWithCourseAndTeacher
import com.example.educonnect.data.model.users.TeacherProfile

data class CourseUiState(
    val currentUserId : String? = "",
    val enrollment: List<EnrollmentWithCourseAndTeacher> = emptyList()
)
