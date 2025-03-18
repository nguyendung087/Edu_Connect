package com.example.educonnect.ui.bookmark

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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

@Composable
internal fun BookmarkList() {
    LazyColumn(
        modifier = Modifier
            .background(
                Color.White
            )
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        item {
            BookmarkItem()
        }
    }
}

@Composable
internal fun CourseTagList() {
    LazyRow(
        modifier = Modifier.padding(
            horizontal = 8.dp
        )
    ) {
        item {
            CategoryTag(
                text = "All",
                //                    isSelected = category == selectedCategory.value,
                //                    onClick = { selectedCategory.value = category }
                isSelected = true,
                onClick = {}
            )
            CategoryTag(
                text = "Design",
                //                    isSelected = category == selectedCategory.value,
                //                    onClick = { selectedCategory.value = category }
                isSelected = false,
                onClick = {}
            )
        }
    }
}

@Composable
private fun BookmarkItem() {
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
                    painter = painterResource(R.drawable.course),
                    contentDescription = "Course Image",
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
                    "Introduction of Figma",
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
                        "Robert Green",
                        fontWeight = FontWeight.W500,
                        color = Color.Gray
                    )
                }
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        "$180",
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

            BookmarkButton(
                modifier = Modifier
                    .weight(0.6f)
                    .size(30.dp)


            )
        }
    }
}

@Composable
private fun BookmarkButton(
    modifier: Modifier = Modifier
) {
    IconButton(
        modifier = modifier,
        onClick = { /*TODO*/ }
    ) {
        Icon(
            painter = painterResource(R.drawable.bookmark_svgrepo_com),
            tint = Color(0xFF0961F5),
            contentDescription = "BookmarkButton",
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
private fun CategoryTag(text: String, isSelected: Boolean, onClick: () -> Unit) {
    TextButton(
        onClick = onClick,
        shape = RoundedCornerShape(20.dp),
        border = BorderStroke(
            color = Color.Black.copy(
                alpha = 0.1f,
            ),
            width = 1.dp
        ),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isSelected) Color(0xFF0961F5) else Color.White ,
            contentColor = if (isSelected) Color.White else Color.Black,
        ),
        modifier = Modifier.padding(
            top = 4.dp,
            bottom = 4.dp,
            end = 8.dp
        )
    ) {
        Text(
            text = text,
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium,
        )
    }
}

@Composable
@Preview
private fun BookmarkPreview() {
    BookmarkList()
}