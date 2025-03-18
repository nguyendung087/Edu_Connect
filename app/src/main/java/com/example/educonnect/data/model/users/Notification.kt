package com.example.educonnect.data.model.users

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@Entity(
    tableName = "notifications",
    foreignKeys = [ForeignKey(
        entity = User::class,
        parentColumns = ["userId"],
        childColumns = ["userId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class Notification(
    @PrimaryKey
    @ColumnInfo(name = "notification_id")
    val notificationId: String,
    @ColumnInfo(name = "user_id")
    val userId: String,
    val title : String,
    val content: String,
    val type: String,
    @ColumnInfo(name = "is_read")
    val isRead : Boolean,
    @ColumnInfo(name = "time_stamp")
    val timeStamp : LocalDateTime
)