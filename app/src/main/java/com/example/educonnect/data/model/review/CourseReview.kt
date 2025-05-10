package com.example.educonnect.data.model.review

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.educonnect.data.model.courses.Course
import com.example.educonnect.data.model.users.StudentProfile
import com.example.educonnect.data.model.users.User
import java.time.LocalDateTime

@Entity(
    tableName = "course_reviews",
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
    ],
    indices = [
        Index(value = ["student_id"]),
        Index(value = ["course_id"])
    ]
)
data class CourseReview(
    @PrimaryKey
    @ColumnInfo(name = "course_review_id")
    val courseReviewId: String = "",
    @ColumnInfo(name = "student_id")
    val studentId: String = "",
    @ColumnInfo(name = "course_id")
    val courseId: String = "",
    val rating: Float = 0.0f,
    val comment: String = "",
    @ColumnInfo(name = "created_at")
    val createdAt : LocalDateTime = LocalDateTime.now()
)