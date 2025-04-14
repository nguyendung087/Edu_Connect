package com.example.educonnect.data.model.courses

import androidx.room.ColumnInfo
import androidx.room.Embedded
import com.example.educonnect.data.model.users.TeacherProfile

data class CourseWithTeacher(
    @Embedded(prefix = "course_")
    val course : Course = Course(),
    @Embedded(prefix = "teacher_")
    val teacher : TeacherProfile = TeacherProfile()
)