package com.example.educonnect.data.model.courses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.example.educonnect.data.model.users.StudentProfile
import com.example.educonnect.data.model.users.User
import java.time.LocalDateTime

@Entity(
    tableName = "attendances",
    primaryKeys = ["student_id", "lesson_id"],
    foreignKeys = [
        ForeignKey(
            entity = StudentProfile::class,
            parentColumns = ["student_id"],
            childColumns = ["student_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Lesson::class,
            parentColumns = ["lesson_id"],
            childColumns = ["lesson_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["student_id"]),
        Index(value = ["lesson_id"])
    ]
)
data class Attendance(
//    @PrimaryKey
//    @ColumnInfo(name = "attendance_id")
//    val attendanceId: String,
    @ColumnInfo(name = "student_id")
    val studentId: String = "",
    @ColumnInfo(name = "lesson_id")
    val lessonId: String,
    @ColumnInfo(name = "join_time")
    val joinTime: LocalDateTime,
    @ColumnInfo(name = "leave_time")
    val leaveTime: LocalDateTime
)