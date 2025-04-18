package com.example.educonnect.data.model.review

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.educonnect.data.model.courses.Course
import com.example.educonnect.data.model.users.User
import java.time.LocalDateTime

@Entity(
    tableName = "course_reviews",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["user_id"],
            childColumns = ["reviewer_id"],
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
        Index(value = ["reviewer_id"]),
        Index(value = ["course_id"])
    ]
)
data class CourseReview(
    @PrimaryKey
    @ColumnInfo(name = "course_review_id")
    val courseReviewId: String,
    @ColumnInfo(name = "reviewer_id")
    val reviewerId: String,
    @ColumnInfo(name = "course_id")
    val courseId: String,
    val rating: Float,
    val comment: String,
    @ColumnInfo(name = "created_at")
    val createdAt : LocalDateTime
)