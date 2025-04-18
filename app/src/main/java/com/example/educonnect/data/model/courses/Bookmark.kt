package com.example.educonnect.data.model.courses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import com.example.educonnect.data.model.users.StudentProfile

@Entity(
    tableName = "bookmarks",
    primaryKeys = ["student_id", "course_id"],
    foreignKeys = [
        ForeignKey(
            entity = StudentProfile::class,
            parentColumns = ["student_id"],
            childColumns = ["student_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Course::class,
            parentColumns = ["course_id"],
            childColumns = ["course_id"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Bookmark(
    @ColumnInfo(name = "student_id") val studentId: String,
    @ColumnInfo(name = "course_id") val courseId: String
)