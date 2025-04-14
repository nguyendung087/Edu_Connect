package com.example.educonnect.ui.mentor

import com.example.educonnect.data.model.users.TeacherProfile

data class MentorUiState(
    val mentorList : List<TeacherProfile> = listOf()
)