package com.example.educonnect.data.model.review

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.educonnect.data.model.users.User
import java.time.LocalDateTime

@Entity(
    tableName = "teacher_reviews",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["userId"],
            childColumns = ["reviewerId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = User::class,
            parentColumns = ["userId"],
            childColumns = ["teacherId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class TeacherReview(
    @PrimaryKey
    @ColumnInfo(name = "teacher_review_id")
    val teacherReviewId: String,
    @ColumnInfo(name = "reviewer_id")
    val reviewerId: String,
    @ColumnInfo(name = "teacher_id")
    val teacherId: String,
    val rating: Float,
    val comment: String,
    @ColumnInfo(name = "created_at")
    val createdAt : LocalDateTime
)