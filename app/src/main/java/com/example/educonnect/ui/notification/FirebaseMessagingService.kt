package com.example.educonnect.ui.notification

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.educonnect.EduApplication
import com.example.educonnect.R
import com.example.educonnect.data.database.offline_repos.OfflineNotificationRepository
import com.example.educonnect.data.database.repositories.NotificationRepository
import com.example.educonnect.data.model.users.Notification
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.UUID

class MyFirebaseMessagingService : FirebaseMessagingService() {
    private val notificationRepository: NotificationRepository by lazy {
        (application as EduApplication).container.notificationRepository
    }
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(TAG, "From: ${remoteMessage.from}")

        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data payload: ${remoteMessage.data}")
            handleDataPayload(remoteMessage.data)
        }

        remoteMessage.notification?.let {
            Log.d(TAG, "Message Notification Body: ${it.body}")
        }
    }

    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")
        sendRegistrationToServer(token)
    }

    private fun handleDataPayload(data: Map<String, String>) {
        val notificationType = data["type"] ?: return
        val title = data["title"] ?: "New Notification"
        val content = data["content"] ?: "You have a new update."
        val userId = data["userId"] ?: return

        val notification = Notification(
            notificationId = UUID.randomUUID().toString(),
            userId = userId,
            title = title,
            content = content,
            type = notificationType,
            isRead = false,
            timeStamp = LocalDateTime.now()
        )

        coroutineScope.launch {
            notificationRepository.insertStream(notification)
        }

        if (isAppInForeground()) {
            showNotification(title, content)
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun showNotification(title: String, content: String) {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "educonnect_notifications"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "EduConnect Notifications",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }

    private fun sendRegistrationToServer(token: String?) {
        Log.d(TAG, "sendRegistrationTokenToServer($token)")
        // TODO: Implement server communication to store token with userId
    }

    companion object {
        private const val TAG = "MyFirebaseMsgService"
    }

    @SuppressLint("ServiceCast")
    private fun isAppInForeground(): Boolean {
        val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val runningProcesses = activityManager.runningAppProcesses ?: return false
        for (processInfo in runningProcesses) {
            if (processInfo.importance == ActivityManager.RunningAppProcessInfo.IMPORTANCE_FOREGROUND) {
                for (activeProcess in processInfo.pkgList) {
                    if (activeProcess == packageName) {
                        return true
                    }
                }
            }
        }
        return false
    }
}
