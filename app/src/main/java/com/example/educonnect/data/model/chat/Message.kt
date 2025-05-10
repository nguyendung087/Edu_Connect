package com.example.educonnect.data.model.chat

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.example.educonnect.data.model.users.User
import java.time.LocalDateTime

@Entity(
    tableName = "messages",
    foreignKeys = [
        ForeignKey(
            entity = Conversation::class,
            parentColumns = ["conversation_id"],
            childColumns = ["conversation_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = User::class,
            parentColumns = ["user_id"],
            childColumns = ["sender_id"],
            onDelete = ForeignKey.CASCADE
        ),
    ],
    indices = [
        Index(value = ["conversation_id"]),
        Index(value = ["sender_id"])
    ]
)
data class Message(
    @PrimaryKey
    @ColumnInfo(name = "message_id")
    val messageId: String = "",
    @ColumnInfo(name = "conversation_id")
    val conversationId: String = "",
    @ColumnInfo(name = "sender_id")
    val senderId: String = "",
    val content: String = "",
    val timestamp: LocalDateTime,
    val status: String = ""
)