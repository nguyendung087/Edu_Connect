package com.example.educonnect.ui.information_form

import com.example.educonnect.data.model.users.TeacherProfile

data class InformationUiState(
    val teacherProfile: TeacherProfile = TeacherProfile(),
    val isFormValid : Boolean = false
)