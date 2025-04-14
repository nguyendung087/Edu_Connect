package com.example.educonnect.ui

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.educonnect.ui.navigation.EduNavHost

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
}
