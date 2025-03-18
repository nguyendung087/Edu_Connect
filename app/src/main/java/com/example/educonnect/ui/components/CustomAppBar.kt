package com.example.educonnect.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.educonnect.R

@Composable
fun CustomAppBar(
    title : String,
    hasActions : Boolean,
    modifier: Modifier = Modifier,
    @DrawableRes icon : Int = 0,
    contentDescription : String = "",
    navigationOnClick : () -> Unit = {},
    actionOnClick : () -> Unit = {},
    tint : Color = Color.Black,
    textColor : Color = Color.Black
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        NavigationButton(
            icon = R.drawable.arrow_left_svgrepo_com,
            onClick = navigationOnClick,
            tint = tint,
            modifier = Modifier
                .background(
                    color = Color.White,
                    shape = RoundedCornerShape(100.dp)
                )

                .size(45.dp)
                .border(
                    width = 1.dp,
                    color = Color.Black.copy(
                        alpha = 0.2f
                    ),
                    shape = RoundedCornerShape(100.dp),
                )
        )
        Text(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.W500,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center,
            color = textColor
        )
        if (hasActions) {
            ActionButton(
                icon = icon,
                contentDescription = contentDescription,
                onClick = actionOnClick,
                tint = tint,
                modifier = Modifier
                    .background(
                        color = Color.White,
                        shape = RoundedCornerShape(100.dp)
                    )

                    .size(45.dp)
                    .border(
                        width = 1.dp,
                        color = Color.Black.copy(
                            alpha = 0.2f
                        ),
                        shape = RoundedCornerShape(100.dp),
                    )
            )
        } else {
            Spacer(modifier = Modifier.size(45.dp))
        }
    }

}

@Composable
private fun NavigationButton(
    @DrawableRes icon : Int,
    onClick : () -> Unit,
    tint: Color,
    modifier: Modifier = Modifier
) {
    CustomIconButton(
        icon = icon,
        contentDescription = "Navigation back",
        onClick = onClick,
        tint = tint,
        modifier = modifier
    )
}

@Composable
private fun ActionButton(
    @DrawableRes icon : Int,
    contentDescription : String,
    onClick : () -> Unit,
    tint : Color,
    modifier: Modifier = Modifier
) {
    CustomIconButton(
        icon = icon,
        contentDescription = contentDescription,
        onClick = onClick,
        tint = tint,
        modifier = modifier
    )
}

@Composable
@Preview
private fun CustomAppBarPreview() {
    CustomAppBar(
        title = "My Course",
        hasActions = false,
//        tint = Color.Black,
//        icon = R.drawable.bookmark_svgrepo_com_dark
    )
}