package com.example.educonnect.data.model.courses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.educonnect.data.model.users.TeacherProfile
import java.time.LocalDateTime

@Entity(
    tableName = "courses",
    foreignKeys = [ForeignKey(
        entity = TeacherProfile::class,
        parentColumns = ["teacher_id"],
        childColumns = ["teacher_id"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [
        Index(value = ["teacher_id"]),
    ]
)
data class Course(
    @PrimaryKey
    @ColumnInfo(name = "course_id")
    val courseId: String,
    @ColumnInfo(name = "teacher_id")
    val teacherId: String,
    val title: String,
    val description: String,
    val cost : Double,
    @ColumnInfo(name = "created_at")
    val createdAt: LocalDateTime
)

