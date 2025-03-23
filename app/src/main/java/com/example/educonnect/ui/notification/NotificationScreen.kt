package com.example.educonnect.ui.notification

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.educonnect.R
import com.example.educonnect.ui.components.CustomIconButton
import com.example.educonnect.ui.navigation.NavigationDestination
import com.example.educonnect.ui.theme.EduConnectTheme

object NotificationDestination : NavigationDestination {
    override val route = "notification"
    override val titleRes = R.string.notification_title
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NotificationScreen(
    navigateBack : () -> Unit
) {
    var isSelected by remember {
        mutableStateOf(true)
    }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Notification",
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                navigationIcon = {
                    CustomIconButton(
                        icon = R.drawable.arrow_left_svgrepo_com,
                        contentDescription = "Navigation back",
                        onClick = navigateBack,
                        tint = Color.Black,
                        modifier = Modifier
                            .background(
                                color = Color.White,
                                shape = MaterialTheme.shapes.large
                            )

                            .size(45.dp)
                            .border(
                                width = 1.dp,
                                color = Color.Black.copy(
                                    alpha = 0.2f
                                ),
                                shape = MaterialTheme.shapes.large,
                            )
                    )
                },
                actions = {
                    TextButton(
                        onClick = {  },
                        shape = MaterialTheme.shapes.medium,
                        border = BorderStroke(
                            color = Color.Black.copy(
                                alpha = 0.1f,
                            ),
                            width = 1.dp
                        ),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if (isSelected) Color(0xFF0961F5) else Color.White ,
                            contentColor = if (isSelected) Color.White else Color.Black,
                        ),
                    ) {
                        Text(
                            text = "2 NEW",
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 20.dp,
                        vertical = 16.dp
                    ),
            )
        }
    ) { innerPadding ->
        NotificationContent(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = innerPadding.calculateTopPadding())
        )
    }
}

@Composable
private fun NotificationContent(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                "TODAY",
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Gray
            )
            Text(
                "Mark all as read",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF0961F5)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 18.dp,
                    horizontal = 20.dp
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = Color(0xFFF4F6F9),
                        shape = MaterialTheme.shapes.large
                    )
                    .size(65.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.laptop_code_svgrepo_com),
                    contentDescription = "",
                    tint = Color(0xFF0961F5),
                    modifier = Modifier
                        .size(25.dp)
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Certificate Available",
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = 18.sp
                    )
                    Text(
                        "1h",
                        style = MaterialTheme.typography.titleSmall,
                        color = Color.Gray
                    )
                }
                Text(
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                    style = MaterialTheme.typography.labelLarge,
                    fontSize = 13.sp,
                    color = Color.Gray,
                )
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFFF4F6F9))
                .padding(
                    vertical = 18.dp,
                    horizontal = 20.dp
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .background(
                        color = Color.White,
                        shape = MaterialTheme.shapes.large
                    )
                    .size(65.dp),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter = painterResource(R.drawable.laptop_code_svgrepo_com),
                    contentDescription = "",
                    tint = Color(0xFF0961F5),
                    modifier = Modifier
                        .size(25.dp)
                )
            }
            Column(
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.Bottom,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Certificate Available",
                        style = MaterialTheme.typography.titleMedium,
                        fontSize = 18.sp
                    )
                    Text(
                        "1h",
                        style = MaterialTheme.typography.titleSmall,
                        color = Color.Gray
                    )
                }
                Text(
                    "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                    style = MaterialTheme.typography.labelLarge,
                    fontSize = 13.sp,
                    color = Color.Gray,
                )
            }
        }
    }
}

@Composable
@Preview
private fun NotificationPreview() {
    EduConnectTheme {
        NotificationScreen(
            navigateBack = {}
        )
    }
}