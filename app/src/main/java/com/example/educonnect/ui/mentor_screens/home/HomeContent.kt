package com.example.educonnect.ui.mentor_screens.home

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.educonnect.data.model.courses.CourseWithTeacher
import com.example.educonnect.data.model.courses.EnrollmentWithCourseAndTeacher
import com.example.educonnect.data.model.users.TeacherProfile
import com.example.educonnect.ui.students_screens.courses.LinearIndicator
import com.example.educonnect.ui.theme.EduConnectTheme

@Composable
internal fun MentorBasicTitle(
    title : String,
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
    }
}

@Composable
internal fun ClassesList(
    modifier: Modifier = Modifier,
    courseWithTeacher : CourseWithTeacher,
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.weight(1f)
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
            }
            Text(
                courseWithTeacher.course.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.W700,
            )

        }
    }
}


@Composable
internal fun CustomYetCreated(
    @DrawableRes icon : Int,
    announceText : String,
    buttonText: String
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Icon(
            painter = painterResource(icon),
            contentDescription = "Classes",
            tint = Color(0xFF0961F5),
            modifier = Modifier.size(50.dp)
        )
        Text(
            announceText,
            style = MaterialTheme.typography.titleMedium,
            fontSize = 16.sp,
            fontWeight = FontWeight.W300,
            color = Color.Gray
        )
        CreateButton(
            buttonText = buttonText
        )
    }
}

@Composable
internal fun CreateButton(
    modifier: Modifier = Modifier,
    buttonText : String,
) {
    OutlinedButton(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        border = BorderStroke(
            color = Color(0xFF0961F5),
            width = 1.dp
        ),
        onClick = { /*TODO*/ }
    ) {
        Text(
            buttonText,
            style = MaterialTheme.typography.titleMedium,
            color = Color.Black,
            fontSize = 16.sp,
            modifier = Modifier.padding(
                vertical = 5.dp
            )
        )
    }
}

@Preview
@Composable
private fun MentorPreview() {
    EduConnectTheme {
    }
}