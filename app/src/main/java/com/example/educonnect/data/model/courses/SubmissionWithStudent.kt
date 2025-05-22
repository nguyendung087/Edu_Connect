package com.example.educonnect.data.model.courses

import androidx.room.Embedded
import com.example.educonnect.data.model.users.StudentProfile

data class SubmissionWithStudent(
    @Embedded(prefix = "submission_")
    val submission: Submission = Submission(),
    @Embedded(prefix = "student_")
    val student : StudentProfile = StudentProfile()
)
