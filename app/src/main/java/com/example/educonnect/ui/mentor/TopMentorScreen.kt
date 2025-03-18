package com.example.educonnect.ui.mentor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.educonnect.R
import com.example.educonnect.ui.navigation.NavigationDestination

object TopMentorDestination : NavigationDestination {
    override val route = "top_mentor"
    override val titleRes = R.string.mentor_title
}

@Composable
fun TopMentorScreen(
    navigateToMentorDetails : () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .background(Color.White)
            .fillMaxSize()
    ) {
        item {
            MentorItem(
                navigateToMentorDetails = navigateToMentorDetails
            )
        }
    }
}