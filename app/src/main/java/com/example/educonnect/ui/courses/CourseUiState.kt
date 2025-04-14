package com.example.educonnect.ui.courses

import com.example.educonnect.data.model.courses.Course
import com.example.educonnect.data.model.courses.CourseWithTeacher

data class CourseUiState(
    var courseList : List<CourseWithTeacher> = emptyList(),
    val isBookMarked : Boolean = false
)
