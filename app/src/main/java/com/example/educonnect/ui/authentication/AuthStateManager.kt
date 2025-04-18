package com.example.educonnect.ui.authentication

import androidx.compose.runtime.compositionLocalOf
import com.example.educonnect.data.model.users.User
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

data class AuthState(
    val isLoggedIn: Boolean = false,
    val currentUserId: String? = null
)

val LocalAuthState = compositionLocalOf { AuthState() }