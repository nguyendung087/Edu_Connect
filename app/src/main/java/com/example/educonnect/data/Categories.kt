package com.example.educonnect.data

import androidx.compose.ui.graphics.Color
import com.example.educonnect.R

object LocalCourseCategoryData {
    val courseCategories = listOf(
        Pair("Coding", R.drawable.laptop_code_svgrepo_com),
        Pair("AI/ML", R.drawable.smart_work_artificial_intelligence_ai_svgrepo_com),
        Pair("Design", R.drawable.designtools_svgrepo_com),
        Pair("Database", R.drawable.database_svgrepo_com),
    )
}

object LocalAssignmentCategoryData {
    val assignmentCategories = listOf(
        Pair("Quiz", Color(0xFF0961F5)),
        Pair("Homework", Color(0xFF08AC6C)),
        Pair("Test", Color(0xFF8C00BF)),
        Pair("Coursework", Color(0xFFFF800D)),
    )
}

