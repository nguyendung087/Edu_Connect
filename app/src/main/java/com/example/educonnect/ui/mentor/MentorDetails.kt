package com.example.educonnect.ui.mentor

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.educonnect.R
import com.example.educonnect.ui.components.CustomAppBar
import com.example.educonnect.ui.components.CustomIconButton
import com.example.educonnect.ui.courses.CourseTabs
import com.example.educonnect.ui.navigation.NavigationDestination
import com.example.educonnect.ui.theme.EduConnectTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

object MentorDetailsDestination : NavigationDestination {
    override val route = "mentor_details"
    override val titleRes = R.string.mentor_detail_title
    const val mentorIdArg = "mentorId"
    val routeWithArgs = "$route/{$mentorIdArg}"
}

enum class MentorDetailsTabs(
    val title : String,
) {
    About("About"),
    Courses("Courses"),
    Reviews("Reviews")
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MentorDetails(
    navigateBack : () -> Unit,
    onNavigateUp : () -> Unit
) {
    Scaffold(
        topBar = {
            CustomAppBar(
                title = "Mentor Details",
                hasActions = false,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 20.dp,
                        vertical = 16.dp
                    ),
                navigationOnClick = navigateBack
            )
        }
    ) {
        val scope = rememberCoroutineScope()
        val pagerState = rememberPagerState(
            pageCount = {
                CourseTabs.entries.size
            }
        )
        val selectedTabIndex = remember {
            derivedStateOf { pagerState.currentPage }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = it.calculateTopPadding())
                .padding(horizontal = 20.dp),
        ) {
            MentorCard()
            HorizontalDivider(
                modifier = Modifier.padding(
                    top = 12.dp,
                    bottom = 4.dp
                )
            )
            MentorInformationTabs(
                scope = scope,
                pagerState = pagerState,
                selectedTabIndex = selectedTabIndex.value
            )
        }
    }
}

@Composable
private fun MentorCard(
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = 12.dp
            ),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            MentorImage(
                modifier = Modifier
                    .size(75.dp)
                    .clickable { },
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    "Wade Warren",
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    "Design Expert",
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.Gray
                )
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Icon(
                        Icons.Default.Star,
                        contentDescription = "Ratings",
                        tint = Color(0xFFFFA800),
                        modifier = Modifier.size(20.dp)
                    )
                    Text(
                        text = "4.5 (365 reviews)",
                        style = MaterialTheme.typography.labelLarge,
                        color = Color.Gray
                    )
                }
            }
        }
        Row(
            modifier = Modifier.align(Alignment.CenterVertically),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            CustomIconButton(
                icon = R.drawable.laptop_code_svgrepo_com,
                contentDescription = "",
                onClick = { /*TODO*/ },
                tint = Color(0xFF0961F5),
                modifier = Modifier
                    .background(
                        color = Color(0xFFF4F6F9),
                        shape = MaterialTheme.shapes.large
                    )
            )
            CustomIconButton(
                icon = R.drawable.laptop_code_svgrepo_com,
                contentDescription = "",
                onClick = { /*TODO*/ },
                tint = Color(0xFF0961F5),
                modifier = Modifier
                    .background(
                        color = Color(0xFFF4F6F9),
                        shape = MaterialTheme.shapes.large
                    )
            )
//            InteractionIcon(
//                modifier = Modifier
//                    .background(
//                        color = Color(0xFFF4F6F9),
//                        shape = RoundedCornerShape(100.dp)
//                    )
//                    .size(45.dp),
//            )
//            InteractionIcon(
//                modifier = Modifier
//                    .background(
//                        color = Color(0xFFF4F6F9),
//                        shape = RoundedCornerShape(100.dp)
//                    )
//                    .size(45.dp),
//            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun MentorInformationTabs(
    scope : CoroutineScope,
    pagerState : PagerState,
    selectedTabIndex : Int,
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
        MentorDetailsTabs.entries.forEachIndexed { index, currentTab ->
            Tab(
                selected = selectedTabIndex == index,
                selectedContentColor = Color(0xFF0961F5),
                unselectedContentColor = Color.DarkGray,
                modifier = Modifier
                    .padding(bottom = 4.dp),
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
//    HorizontalPager(
//        state = pagerState,
//        modifier = Modifier
//            .fillMaxWidth()
//    ) {
//    }

    val fullText = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua "
    val readMoreText = "Read more"

    Column(
        modifier = Modifier.padding(
            vertical = 16.dp
        ),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                "About",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
            )
            Text(
                text = buildAnnotatedString {
                    append(fullText)
                    pushStringAnnotation(tag = "read_more", annotation = "read_more")
                    withStyle(style = SpanStyle(color = Color.Blue)) {
                        append(readMoreText)
                    }
                    pop()
                },
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                color = Color.Gray,
            )
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                "Info",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(130.dp)
            ) {
                Column {
                    Text(
                        "Students",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium,
                        color = Color.Gray
                    )
                    Text(
                        "123,456",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium,
                    )
                }
                Column {
                    Text(
                        "Courses",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium,
                        color = Color.Gray
                    )
                    Text(
                        "32",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium,
                    )
                }
            }
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                "Experience",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Box(
                    modifier = Modifier.border(
                        width = 1.dp,
                        color = Color.LightGray,
                        shape = MaterialTheme.shapes.large,
                    )

                ) {
                    Icon(
                        painterResource(R.drawable.google),
                        contentDescription = "Google",
                        tint = Color.Unspecified,
                        modifier = Modifier
                            .padding(8.dp)
                    )
                }
                Column {
                    Text(
                        "Senior UX/UI Designer",
                        style = MaterialTheme.typography.bodyLarge,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium,
                    )
                    Text(
                        "2020 - Now",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        color = Color.Gray
                    )
                }
            }
            Text(
                fullText,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                color = Color.Gray
            )
        }
    }
}

@Composable
@Preview
private fun MentorDetailsPreview() {
    EduConnectTheme {
        MentorDetails(
            onNavigateUp = {},
            navigateBack = {}
        )
    }
}