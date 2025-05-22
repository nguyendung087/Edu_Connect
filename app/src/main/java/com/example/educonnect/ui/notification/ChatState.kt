package com.example.educonnect.ui.notification

data class ChatState(
    val isEnteringToken : Boolean = false,
    val remoteToken : String = "",
    val messageText : String = ""
)