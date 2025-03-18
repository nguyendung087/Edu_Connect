package com.example.educonnect.data.model.courses

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.example.educonnect.data.model.users.User
import java.time.LocalDateTime

@Entity(
    tableName = "attendances",
    foreignKeys = [
        ForeignKey(
            entity = User::class,
            parentColumns = ["userId"],
            childColumns = ["studentId"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = Lesson::class,
            parentColumns = ["lessonId"],
            childColumns = ["lessonId"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class Attendance(
//    @PrimaryKey
//    @ColumnInfo(name = "attendance_id")
//    val attendanceId: String,
    @ColumnInfo(name = "student_id")
    val studentId: String,
    @ColumnInfo(name = "lesson_id")
    val lessonId: String,
    @ColumnInfo(name = "join_time")
    val joinTime: LocalDateTime,
    @ColumnInfo(name = "leave_time")
    val leaveTime: LocalDateTime
)