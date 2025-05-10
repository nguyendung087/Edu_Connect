package com.example.educonnect.data.model.courses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "lessons",
    foreignKeys = [ForeignKey(
        entity = Course::class,
        parentColumns = ["course_id"],
        childColumns = ["course_id"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [
        Index(value = ["course_id"])
    ]
)
data class Lesson(
    @PrimaryKey
    @ColumnInfo(name = "lesson_id")
    val lessonId: String = "",
    @ColumnInfo(name = "course_id")
    val courseId: String = "",
    val title: String = "",
    val content : String = "",
    val type: String = "",
    @ColumnInfo(name = "file_url")
    val fileUrl: String = ""
)