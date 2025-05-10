package com.example.educonnect.ui.students_screens.courses

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.educonnect.R
import com.example.educonnect.data.model.courses.Course
import com.example.educonnect.data.model.courses.CourseWithTeacher
import com.example.educonnect.data.model.courses.EnrollmentWithCourseAndTeacher

@Composable
internal fun CourseItem(
    navigateToCourseDetails : (String) -> Unit,
    enrollment : EnrollmentWithCourseAndTeacher
) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
        border = CardDefaults.outlinedCardBorder(),
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable {
                navigateToCourseDetails(enrollment.course.courseId)
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .height(130.dp)
                    .weight(2f)
            ) {
                Image(
                    painter = painterResource(enrollment.course.courseImage),
                    contentDescription = "Course Image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.clip(
                        shape = RoundedCornerShape(10.dp)
                    )
                )
            }
            Column(
                modifier = Modifier.weight(3f),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
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
                Text(
                    enrollment.course.title,
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
                        enrollment.teacher.name,
                        fontWeight = FontWeight.W500,
                        color = Color.Gray
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    LinearIndicator(
                        modifier = Modifier.weight(3f)
                    )
                    Text(
                        "20/25",
                        color = Color.Gray,
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
fun LinearIndicator(
    modifier: Modifier = Modifier
) {
    var currentProgress by remember { mutableStateOf(20 / 25f) }
//    var loading by remember { mutableStateOf(false) }
//    val scope = rememberCoroutineScope()

    Column(
        modifier = modifier
    ) {
        LinearProgressIndicator(
            color = Color(0xFF0961F5),

            progress = {
                currentProgress
            },
            modifier = Modifier
                .height(8.dp)
                .clip(RoundedCornerShape(100.dp))
        )
    }
}