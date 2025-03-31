package com.example.educonnect.data.model.courses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.example.educonnect.data.model.users.User

@Entity(
    tableName = "enrollments",
    primaryKeys = ["user_id", "course_id"],
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["user_id"],
            childColumns = ["user_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Course::class,
            parentColumns = ["course_id"],
            childColumns = ["course_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["user_id"]),
        Index(value = ["course_id"])
    ]
)
data class Enrollment(
    @ColumnInfo(name = "user_id")
    val userId: String,
    @ColumnInfo(name = "course_id")
    val courseId: String,
    val status: String,
    val progress: Float
)