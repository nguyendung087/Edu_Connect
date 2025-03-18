package com.example.educonnect.data.model.courses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(
    tableName = "assignments",
    foreignKeys = [ForeignKey(
        entity = Course::class,
        parentColumns = ["courseId"],
        childColumns = ["courseId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Assignment(
    @PrimaryKey
    @ColumnInfo(name = "assignment_id")
    val assignmentId: String,
    @ColumnInfo(name = "course_id")
    val courseId: String,
    val title: String,
    val description : String,
    val deadline: LocalDateTime,
    @ColumnInfo(name = "max_score")
    val maxScore: Float
)