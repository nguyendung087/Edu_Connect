package com.example.educonnect.data.database.repositories

import com.example.educonnect.data.model.users.Notification
import kotlinx.coroutines.flow.Flow

interface NotificationRepository {
    fun getNotificationsByUserStream(userId: String): Flow<List<Notification>>

    fun getUnreadNotificationCountStream(userId: String): Flow<Int>

    suspend fun insertStream(notification: Notification)

    suspend fun markAsReadStream(notificationId: String, userId: String)

    suspend fun deleteStream(notification: Notification)

    suspend fun deleteAllNotificationsByUserStream(userId: String)
}