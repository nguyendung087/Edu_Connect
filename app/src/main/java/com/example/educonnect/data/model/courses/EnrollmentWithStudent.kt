package com.example.educonnect.data.model.courses

import androidx.room.Embedded
import com.example.educonnect.data.model.users.StudentProfile
import com.example.educonnect.data.model.users.TeacherProfile

data class EnrollmentWithStudent(
    @Embedded(prefix = "enrollment_")
    val enrollment : Enrollment = Enrollment(),
    @Embedded(prefix = "student_")
    val student : StudentProfile = StudentProfile()
)
