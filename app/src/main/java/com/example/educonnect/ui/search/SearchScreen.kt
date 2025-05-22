package com.example.educonnect.ui.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.educonnect.R
import com.example.educonnect.data.model.courses.Course
import com.example.educonnect.data.model.courses.CourseWithTeacher
import com.example.educonnect.data.model.users.TeacherProfile
import com.example.educonnect.ui.EduViewModelProvider
import com.example.educonnect.ui.authentication.LocalAuthState
import com.example.educonnect.ui.components.ActionButton
import com.example.educonnect.ui.components.CustomTabRow
import com.example.educonnect.ui.components.NavigationButton
import com.example.educonnect.ui.navigation.NavigationDestination
import com.example.educonnect.ui.students_screens.courses.CourseDetailsTabs
import com.example.educonnect.ui.students_screens.courses.CourseTabs
import com.example.educonnect.ui.students_screens.mentor.MentorItem
import kotlinx.coroutines.launch

object StudentSearchDestination : NavigationDestination {
    override val route = "seacrch"
    override val titleRes = R.string.search_title
}

enum class SearchTabs(
    val title : String,
) {
    Course("Course"),
    Mentor("Mentor"),
}

@Composable
fun StudentSearchScreen(
    innerPadding : PaddingValues,
    onNavigateUp: () -> Unit,
    navigateToCourseDetails: (String) -> Unit,
    navigateToMentorDetails: (String) -> Unit,
    viewModel : SearchViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = EduViewModelProvider.Factory
    )
) {
    val uiState = viewModel.searchUiState.collectAsState()

    val authState = LocalAuthState.current
    LaunchedEffect(authState.currentUserId) {
        viewModel.loadUser(authState.currentUserId)
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

    var searchText by remember { mutableStateOf("") }
    var isSearchClick by remember {
        mutableStateOf(false)
    }

    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(
        pageCount = {
            SearchTabs.entries.size
        }
    )
    val selectedTabIndex = remember {
        derivedStateOf { pagerState.currentPage }
    }

    Scaffold(
        topBar = {
            SearchAppBar(
                searchText,
                onNavigateUp = onNavigateUp,
                onSearchAction = {
                    viewModel.addRecentSearch(searchText, authState.currentUserId)
                    isSearchClick = true
                    viewModel.search(searchText)
                },
                onSearchTextChange = {
                    searchText = it
                    if(searchText == "") {
                        isSearchClick = false
                    }
                }
            )
        }
    ) {
        if (searchText.isEmpty()) {
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .fillMaxWidth()
                    .padding(
                        start = 8.dp,
                        end = 8.dp,
                        top = it.calculateTopPadding(),
                        bottom = 8.dp
                    )
                    .padding(innerPadding)
            ) {
                if (uiState.value.recentSearches.isNotEmpty()) {
                    RecentSearchesSection(
                        recentSearches = uiState.value.recentSearches,
                        onSearchClick = { term ->
                            viewModel.search(term)
                            searchText = term
                            isSearchClick = true
                        },
                        onRemoveSearch = { term ->
                            viewModel.removeRecentSearch(term, authState.currentUserId!!)
                            isSearchClick = false
                        }
                    )
                }
                if (uiState.value.recentView.isNotEmpty()) {
                    RecentViewSection(
                        recentView = uiState.value.recentView,
                        navigateToCourseDetails = navigateToCourseDetails
                    )
                }
            }
        } else {
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
                    SearchTabs.entries.forEachIndexed { index, currentTab ->
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
                        0 -> CourseResultsSection(
                            searchResults = uiState.value.courseResults,
                            isSearchClick = isSearchClick,
                            navigateToCourseDetails = navigateToCourseDetails
                        )
                        1 -> MentorResultsSection(
                            searchResults = uiState.value.mentorResults,
                            isSearchClick = isSearchClick,
                            navigateToMentorDetails = navigateToMentorDetails
                        )
                    }

                }
            }
        }
    }
}

@Composable
private fun SearchAppBar(
    searchText: String,
    onNavigateUp : () -> Unit,
    onSearchTextChange: (String) -> Unit,
    onSearchAction : () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = 20.dp,
                vertical = 16.dp
            ),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        NavigationButton(
            icon = R.drawable.arrow_left_svgrepo_com,
            onClick = onNavigateUp,
            tint = Color.Black,
            modifier = Modifier
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(100.dp)
                )

                .size(45.dp)
                .border(
                    width = 1.dp,
                    color = Color.Black.copy(
                        alpha = 0.2f
                    ),
                    shape = RoundedCornerShape(100.dp),
                )
        )
        SearchBar(
            searchText = searchText,
            onSearchAction = onSearchAction,
            onSearchTextChange = onSearchTextChange
        )
    }
}

@Composable
private fun CourseResultsSection(
    searchResults: List<CourseWithTeacher>,
    isSearchClick : Boolean,
    navigateToCourseDetails: (String) -> Unit,
) {
    if (!isSearchClick) {
        Column {

        }
    } else {
        if (searchResults.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    "Không tìm thấy kết quả",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.W700
                )
            }
        } else {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                searchResults.forEach { result ->
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = Color.White
                        ),
                        border = CardDefaults.outlinedCardBorder(),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                vertical = 8.dp
                            )
                            .clickable {
                                navigateToCourseDetails(result.course.courseId)
                            }
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                        ) {
                            Box(
                                modifier = Modifier
                                    .height(130.dp)
                                    .weight(2.5f)
                            ) {
                                Image(
                                    painter = painterResource(result.course.courseImage),
                                    contentDescription = result.course.title,
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.clip(
                                        shape = RoundedCornerShape(10.dp)
                                    )
                                )
                            }
                            Column(
                                modifier = Modifier
                                    .align(Alignment.CenterVertically)
                                    .weight(3.5f)
                                    .padding(
                                        start = 10.dp
                                    ),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {

                                Text(
                                    result.course.title,
                                    fontSize = 16.sp,
                                    fontWeight = FontWeight.W500,
                                )
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                                ) {
                                    Icon(
                                        Icons.Filled.AccountCircle,
                                        contentDescription = "Course Author",
                                        tint = Color.Gray
                                    )
                                    Text(
                                        result.teacher.name,
                                        fontWeight = FontWeight.W500,
                                        color = Color.Gray
                                    )
                                }
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Text(
                                        "$${result.course.cost}",
                                        fontWeight = FontWeight.W700,
                                        color = Color(0xFF0961F5),
                                        fontSize = 16.sp
                                    )
                                    Text(
                                        "Best Seller",
                                        fontWeight = FontWeight.W700,
                                        color = Color(0xFFFFA800),
                                        fontSize = 12.sp,
                                        modifier = Modifier
                                            .background(
                                                color = Color(0xFFFFA800).copy(
                                                    alpha = 0.15f
                                                ),
                                                shape = RoundedCornerShape(100.dp),
                                            )
                                            .padding(
                                                vertical = 5.dp,
                                                horizontal = 8.dp
                                            )

                                    )
                                }
                            }
//                BookmarkButton(
//                    onRemove = { onShowRemoveConfirmation(bookmarkedCourse) },
//                    modifier = Modifier
//                        .weight(0.6f)
//                        .size(30.dp)
//                )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun MentorResultsSection(
    searchResults: List<TeacherProfile>,
    isSearchClick: Boolean,
    navigateToMentorDetails: (String) -> Unit,
) {
    if (!isSearchClick) {
        Column {

        }
    } else {
        if (searchResults.isEmpty()) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    "Không tìm thấy kết quả",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.W700
                )
            }
        } else {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                searchResults.forEach { result ->
                    MentorItem(
                        navigateToMentorDetails = navigateToMentorDetails,
                        onClick = {

                        },
                        mentor = result
                    )
                }
            }
        }
    }
}

@Composable
private fun RecentSearchesSection(
    recentSearches: List<String>,
    onRemoveSearch: (String) -> Unit,
    onSearchClick : (String) -> Unit
) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            "Tìm kiếm gần đây",
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 16.sp,
            color = Color.Black,
            fontWeight = FontWeight.W900
        )
        recentSearches.forEach { term ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp)
                    .clickable { onSearchClick(term) },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    term,
                    style = MaterialTheme.typography.titleMedium,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W700,
                    color = Color.Gray
                )
                IconButton(onClick = { onRemoveSearch(term) }) {
                    Icon(Icons.Default.Close, contentDescription = "Xóa")
                }
            }
        }
    }
}

@Composable
private fun RecentViewSection(
    recentView : List<CourseWithTeacher>,
    navigateToCourseDetails : (String) -> Unit
) {
    recentView.forEach { course ->
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            ),
            border = CardDefaults.outlinedCardBorder(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 8.dp
                )
                .clickable {
                    navigateToCourseDetails(course.course.courseId)
                }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
            ) {
                Box(
                    modifier = Modifier
                        .height(130.dp)
                        .weight(2.5f)
                ) {
                    Image(
                        painter = painterResource(course.course.courseImage),
                    contentDescription = course.course.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.clip(
                        shape = RoundedCornerShape(10.dp)
                    )
                    )
                }
                Column(
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .weight(3.5f)
                        .padding(
                            start = 10.dp
                        ),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    Text(
                        course.course.title,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.W500,
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Icon(
                            Icons.Filled.AccountCircle,
                            contentDescription = "Course Author",
                            tint = Color.Gray
                        )
                        Text(
                            course.teacher.name,
                            fontWeight = FontWeight.W500,
                            color = Color.Gray
                        )
                    }
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            "$${course.course.cost}",
                            fontWeight = FontWeight.W700,
                            color = Color(0xFF0961F5),
                            fontSize = 16.sp
                        )
                        Text(
                            "Best Seller",
                            fontWeight = FontWeight.W700,
                            color = Color(0xFFFFA800),
                            fontSize = 12.sp,
                            modifier = Modifier
                                .background(
                                    color = Color(0xFFFFA800).copy(
                                        alpha = 0.15f
                                    ),
                                    shape = RoundedCornerShape(100.dp),
                                )
                                .padding(
                                    vertical = 5.dp,
                                    horizontal = 8.dp
                                )

                        )
                    }
                }
//                BookmarkButton(
//                    onRemove = { onShowRemoveConfirmation(bookmarkedCourse) },
//                    modifier = Modifier
//                        .weight(0.6f)
//                        .size(30.dp)
//                )
            }
        }
    }
}



@Composable
private fun SearchBar(
    searchText : String,
    onSearchTextChange : (String) -> Unit,
    onSearchAction: () -> Unit
) {
    OutlinedTextField(
        value = searchText,
        onValueChange = onSearchTextChange,
        placeholder = { Text("Tìm kiếm", color = Color.Gray) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearchAction()
            }
        ),
        leadingIcon = {
            Icon(
                Icons.Rounded.Search,
                tint = Color(0xFF0961F5),
                contentDescription = "Search"
            )
        },
        trailingIcon = {
            if (searchText.isNotEmpty()) {
                IconButton(onClick = { onSearchTextChange("") }) {
                    Icon(Icons.Default.Close, contentDescription = "Xóa")
                }
            }
        },
        colors = TextFieldDefaults.colors(
            focusedTextColor = Color.Black,
            unfocusedTextColor = Color.DarkGray,
            focusedContainerColor = Color.White,
            focusedIndicatorColor = Color.Transparent,
            unfocusedContainerColor = Color.White,
            errorLabelColor = Color.Red,
            unfocusedIndicatorColor = Color.Transparent
        ),
        shape = MaterialTheme.shapes.medium,
        modifier = Modifier
            .fillMaxWidth()
            .height(68.dp)
            .padding(
                vertical = 8.dp,
                horizontal = 16.dp
            )
            .border(
                color = Color.Black.copy(
                    alpha = 0.2f
                ),
                shape = RoundedCornerShape(20.dp),
                width = 1.dp
            )
    )
}

