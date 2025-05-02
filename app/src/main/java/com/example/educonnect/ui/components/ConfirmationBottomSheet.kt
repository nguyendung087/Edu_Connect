package com.example.educonnect.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.educonnect.data.model.courses.CourseWithTeacher
import com.example.educonnect.ui.courses.CourseViewModel

@Composable
fun ConfirmationNotification(
    title : String,
    course: CourseWithTeacher,
    onConfirm: () -> Unit,
    onCancel: () -> Unit
) {
    Column(
        modifier = Modifier
            .padding(
                start = 16.dp,
                end = 16.dp,
                bottom = 24.dp
            )
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            title,
            style = MaterialTheme.typography.titleMedium.copy(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            ),
            color = Color.Black
        )

        HorizontalDivider(
            modifier = Modifier
                .padding(top = 20.dp)

        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(
                vertical = 24.dp
            )
        ) {
            Image(
                painter = painterResource(course.course.courseImage),
                contentDescription = course.course.title,
                modifier = Modifier
                    .size(120.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Column(
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = course.course.title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 15.sp,
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = Color.Black
                )
                Text(
                    text = "Giáo viên: ${course.teacher.name}",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 14.sp
                    ),
                    color = Color.Gray
                )
                Text(
                    text = "Học phí: \$${course.course.cost}",
                    style = MaterialTheme.typography.titleMedium.copy(
                        fontSize = 14.sp
                    ),
                    color = Color.Gray
                )
            }
        }

        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Button(
                onClick = onCancel,
                colors = ButtonDefaults.buttonColors(
                    disabledContainerColor = Color(0xFFF4F6F9),
                    disabledContentColor = Color(0xFF0961F5),
                    containerColor = Color(0xFFF4F6F9),
                    contentColor = Color(0xFF0961F5)
                ),
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier
                    .height(60.dp)
                    .weight(1f)
            ) {
                Text("Hủy")
            }
            Button(
                onClick = onConfirm,
                colors = ButtonDefaults.buttonColors(
                    disabledContainerColor = Color(0xFF0961F5),
                    disabledContentColor = Color.White,
                    containerColor = Color(0xFF0961F5),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(50.dp),
                modifier = Modifier
                    .height(60.dp)
                    .weight(1f)
            ) {
                Text("Xác nhận")
            }
        }
    }
}