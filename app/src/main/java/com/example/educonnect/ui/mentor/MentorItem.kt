package com.example.educonnect.ui.mentor

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.educonnect.R

@Composable
fun MentorItem(
    navigateToMentorDetails : () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            MentorImage(
                modifier = Modifier
                    .size(65.dp)
                    .clickable {
                        navigateToMentorDetails()
                    },
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    "Wade Warren",
                    fontSize = 19.sp,
                    fontWeight = FontWeight.W500,
                )
                Text(
                    "Design Expert",
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
            horizontalArrangement = Arrangement.spacedBy(14.dp),
        ) {
            InteractionIcon(
                modifier = Modifier
                    .background(
                        color = Color(0xFFF4F6F9),
                        shape = RoundedCornerShape(100.dp)
                    )
                    .size(45.dp),
            )
            InteractionIcon(
                modifier = Modifier
                    .background(
                        color = Color(0xFFF4F6F9),
                        shape = RoundedCornerShape(100.dp)
                    )
                    .size(45.dp),
            )
        }
    }
}

@Composable
internal fun MentorImage(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.lecturer),
            contentDescription = "Top Mentor",
            contentScale = ContentScale.Crop,
            modifier = Modifier.clip(
                shape = RoundedCornerShape(100.dp)
            )
        )
    }
}


@Composable
private fun InteractionIcon(
    modifier: Modifier = Modifier
) {
    IconButton(
        modifier = modifier,
        onClick = { /*TODO*/ }
    ) {
        Icon(
            painterResource(R.drawable.laptop_code_svgrepo_com),
            modifier = Modifier.size(25.dp),
            tint = Color(0xFF0961F5),
            contentDescription = "Notification",
        )
    }
}
