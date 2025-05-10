package com.example.educonnect.ui.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.educonnect.ui.students_screens.courses.CourseTabs
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun CustomTabRow(
    selectedTabIndex : Int,
    scope : CoroutineScope,
    pagerState : PagerState,
) {

    TabRow(
        selectedTabIndex = selectedTabIndex,
        indicator = { tabPositions ->
            SecondaryIndicator(
                modifier = Modifier
                    .tabIndicatorOffset(
                        tabPositions[selectedTabIndex]
                    ),
                height = 4.dp,
                color = Color(0xFF0961F5)
            )
        },
        modifier = Modifier
            .fillMaxWidth()
    ) {
        CourseTabs.entries.forEachIndexed { index, currentTab ->
            Tab(
                selected = selectedTabIndex == index,
                selectedContentColor = Color(0xFF0961F5),
                unselectedContentColor = Color.Gray,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(
                            currentTab.ordinal
                        )
                    }
                },
                text = {
                    Text(
                        text = currentTab.text,
                        fontSize = 18.sp
                    )
                },
            )
        }
    }
}