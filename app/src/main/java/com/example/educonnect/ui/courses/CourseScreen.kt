package com.example.educonnect.ui.courses

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.educonnect.R
import com.example.educonnect.ui.EduViewModelProvider
import com.example.educonnect.ui.components.CustomAppBar
import com.example.educonnect.ui.components.CustomTabRow
import com.example.educonnect.ui.navigation.NavigationDestination

object CourseDestination : NavigationDestination {
    override val route = "course"
    override val titleRes = R.string.course_title
}

enum class CourseTabs(
    val text : String
) {
    OngoingCourse(text = "Ongoing"),
    CompletedCourse(text = "Completed")
}

@Composable
fun CourseScreen(
    innerPadding : PaddingValues,
    navigateToCourseDetails : (String) -> Unit,
    viewModel: CourseViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = EduViewModelProvider.Factory
    )
) {
    val uiState = viewModel.courseUiState.collectAsState()

    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(
        pageCount = {
            CourseTabs.entries.size
        }
    )
    val selectedTabIndex = remember {
        derivedStateOf { pagerState.currentPage }
    }

    Scaffold(

        topBar = {
            CustomAppBar(
                title = "My Course",
                hasActions = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 20.dp,
                        vertical = 16.dp
                    ),
            )
        },

    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    top = it.calculateTopPadding(),
                    bottom = it.calculateBottomPadding()
                )
                .padding(innerPadding)
                .background(Color.White)
        ) {
            CustomTabRow(
                selectedTabIndex = selectedTabIndex.value,
                scope = scope,
                pagerState = pagerState
            )
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                LazyColumn(
                    modifier = Modifier
                        .padding(
                            vertical = 8.dp,
                            horizontal = 12.dp
                        )
                ) {
                    items(
                        items = uiState.value.courseList,
                        key = { it.course.courseId }
                    ) { course ->
                        CourseItem(
                            navigateToCourseDetails = navigateToCourseDetails,
                            course = course
                        )
                    }
                }
            }

        }
    }

}

//@Composable
//@Preview
//private fun CoursePreview() {
//    CourseScreen()
//}