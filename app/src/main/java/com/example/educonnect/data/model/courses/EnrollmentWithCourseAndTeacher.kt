package com.example.educonnect.data.model.courses

import androidx.room.Embedded
import com.example.educonnect.data.model.users.TeacherProfile

data class EnrollmentWithCourseAndTeacher(
    @Embedded(prefix = "enrollment_")
    val enrollment : Enrollment = Enrollment(),
    @Embedded(prefix = "course_")
    val course : Course = Course(),
    @Embedded(prefix = "teacher_")
    val teacher : TeacherProfile = TeacherProfile()
)