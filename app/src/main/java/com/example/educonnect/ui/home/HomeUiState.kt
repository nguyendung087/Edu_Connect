package com.example.educonnect.ui.home

import com.example.educonnect.data.model.courses.CourseWithTeacher
import com.example.educonnect.data.model.users.StudentProfile
import com.example.educonnect.data.model.users.TeacherProfile

data class HomeUiState(
    var courseWithTeacherList: List<CourseWithTeacher> = listOf(),
    var mentorList : List<TeacherProfile> = listOf(),
    var currentUser : StudentProfile? = StudentProfile()
)
