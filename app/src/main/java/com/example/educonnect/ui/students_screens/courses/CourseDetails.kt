package com.example.educonnect.ui.students_screens.courses

import android.annotation.SuppressLint
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults.SecondaryIndicator
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
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
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.educonnect.R
import com.example.educonnect.data.model.courses.CourseWithTeacher
import com.example.educonnect.data.model.courses.Lesson
import com.example.educonnect.data.model.users.TeacherProfile
import com.example.educonnect.ui.EduViewModelProvider
import com.example.educonnect.ui.authentication.LocalAuthState
import com.example.educonnect.ui.components.ConfirmationNotification
import com.example.educonnect.ui.components.CustomAppBar
import com.example.educonnect.ui.components.CustomIconButton
import com.example.educonnect.ui.students_screens.mentor.InteractionIcon
import com.example.educonnect.ui.students_screens.mentor.MentorImage
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


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CourseDetails(
    innerPadding : PaddingValues = PaddingValues(0.dp),
    navigateBack : () -> Unit,
    navigateToMentorDetails : (String) -> Unit,
    onNavigateUp : () -> Unit,
    viewModel: CourseDetailsViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = EduViewModelProvider.Factory
    )
) {
    val uiState = viewModel.courseUiState.collectAsState()

    val scope = rememberCoroutineScope()
    val pagerState = rememberPagerState(
        pageCount = {
            CourseDetailsTabs.entries.size
        }
    )
    val selectedTabIndex = remember {
        derivedStateOf { pagerState.currentPage }
    }

    val authState = LocalAuthState.current
    val courseId = uiState.value.courseDetails.course.courseId
    if (!authState.isLoggedIn) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator()
        }
        return
    }

    val isBookmarked by viewModel.isCourseBookmarked(
        authState.currentUserId!!,
        courseId
    )
        .collectAsState(initial = false)

    var showRemoveConfirmation by remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState()

    var showEnrollConfirmation by remember { mutableStateOf(false) }

    val isEnrolled by viewModel.isUserEnrolled(authState.currentUserId!!, courseId)
        .collectAsState(initial = false)

    Column(modifier = Modifier.fillMaxSize()) {
        CourseDetailsAppBar(
            navigateBack = navigateBack,
            isBookmarked = isBookmarked,
            onBookmarkChange = {
                if (isBookmarked) {
                    showRemoveConfirmation = true
                } else {
                    authState.currentUserId?.let { studentId ->
                        viewModel.addBookmark(
                            studentId,
                            courseId
                        )
                    }
                }
            }
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .offset(y = (-30).dp)
                .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
        ) {
            CourseDetailsContent(
                scope = scope,
                pagerState = pagerState,
                selectedTabIndex = selectedTabIndex.value,
                courseWithTeacher = uiState.value.courseDetails,
                lessons = uiState.value.lessons,
                navigateToMentorDetails = navigateToMentorDetails,
                modifier = Modifier
                    .align(alignment = Alignment.TopCenter)
            )
        }
        EnrollmentBottomBar(
            isEnrolled = isEnrolled,
            courseCost = uiState.value.courseDetails.course.cost,
            onEnrollCourse = {
                showEnrollConfirmation = true
            }
        )
    }

    if (showEnrollConfirmation) {
        ModalBottomSheet(
            onDismissRequest = { showEnrollConfirmation = false },
            sheetState = sheetState
        ) {
            if (!isEnrolled) {
                ConfirmationNotification(
                    title = "Bạn xác nhận muốn đăng ký?",
                    course = uiState.value.courseDetails,
                    onConfirm = {
                        viewModel.enrollCourse(
                            authState.currentUserId!!,
                            courseId
                        )
                    },
                    onCancel = {
                        showRemoveConfirmation = false
                    }
                )
            } else {
                ConfirmationNotification(
                    title = "Bạn xác nhận muốn hủy đăng ký?",
                    course = uiState.value.courseDetails,
                    onConfirm = {
                        viewModel.removeEnrollment(
                            authState.currentUserId!!,
                            courseId
                        )
                    },
                    onCancel = {
                        showRemoveConfirmation = false
                    }
                )
            }
        }
    }

    LaunchedEffect(isEnrolled) {
        if (showEnrollConfirmation) {
            showEnrollConfirmation = false
        }
    }

    if (showRemoveConfirmation) {
        ModalBottomSheet(
            onDismissRequest = { showRemoveConfirmation = false },
            sheetState = sheetState
        ) {
            ConfirmationNotification(
                title = "Bạn chắc chắn muốn xóa khỏi Bookmark?",
                course = uiState.value.courseDetails,
                onConfirm = {
                    viewModel.removeBookmark(authState.currentUserId!!, courseId)
                    showRemoveConfirmation = false
                },
                onCancel = {
                    showRemoveConfirmation = false
                }
            )
        }
    }
}

@Composable
private fun CourseDetailsAppBar(
    navigateBack : () -> Unit,
    onBookmarkChange : () -> Unit,
    isBookmarked : Boolean
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
            icon = if (isBookmarked) R.drawable.bookmark_svgrepo_com else R.drawable.bookmark_svgrepo_com_outline,
            tint = Color.Black,
            contentDescription = "Bookmark",
            navigationOnClick = navigateBack,
            actionOnClick = onBookmarkChange,
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
    courseWithTeacher: CourseWithTeacher,
    lessons : List<Lesson>,
    scope : CoroutineScope,
    pagerState : PagerState,
    selectedTabIndex : Int,
    navigateToMentorDetails: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(
                horizontal = 20.dp,
                vertical = 30.dp
            ),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        item {
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
                text = courseWithTeacher.course.title,
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
                    Text(text = courseWithTeacher.teacher.name, color = Color.Gray, fontSize = 14.sp)
                }
                Row {
                    Icon(Icons.Default.PlayArrow, contentDescription = null, tint = Color.Gray)
                    Text(text = "${lessons.size} Lessons", color = Color.Gray, fontSize = 14.sp)
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
            HorizontalPager(
                state = pagerState,
                modifier = Modifier
                    .fillMaxWidth()
            ) { page ->
                when (page) {
                    0 -> AboutTab(
                        courseWithTeacher = courseWithTeacher,
                        navigateToMentorDetails = navigateToMentorDetails
                    )
                    1 -> LessonTab(
                        lessons = lessons
                    )
                }

            }

        }

    }
}

@Composable
private fun AboutTab(
    courseWithTeacher: CourseWithTeacher,
    navigateToMentorDetails: (String) -> Unit
) {
    val fullText = courseWithTeacher.course.description
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
                text = courseWithTeacher.course.description,
//                            text = buildAnnotatedString {
//                                append(fullText)
//                                pushStringAnnotation(tag = "read_more", annotation = "read_more")
//                                withStyle(style = SpanStyle(color = Color(0xFF0961F5))) {
//                                    append(readMoreText)
//                                }
//                                pop()
//                            },
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
                color = Color.Gray,
            )
        }

        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                "Mentor",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
            )
            MentorInfo(
                navigateToMentorDetails = navigateToMentorDetails,
                courseWithTeacher = courseWithTeacher
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
}

@Composable
private fun LessonTab(
    lessons: List<Lesson>
) {
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
                "Lesson (${lessons.size})",
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Medium,
            )

        }
        LessonList(
            lessons = lessons
        )

    }
}

@Composable
private fun MentorInfo(
    navigateToMentorDetails : (String) -> Unit,
    courseWithTeacher: CourseWithTeacher,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = 12.dp,
            ),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            MentorImage(
                mentorImage = courseWithTeacher.teacher.avatarUrl,
                modifier = Modifier
                    .size(65.dp)
                    .clickable {
                        navigateToMentorDetails(courseWithTeacher.teacher.teacherId)
                    },
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    courseWithTeacher.teacher.name,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.W500,
                )
                Text(
                    courseWithTeacher.teacher.specialization,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W400,
                    color = Color.Black.copy(
                        alpha = 0.3f
                    )
                )
            }
        }
        Row(
            modifier = Modifier.align(Alignment.CenterVertically),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            InteractionIcon(
                icon = R.drawable.call_svgrepo_com,
                onClick = {

                },
                modifier = Modifier
                    .background(
                        color = Color(0xFFF4F6F9),
                        shape = RoundedCornerShape(100.dp)
                    )
                    .size(45.dp)
                    .weight(1f),
            )
            InteractionIcon(
                icon = R.drawable.message_text_1_svgrepo_com,
                onClick = {

                },
                modifier = Modifier
                    .background(
                        color = Color(0xFFF4F6F9),
                        shape = RoundedCornerShape(100.dp)
                    )
                    .size(45.dp)
                    .weight(1f),
            )
        }
    }
}

@SuppressLint("DefaultLocale")
@Composable
private fun LessonList(
    lessons: List<Lesson>
) {
    for ((index, lesson) in lessons.withIndex()) {
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
                        text = String.format("%02d", index + 1),
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
                        lesson.title,
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
//    Row(
//        modifier = Modifier.fillMaxWidth(),
//        horizontalArrangement = Arrangement.SpaceBetween
//    ) {
//        Text(
//            "Section 1 - Introduction",
//            style = MaterialTheme.typography.bodyLarge,
//            fontWeight = FontWeight.Medium,
//            color = Color.Gray
//        )
//        Text(
//            "15 Min",
//            style = MaterialTheme.typography.bodyLarge,
//            fontWeight = FontWeight.Medium,
//            color = Color(0xFF0961F5)
//        )
//    }

}

@Composable
private fun EnrollmentBottomBar(
    isEnrolled : Boolean,
    courseCost: Double,
    onEnrollCourse : () -> Unit
) {
    Row(
        modifier = Modifier
            .background(Color.White)
            .fillMaxWidth()
            .padding(
                horizontal = 16.dp,
                vertical = 8.dp
            )
        ,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Text(
                "Total Price",
                style = MaterialTheme.typography.titleSmall,
                fontSize = 14.sp,
                color = Color.Gray
            )
            Text(
                "$$courseCost",
                style = MaterialTheme.typography.titleMedium,
                fontSize = 16.sp,
                fontWeight = FontWeight.W700,
                color = Color(0xFF0961F5)
            )
        }

        TextButton(
            modifier = Modifier
                .background(
                    color = if(!isEnrolled) Color(0xFF0961F5) else Color.Black.copy(
                        alpha = 0.3f
                    ),
                    shape = RoundedCornerShape(50.dp)
                ),
            onClick = onEnrollCourse
        ) {
            Text(
                if(!isEnrolled) "Đăng ký ngay" else "Hủy đăng ký",
                style = MaterialTheme.typography.titleMedium,
                color = if(!isEnrolled) Color.White else Color.Black,
                fontSize = 16.sp,
                modifier = Modifier.padding(
                    horizontal = 40.dp
                )
            )
        }
    }
}