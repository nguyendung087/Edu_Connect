package com.example.educonnect.ui.mentor_screens.students

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberAsyncImagePainter
import com.example.educonnect.R
import com.example.educonnect.data.model.courses.EnrollmentWithStudent
import com.example.educonnect.ui.EduViewModelProvider
import com.example.educonnect.ui.navigation.NavigationDestination

object StudentManageDestination : NavigationDestination {
    override val route = "students"
    override val titleRes = R.string.student
}

@Composable
fun StudentsTabs(
    viewModel: StudentManageViewModel = viewModel(
        factory = EduViewModelProvider.Factory
    )
) {
    val uiState = viewModel.studentUiState.collectAsState()

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "${uiState.value.students.size} " + if (uiState.value.students.size > 1) "Students" else "Student",
                style = MaterialTheme.typography.titleMedium,
                fontSize = 16.sp
            )
            TextButton(
                onClick = {

                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.sort_svgrepo_com),
                    contentDescription = "Sort Students",
                    tint = Color(0xFF0961F5),
                    modifier = Modifier.size(20.dp)
                )
                Text(
                    " Sort & Filter",
                    style = MaterialTheme.typography.titleMedium,
                    color = Color(0xFF0961F5),
                    fontSize = 16.sp
                )
            }
        }

        LazyColumn(
            modifier = Modifier.fillMaxWidth()
        ) {
            items(
                items = uiState.value.students,
                key = { it.student.studentId },
            ) { student ->
                StudentInfo(
                    enrollment = student
                )
            }
        }
    }
}

@Composable
private fun StudentInfo(
    enrollment : EnrollmentWithStudent
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                vertical = 5.dp
            )
            .clickable {

            },
        border = BorderStroke(
            color = Color.Black.copy(
                alpha = 0.5f
            ),
            width = 1.dp,
        ),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Image(
                painter = rememberAsyncImagePainter(
                    model = enrollment.student.avatarUrl.ifBlank { R.drawable.person_crop_circle_fill_svgrepo_com }
                ),
                contentDescription = enrollment.student.name,
                modifier = Modifier.weight(0.5f)
            )
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    enrollment.student.name,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W500,
                )
                Text(
                    enrollment.student.studentId,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W400,
                    color = Color.Black.copy(
                        alpha = 0.3f
                    )
                )
            }
        }
    }
}