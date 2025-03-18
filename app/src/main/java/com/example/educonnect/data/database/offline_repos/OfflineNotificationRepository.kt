package com.example.educonnect.data.database.offline_repos

import com.example.educonnect.data.database.daos.NotificationDao
import com.example.educonnect.data.database.repositories.NotificationRepository
import com.example.educonnect.data.model.users.Notification
import kotlinx.coroutines.flow.Flow

class OfflineNotificationRepository(
    private val notificationDao: NotificationDao
) : NotificationRepository {
    override fun getNotificationsByUserStream(userId: String): Flow<List<Notification>> =
        notificationDao.getNotificationsByUser(userId)

    override fun getUnreadNotificationCountStream(userId: String): Flow<Int> =
        notificationDao.getUnreadNotificationCount(userId)

    override suspend fun insertStream(notification: Notification) =
        notificationDao.insertNotification(notification)

    override suspend fun markAsReadStream(notificationId: String, userId: String) =
        notificationDao.markAsRead(notificationId, userId)

    override suspend fun deleteStream(notification: Notification) =
        notificationDao.deleteNotification(notification)

    override suspend fun deleteAllNotificationsByUserStream(userId: String) =
        notificationDao.deleteAllNotificationsByUser(userId)
}