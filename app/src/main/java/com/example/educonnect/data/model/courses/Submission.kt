package com.example.educonnect.data.model.courses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.educonnect.data.model.users.StudentProfile
import com.example.educonnect.data.model.users.User
import java.time.LocalDateTime

@Entity(
    tableName = "submissions",
    foreignKeys = [
        ForeignKey(
            entity = StudentProfile::class,
            parentColumns = ["student_id"],
            childColumns = ["student_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Assignment::class,
            parentColumns = ["assignment_id"],
            childColumns = ["assignment_id"],
            onDelete = ForeignKey.CASCADE
        ),
    ],
    indices = [
        Index(value = ["student_id"]),
        Index(value = ["assignment_id"])
    ]
)
data class Submission(
    @PrimaryKey
    @ColumnInfo(name = "submission_id")
    val submissionId: String = "",
    @ColumnInfo(name = "student_id")
    val studentId: String = "",
    @ColumnInfo(name = "assignment_id")
    val assignmentId: String? = "",
    @ColumnInfo(name = "file_url")
    val fileUrl: String = "",
    @ColumnInfo(name = "submitted_at")
    val submittedAt : LocalDateTime = LocalDateTime.now()
)