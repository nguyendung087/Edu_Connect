package com.example.educonnect.data.model.courses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.educonnect.data.model.users.User
import java.time.LocalDateTime

@Entity(
    tableName = "submissions",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["userId"],
            childColumns = ["studentId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Assignment::class,
            parentColumns = ["assignmentId"],
            childColumns = ["assignmentId"],
            onDelete = ForeignKey.CASCADE
        ),
    ]
)
data class Submission(
    @PrimaryKey
    @ColumnInfo(name = "submission_id")
    val submissionId: String,
    @ColumnInfo(name = "student_id")
    val studentId: String,
    @ColumnInfo(name = "assignment_id")
    val assignmentId: String?,
    @ColumnInfo(name = "file_url")
    val fileUrl: String,
    @ColumnInfo(name = "submitted_at")
    val submittedAt : LocalDateTime
)