package com.example.educonnect.data.model.courses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.example.educonnect.data.model.users.User

@Entity(
    tableName = "enrollments",
    primaryKeys = ["userId", "courseId"],
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["studentId"],
            childColumns = ["studentId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Course::class,
            parentColumns = ["courseId"],
            childColumns = ["courseId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Enrollment(
    @ColumnInfo(name = "student_id")
    val studentId: String,
    @ColumnInfo(name = "course_id")
    val courseId: String,
    val status: String,
    val progress: Float
)