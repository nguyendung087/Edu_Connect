package com.example.educonnect.ui.notification

import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.educonnect.data.database.repositories.NotificationRepository
import com.example.educonnect.data.database.repositories.UserRepository
import com.example.educonnect.data.model.users.Notification
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NotificationViewModel(
    private val userRepository: UserRepository,
    private val notificationRepository: NotificationRepository
) : ViewModel() {
    private var _notificationUiState = MutableStateFlow(NotificationUiState())
    val notificationUiState : StateFlow<NotificationUiState> = _notificationUiState.asStateFlow()

    init {
        viewModelScope.launch {
            snapshotFlow { _notificationUiState.value.currentId }
                .collect { userId ->
                    userId?.takeIf { it.isNotBlank() }?.let {
                        getNotificationsByUser(it).collect { notifications ->
                            _notificationUiState.value = NotificationUiState(notifications)
                        }
                    }
                }
        }
    }

    fun setCurrentUserId(userId: String?) {
        _notificationUiState.update { currentState ->
            currentState.copy(
                currentId = userId
            )
        }
    }

    fun addNotification(notification: Notification) {
        viewModelScope.launch {
            notificationRepository.insertStream(notification)
        }
    }

    private fun getNotificationsByUser(userId: String): StateFlow<List<Notification>> {
        return notificationRepository.getNotificationsByUserStream(userId).stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    }

    fun deleteNotification(notification: Notification) {
        viewModelScope.launch {
            notificationRepository.deleteStream(notification)
        }
    }

    fun markAsRead(notificationId: String, userId: String) {
        viewModelScope.launch {
            notificationRepository.markAsReadStream(notificationId, userId)
        }
    }
}

data class NotificationUiState(
    val notification : List<Notification> = emptyList(),
    val currentId : String? = ""
)