package com.example.educonnect.data.database.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.educonnect.data.model.users.Notification
import kotlinx.coroutines.flow.Flow

@Dao
interface NotificationDao {
    @Insert
    suspend fun insertNotification(notification: Notification)

    @Update
    suspend fun updateNotification(notification: Notification)

    @Delete
    suspend fun deleteNotification(notification: Notification)

    @Query("SELECT * FROM notifications WHERE user_id = :userId ORDER BY time_stamp DESC")
    fun getNotificationsByUser(userId: String): Flow<List<Notification>>

    @Query("SELECT COUNT(*) FROM notifications WHERE user_id = :userId AND is_read = 0")
    fun getUnreadNotificationCount(userId: String): Flow<Int>

    @Query("UPDATE notifications SET is_read = 1 WHERE notification_id = :notificationId AND user_id = :userId")
    suspend fun markAsRead(notificationId: String, userId: String)

    @Query("DELETE FROM notifications WHERE user_id = :userId")
    suspend fun deleteAllNotificationsByUser(userId: String)
}