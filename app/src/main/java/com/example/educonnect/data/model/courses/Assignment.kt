package com.example.educonnect.data.model.courses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(
    tableName = "assignments",
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
data class Assignment(
    @PrimaryKey
    @ColumnInfo(name = "assignment_id")
    val assignmentId: String = "",
    @ColumnInfo(name = "course_id")
    val courseId: String = "",
    val title: String = "",
    val description : String = "",
    val deadline: LocalDateTime = LocalDateTime.now(),
    @ColumnInfo(name = "max_score")
    val maxScore: Float = 0.0f
)