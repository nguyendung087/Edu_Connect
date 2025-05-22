package com.example.educonnect.ui.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.educonnect.R
import com.example.educonnect.data.model.chat.Conversation
import com.example.educonnect.ui.EduViewModelProvider
import com.example.educonnect.ui.authentication.LocalAuthState
import com.example.educonnect.ui.components.CustomAppBar
import com.example.educonnect.ui.students_screens.mentor.MentorImage
import com.example.educonnect.ui.navigation.NavigationDestination
import com.example.educonnect.ui.theme.EduConnectTheme

object ConversationDestination : NavigationDestination {
    override val route = "conversation"
    override val titleRes = R.string.conversation_title
}

@Composable
fun ConversationScreen(
    viewModel: ConversationViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = EduViewModelProvider.Factory
    )
) {
    val uiState = viewModel.conversationUiState.collectAsState()

    val authState = LocalAuthState.current
    LaunchedEffect(authState.currentUserId) {
    }

    if (!authState.isLoggedIn) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Vui lòng đăng nhập để tiếp tục",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 18.sp,
                color = Color.Black
            )
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        ConversationAppBar(
            Modifier
                .fillMaxWidth()
                .background(Color(0xFF0961F5))
                .padding(
                    top = 20.dp,
                    bottom = 45.dp
                )
        )
        Box(
            modifier = Modifier
                .offset(y = (-30).dp)
                .clip(
                    RoundedCornerShape(
                        topStart = 20.dp,
                        topEnd = 20.dp,
                    )
                )
                .background(Color.White)
        ) {
            ConversationList(
                conversations = uiState.value.conversation,
                receiverName = uiState.value.conversationName,
                receiverImage = "",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = 18.dp,
                        horizontal = 20.dp
                    )
            )
        }
    }
}

@Composable
private fun ConversationAppBar(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        CustomAppBar(
            title = "Chat",
            hasActions = false,
            tint = Color(0xFF0961F5),
            textColor = Color.White,
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(
                    horizontal = 20.dp,
                    vertical = 16.dp
                ),
        )
        SearchBar()
    }
}

@Composable
private fun ConversationList(
    receiverName : String,
    receiverImage : String,
    conversations : List<Conversation>,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(
            items = conversations
        ) { conversation ->
            ConversationCard(
                receiverName = receiverName,
                receiverImage = receiverImage,
                conversation = conversation
            )
        }
    }
}

@Composable
private fun ConversationCard(
    receiverName : String,
    receiverImage : String,
    conversation: Conversation
) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = 8.dp,
                horizontal = 4.dp
            ),
        border = CardDefaults.outlinedCardBorder(),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                MentorImage(
                    mentorImage = receiverImage,
                    modifier = Modifier
                        .size(45.dp),
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        receiverName,
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        "Perfect, will check it",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.Gray
                    )
                }
            }

            Text(
                "${conversation.updatedAt}",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                color = Color.Gray
            )
        }
    }
}

@Composable
private fun SearchBar() {
    var searchText by remember { mutableStateOf("") }

    TextField(
        value = searchText,

        onValueChange = { searchText = it },

        placeholder = { Text("Search Mentors", color = Color.Gray) },
        leadingIcon = {
            Icon(
                Icons.Rounded.Search,
                tint = Color(0xFF0961F5),
                contentDescription = "Search Mentors"
            )
        },
        colors = TextFieldDefaults.colors(
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent

        ),
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
            .height(68.dp)
            .padding(
                vertical = 8.dp,
                horizontal = 20.dp
            )
    )
}