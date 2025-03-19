package com.example.educonnect.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.educonnect.ui.chat.ChatScreen
import com.example.educonnect.ui.home.HomeScreen
import com.example.educonnect.ui.mentor.TopMentorScreen
import com.example.educonnect.ui.navigation.EduNavHost

enum class E

//@Composable
//fun EduApp(
//
//) {
//    val navController = rememberNavController()

//    Scaffold(
//
//        bottomBar = { BottomNavigationBar(navController) }
//    ) { paddingValues ->
//        NavHost(
//            navController = navController,
//            startDestination = BottomNavItem.Home.route,
//            modifier = Modifier.padding(paddingValues)
//        ) {
//            composable(BottomNavItem.Home.route) { HomeScreen() }
//            composable(BottomNavItem.Chat.route) { ChatScreen() }
//            composable(BottomNavItem.Profile.route) { TopMentorScreen() }
//        }
//    }
//}

@Composable
fun EduApp(
    innerPaddingValues: PaddingValues = PaddingValues(0.dp),
    navController: NavHostController = rememberNavController()
) {
    EduNavHost(navController = navController)
}


@Composable
@Preview
private fun EduPreview() {
    EduApp()
//    HomeAppBar()
}
