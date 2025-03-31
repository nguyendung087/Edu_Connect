package com.example.educonnect.ui.courses

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import com.example.educonnect.ui.mentor.MentorImage
import com.example.educonnect.ui.navigation.NavigationDestination
import com.example.educonnect.ui.theme.EduConnectTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

object CourseDetailsDestination : NavigationDestination {
    override val route = "course_details"
    override val titleRes = R.string.course_detail_title
    const val courseIdArg = "courseId"
    val routeWithArgs = "$route/{$courseIdArg}"
}

enum class CourseDetailsTabs(
    val title : String,
) {
    About("About"),
    Lessons("Lessons"),
    Reviews("Reviews")
}


@Composable
fun CourseDetails(
    inner : PaddingValues = PaddingValues(0.dp),
    navigateBack : () -> Unit,
    onNavigateUp : () -> Unit
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

    Column(modifier = Modifier.fillMaxSize()) {
        CourseDetailsAppBar(
            navigateBack = navigateBack
        )
        Box(
            modifier = Modifier
                .offset(y = (-30).dp)
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
        ) {
            CourseDetailsContent(
                scope = scope,
                pagerState = pagerState,
                selectedTabIndex = selectedTabIndex.value,
                modifier = Modifier
                    .align(alignment = Alignment.TopCenter)
                    .verticalScroll(rememberScrollState())
            )
        }
        EnrollCourseBottomBar(
        )
    }
}

@Composable
private fun CourseDetailsAppBar(
    navigateBack : () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)

    ) {
        Image(
            painter = painterResource(R.drawable.course),
            contentDescription = "Course Preview",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxSize()

        )
        CustomAppBar(
            title = "",
            hasActions = true,
            icon = R.drawable.bookmark_svgrepo_com_dark,
            tint = Color.Black,
            contentDescription = "Bookmark",
            navigationOnClick = navigateBack,
            actionOnClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(
                    horizontal = 20.dp,
                    vertical = 16.dp
                ),
        )
    }
}

@Composable
private fun CourseDetailsContent(
    scope : CoroutineScope,
    pagerState : PagerState,
    selectedTabIndex : Int,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(
                horizontal = 20.dp,
                vertical = 30.dp
            ),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                "Best Seller",
                style = MaterialTheme.typography.labelLarge,
                color = Color(0xFFFFA800),
                modifier = Modifier
                    .background(
                        color = Color(0xFFFFA800).copy(
                            alpha = 0.15f
                        ),
                        shape = MaterialTheme.shapes.large,
                    )
                    .padding(
                        vertical = 5.dp,
                        horizontal = 8.dp
                    )

            )
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    Icons.Default.Star,
                    contentDescription = "Ratings",
                    tint = Color(0xFFFFA800),
                    modifier = Modifier.size(25.dp)
                )
                Text(
                    text = "4.5 (365 reviews)",
                    style = MaterialTheme.typography.labelLarge,
                    color = Color.Gray
                )
            }
        }

        Text(
            text = "Design Thinking Fundamental",
            style = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Medium
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row {
                Icon(Icons.Default.Person, contentDescription = null, tint = Color.Gray)
                Text(text = "Robert Green", color = Color.Gray, fontSize = 14.sp)
            }
            Row {
                Icon(Icons.Default.PlayArrow, contentDescription = null, tint = Color.Gray)
                Text(text = "32 Lessons", color = Color.Gray, fontSize = 14.sp)
            }
            Row {
                Icon(Icons.Default.Star, contentDescription = null, tint = Color.Gray)
                Text(text = "Certificate", color = Color.Gray, fontSize = 14.sp)
            }
        }

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
            CourseDetailsTabs.entries.forEachIndexed { index, currentTab ->
                Tab(
                    selected = selectedTabIndex == index,
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
//        HorizontalPager(
//            state = pagerState,
//            modifier = Modifier
//                .fillMaxWidth()
//                .weight(1f)
//        ) {
//        }

        val fullText = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua "
        val readMoreText = "Read more"
        Column(
            modifier = Modifier.padding(
                vertical = 12.dp
            ),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Text(
                    "About Course",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                )
                Text(
                    text = buildAnnotatedString {
                        append(fullText)
                        pushStringAnnotation(tag = "read_more", annotation = "read_more")
                        withStyle(style = SpanStyle(color = Color(0xFF0961F5))) {
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
                    "Tutor",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        MentorImage(
                            modifier = Modifier
                                .size(55.dp)
                                .clickable { },
                        )
                        Column(
                            verticalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Text(
                                "Wade Warren",
                                style = MaterialTheme.typography.bodyLarge,
                                fontWeight = FontWeight.SemiBold,
                            )
                            Text(
                                "Design Expert",
                                style = MaterialTheme.typography.labelMedium,
                                color = Color.Gray
                            )
                        }
                    }
                    Row(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        horizontalArrangement = Arrangement.spacedBy(14.dp),
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
                    }
                }
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
                            "Language",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Medium,
                            color = Color.Gray
                        )
                        Text(
                            "English",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Medium,
                        )
                    }
                }
            }
        }

        Column(
            modifier = Modifier.padding(
                vertical = 8.dp
            ),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text(
                    "Lesson (32)",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium,
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Section 1 - Introduction",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium,
                        color = Color.Gray
                    )
                    Text(
                        "15 Min",
                        style = MaterialTheme.typography.bodyLarge,
                        fontWeight = FontWeight.Medium,
                        color = Color(0xFF0961F5)
                    )
                }
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White),
                    border = CardDefaults.outlinedCardBorder(),
                    shape = MaterialTheme.shapes.large,
                    colors = CardDefaults.cardColors(
                        containerColor = Color.White
                    )
                ) {
                    Row(
                        modifier = Modifier.padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier.background(
                                color = Color(0xFF0961F5).copy(
                                    alpha = 0.15f
                                ),
                                shape = MaterialTheme.shapes.large
                            ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "01",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.SemiBold,
                                color = Color(0xFF0961F5),
                                modifier = Modifier.padding(12.dp)
                            )
                        }

                        Column(
                            modifier = Modifier.padding(
                                horizontal = 8.dp
                            )
                        ) {
                            Text(
                                "Introduction to Design Thinking",
                                style = MaterialTheme.typography.bodyMedium,
                                fontSize = 15.sp,
                                fontWeight = FontWeight.SemiBold,
                            )
                            Text(
                                "10:00",
                                style = MaterialTheme.typography.bodyMedium,
                                fontWeight = FontWeight.Medium,
                                color = Color.Gray
                            )
                        }

                        CustomIconButton(
                            icon = R.drawable.play_svgrepo_com,
                            contentDescription = "",
                            onClick = { /*TODO*/ },
                            tint = Color(0xFF0961F5)
                        )
                    }
                }
            }


        }

    }
}

@Composable
private fun EnrollCourseBottomBar(
    modifier: Modifier = Modifier
) {

}

@Composable
@Preview
private fun CourseDetailsPreview() {
    EduConnectTheme {
        CourseDetails(
            onNavigateUp = {},
            navigateBack = {}
        )
    }
}