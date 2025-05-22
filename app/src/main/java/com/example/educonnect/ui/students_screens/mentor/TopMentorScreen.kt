package com.example.educonnect.ui.students_screens.mentor

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.educonnect.R
import com.example.educonnect.ui.EduViewModelProvider
import com.example.educonnect.ui.components.CustomAppBar
import com.example.educonnect.ui.navigation.NavigationDestination
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

object TopMentorDestination : NavigationDestination {
    override val route = "top_mentor"
    override val titleRes = R.string.mentor_title
}

@Composable
fun TopMentorScreen(
    innerPadding : PaddingValues,
    navigateBack : () -> Unit,
    navigateToChatScreen: (String) -> Unit,
    navigateToMentorDetails : (String) -> Unit,
    viewModel: TopMentorViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = EduViewModelProvider.Factory
    )
) {
    val uiState = viewModel.mentorUiState.collectAsState()
    Scaffold(
        modifier = Modifier.background(Color.White),
        topBar = {
            CustomAppBar(
                title = stringResource(id = R.string.mentor_title),
                hasActions = false,
                navigationOnClick = navigateBack,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 20.dp,
                        vertical = 16.dp
                    ),
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .background(Color.White)
                .fillMaxWidth()
                .padding(
                    top = it.calculateTopPadding(),
                    bottom = it.calculateBottomPadding()
                )
                .padding(innerPadding)
        ) {
            items(
                items = uiState.value.mentorList,
                key = { it.teacherId }
            ) { mentor ->
                MentorItem(
                    mentor = mentor,
                    navigateToMentorDetails = navigateToMentorDetails,
                    onClick = {
                        val studentId = Firebase.auth.currentUser?.uid ?: ""
                        viewModel.checkAndCreateConversation(studentId, mentor.teacherId) { conversationId ->
                            navigateToChatScreen(conversationId)
                        }
                    }
                )
            }
        }
    }
}