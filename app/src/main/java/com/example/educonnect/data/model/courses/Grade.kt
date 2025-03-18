package com.example.educonnect.data.model.courses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.educonnect.data.model.users.User
import java.time.LocalDateTime

@Entity(
    tableName = "grades",
    foreignKeys = [
        ForeignKey(
            entity = Submission::class,
            parentColumns = ["submissionId"],
            childColumns = ["submissionId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Grade(
    @PrimaryKey
    @ColumnInfo(name = "grade_id")
    val gradeId: String,
    @ColumnInfo(name = "submission_id")
    val submissionId: String?,
    val score: Float,
    val feedback : String?,
    @ColumnInfo(name = "graded_at")
    val gradedAt : LocalDateTime
)