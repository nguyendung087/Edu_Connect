package com.example.educonnect.ui.students_screens.home

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.educonnect.R
import com.example.educonnect.data.model.courses.Course
import com.example.educonnect.data.model.courses.CourseWithTeacher
import com.example.educonnect.data.model.users.TeacherProfile

@Composable
internal fun BasicTitle(
    title : String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            title,
            style = MaterialTheme.typography.bodyLarge,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            "See all",
            style = MaterialTheme.typography.bodyLarge,
            color = Color(0xFFFFA800),
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold,
            modifier = modifier
        )
    }

}

@Composable
internal fun CategoryButton(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        IconButton(
            onClick = { /*TODO*/ }
        ) {
            Icon(
                painterResource(R.drawable.laptop_code_svgrepo_com),
                modifier = Modifier.size(50.dp),
                tint = Color(0xFF0961F5),
                contentDescription = "Notification",
            )
        }
    }
}


@Composable
internal fun CourseList(
    modifier: Modifier = Modifier,
    courseWithTeacher : CourseWithTeacher,
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                modifier = Modifier
                    .width(240.dp)
            ) {
                Image(
                    painter = painterResource(courseWithTeacher.course.courseImage),
                    contentDescription = courseWithTeacher.course.title,
                    modifier = Modifier
                        .clip(
                            shape = RoundedCornerShape(15.dp)
                        )
                        .fillMaxWidth()
                )
                Row(
                    modifier = Modifier
                        .padding(12.dp)
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(5.dp)
                        )
                        .padding(
                            vertical = 2.dp,
                            horizontal = 5.dp
                        )
                        .align(Alignment.TopStart),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        Icons.Filled.Star,
                        contentDescription = "Ratings",
                        tint = Color(0xFFFFA800),
                    )
                    Text(
                        "4.8",
                        fontWeight = FontWeight.W500,
                        fontSize = 16.sp
                    )
                }
//                IconButton(
//                    modifier = Modifier
//                        .padding(12.dp)
//                        .background(
//                            color = Color.White,
//                            shape = RoundedCornerShape(5.dp)
//                        )
//                        .size(30.dp)
//                        .align(Alignment.TopEnd),
//                    onClick = {
//
//                    }
//                ) {
//                    Icon(
//                        painterResource(R.drawable.bookmark_svgrepo_com),
//                        contentDescription = "Bookmark",
//                        tint = Color(0xFF0961F5)
//                    )
//                }
            }
            Text(
                courseWithTeacher.course.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.W700,
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
                    courseWithTeacher.teacher.name,
                    fontWeight = FontWeight.W500,
                    color = Color.Gray
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    "${courseWithTeacher.course.cost}",
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
    }
}


@Composable
internal fun MentorButton(
    modifier: Modifier = Modifier,
    mentor : TeacherProfile
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(mentor.avatarUrl),
            contentDescription = "Top Mentor",
            contentScale = ContentScale.Crop,
            modifier = Modifier.clip(
                shape = RoundedCornerShape(100.dp)
            )
        )
    }
}