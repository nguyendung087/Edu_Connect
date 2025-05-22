package com.example.educonnect.ui.chat

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.educonnect.R
import com.example.educonnect.data.model.chat.Message
import com.example.educonnect.ui.EduViewModelProvider
import com.example.educonnect.ui.components.CustomAppBar
import com.example.educonnect.ui.navigation.NavigationDestination
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

object ChatDestination : NavigationDestination {
    override val route = "chat"
    override val titleRes = R.string.chat_title
    const val conversationIdArg = "conversationId"
    val routeWithArgs = "$route/{$conversationIdArg}"
}

@Composable
fun ChatScreen(
    viewModel : ChatViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = EduViewModelProvider.Factory
    )
) {
    val uiState = viewModel.chatUiState.collectAsState()
    LaunchedEffect(key1 = true) {
        viewModel.getMessages()
    }
    Scaffold(
        topBar = {
            CustomAppBar(
                title = uiState.value.receiverName,
                hasActions = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 20.dp,
                        vertical = 16.dp
                    ),
//                navigationOnClick = navigateBack
            )
        }
    ) {
        Column(
            modifier = Modifier
                .background(Color.White)
                .fillMaxSize()
                .padding(
                    start = 20.dp,
                    end = 20.dp,
                    top = it.calculateTopPadding(),
                    bottom = it.calculateBottomPadding()
                )
//                .padding(innerPadding)
        ) {
            ChatMessages(
                messages = uiState.value.messages,
                onSendMessage = {
                    viewModel.sendMessage(it)
                }
            )
        }
    }
}

@Composable
fun ChatMessages(
    messages: List<Message>,
    onSendMessage: (String) -> Unit,
) {
    val hideKeyboardController = LocalSoftwareKeyboardController.current

    val msg = remember {
        mutableStateOf("")
    }
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn {
            items(
                items = messages
            ){ message ->
                ChatBubble(message = message)
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(8.dp)
                .background(Color.LightGray), verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = msg.value,
                onValueChange = { msg.value = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text(text = "Type a message") },
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    hideKeyboardController?.hide()
                })
            )
            IconButton(onClick = {
                onSendMessage(msg.value)
                msg.value = ""
            }) {
                Icon(imageVector = Icons.AutoMirrored.Filled.Send, contentDescription = "send")
            }
        }
    }
}

@Composable
fun ChatBubble(message: Message) {
    val isCurrentUser = message.senderId == Firebase.auth.currentUser?.uid
    val bubbleColor = if (isCurrentUser) {
        Color.Blue
    } else {
        Color.Green
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 8.dp)

    ) {
        val alignment = if (!isCurrentUser) Alignment.CenterStart else Alignment.CenterEnd
        Box(
            modifier = Modifier
                .padding(8.dp)
                .background(color = bubbleColor, shape = RoundedCornerShape(8.dp))
                .align(alignment)
        ) {
            Text(
                text = message.content!!, color = Color.White, modifier = Modifier.padding(8.dp)
            )
        }

    }
}

