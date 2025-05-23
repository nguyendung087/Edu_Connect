package com.example.educonnect.ui.notification

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.Firebase
import com.google.firebase.messaging.messaging
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import java.io.IOException

class ChatViewModel : ViewModel() {
    var chatState by mutableStateOf(ChatState())
        private set

    private val api : FcmApi = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8080/")
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create()

    init {
        viewModelScope.launch {
            Firebase.messaging.subscribeToTopic("chat").await()
        }
    }

    fun onRemoteTokenChange(newToken : String) {
        chatState = chatState.copy(
            remoteToken = newToken
        )
    }

    fun onMessageChange(message : String) {
        chatState = chatState.copy(
            messageText = message
        )
    }

    fun sendMessage(isBroadcast : Boolean) {
        viewModelScope.launch {
            val messageSto = SendMessageDTO(
                to = if (isBroadcast) null else chatState.remoteToken,
                notification = NotificationBody(
                    title = "New message",
                    body = chatState.messageText
                )
            )

            try {
                if (isBroadcast) {
                    api.broadcast(messageSto)
                } else {
                    api.sendMessage(messageSto)
                }

                chatState = chatState.copy(
                    messageText = ""
                )
            } catch (e : HttpException) {
                e.printStackTrace()
            } catch (e : IOException) {
                e.printStackTrace()
            }
        }
    }
}