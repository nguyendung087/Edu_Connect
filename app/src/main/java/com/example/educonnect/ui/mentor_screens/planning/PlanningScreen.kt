package com.example.educonnect.ui.mentor_screens.planning

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.educonnect.R
import com.example.educonnect.ui.EduViewModelProvider
import com.example.educonnect.ui.authentication.LocalAuthState
import com.example.educonnect.ui.components.CustomAppBar
import com.example.educonnect.ui.mentor_screens.assignments.AssignmentsTabs
import com.example.educonnect.ui.mentor_screens.course_management.CourseManageScreen
import com.example.educonnect.ui.mentor_screens.course_management.CourseManageViewModel
import com.example.educonnect.ui.mentor_screens.lessons.LessonTabs
import com.example.educonnect.ui.mentor_screens.students.StudentsTabs
import com.example.educonnect.ui.navigation.NavigationDestination
import kotlinx.coroutines.launch

object PlanningDestination : NavigationDestination {
    override val route = "planning"
    override val titleRes = R.string.planning
    const val courseIdArg = "courseId"
    val routeWithArgs = "$route/{$courseIdArg}"
}

enum class PlanningTabs(
    val title : String,
) {
    Students("Students"),
    Lessons("Lessons"),
    Assignments("Assignments"),
    GradeBooks("Grade Books"),
}

@Composable
fun PlanningScreen(
    innerPadding : PaddingValues,
    navigateToAssignmentDetails : (String) -> Unit,
    viewModel : PlanningViewModel = viewModel(
        factory = EduViewModelProvider.Factory
    )
) {
    val uiState = viewModel.uiState
    val authState = LocalAuthState.current

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

    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(
        pageCount = {
            PlanningTabs.entries.size
        }
    )
    val selectedTabIndex = remember {
        derivedStateOf { pagerState.currentPage }
    }

    Scaffold (
        topBar = {
            CustomAppBar(
                title = "My Planning",
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
                .background(Color.White)
                .fillMaxSize()
                .padding(
                    start = 20.dp,
                    end = 20.dp,
                    top = it.calculateTopPadding(),
                    bottom = it.calculateBottomPadding()
                )
                .padding(innerPadding)
        ) {
            TabRow(
                selectedTabIndex = selectedTabIndex.value,
                indicator = { tabPositions ->
                    SecondaryIndicator(
                        modifier = Modifier
                            .tabIndicatorOffset(
                                tabPositions[selectedTabIndex.value]
                            ),
                        height = 4.dp,
                        color = Color(0xFF0961F5)
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                PlanningTabs.entries.forEachIndexed { index, currentTab ->
                    Tab(
                        selected = selectedTabIndex.value == index,
                        selectedContentColor = Color(0xFF0961F5),
                        unselectedContentColor = Color.DarkGray,
                        modifier = Modifier.background(Color.White),
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(
                                    currentTab.ordinal
                                )
                            }
                        },
                        text = {
                            Text(
                                text = currentTab.title,
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.Medium
                            )
                        },
                    )
                }
            }
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
            ) { page ->
                when (page) {
                    0 -> StudentsTabs()
                    1 -> LessonTabs()
                    2 -> AssignmentsTabs(
                        navigateToAssignmentDetails = navigateToAssignmentDetails
                    )
                }
            }
        }
    }
}