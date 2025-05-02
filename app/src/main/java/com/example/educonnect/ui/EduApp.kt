package com.example.educonnect.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.educonnect.ui.authentication.AuthState
import com.example.educonnect.ui.authentication.LocalAuthState
import com.example.educonnect.ui.navigation.EduNavHost
import com.google.firebase.auth.FirebaseAuth

@Composable
fun EduApp(
    innerPaddingValues: PaddingValues = PaddingValues(0.dp),
    navController: NavHostController = rememberNavController()
) {
    val auth = FirebaseAuth.getInstance()
    val isLoggedIn = remember { mutableStateOf(false) }
    val currentUserId = remember { mutableStateOf<String?>(null) }

    DisposableEffect(Unit) {
        val listener = FirebaseAuth.AuthStateListener { firebaseAuth ->
            isLoggedIn.value = firebaseAuth.currentUser != null
            currentUserId.value = firebaseAuth.currentUser?.uid
        }
        auth.addAuthStateListener(listener)
        onDispose {
            auth.removeAuthStateListener(listener)
        }
    }

    CompositionLocalProvider(
        LocalAuthState provides AuthState(
            isLoggedIn = isLoggedIn.value,
            currentUserId = currentUserId.value
        )
    ) {
        EduNavHost(navController = navController)
    }
}
