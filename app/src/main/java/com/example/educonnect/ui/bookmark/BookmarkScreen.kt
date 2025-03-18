package com.example.educonnect.ui.bookmark

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.educonnect.R
import com.example.educonnect.ui.components.CustomAppBar
import com.example.educonnect.ui.navigation.NavigationDestination

object BookmarkDestination : NavigationDestination {
    override val route = "bookmark"
    override val titleRes = R.string.bookmark_title
}

@Composable
fun BookmarkScreen() {
    Scaffold(
        topBar = {
            CustomAppBar(
                title = "Bookmark",
                hasActions = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 20.dp,
                        vertical = 16.dp
                    ),
            )
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = it.calculateTopPadding())
                .padding(
                    horizontal = 12.dp
                )
        ) {
            CourseTagList()
            BookmarkList()
        }
    }
}

@Composable
@Preview
private fun BookmarkPreview() {
    BookmarkScreen()
}