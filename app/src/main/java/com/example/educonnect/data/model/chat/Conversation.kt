package com.example.educonnect.data.model.chat

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.educonnect.data.model.users.StudentProfile
import com.example.educonnect.data.model.users.TeacherProfile
import com.example.educonnect.data.model.users.User
import java.time.LocalDateTime

@Entity(
    tableName = "conversations",
    foreignKeys = [
        ForeignKey(
            entity = StudentProfile::class,
            parentColumns = ["student_id"],
            childColumns = ["student_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = TeacherProfile::class,
            parentColumns = ["teacher_id"],
            childColumns = ["teacher_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["student_id"]),
        Index(value = ["teacher_id"])
    ]
)
data class Conversation(
    @PrimaryKey
    @ColumnInfo(name = "conversation_id")
    val conversationId: String = "",
    @ColumnInfo(name = "student_id")
    val studentId: String = "",
    @ColumnInfo(name = "teacher_id")
    val teacherId: String = "",
    @ColumnInfo(name = "created_at")
    val createdAt: LocalDateTime = LocalDateTime.now(),
    @ColumnInfo(name = "updated_at")
    val updatedAt: LocalDateTime = LocalDateTime.now()
)