package com.example.damas.feature.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import com.example.damas.R
import com.example.damas.resources.FontSize
import com.example.damas.resources.Size
import com.example.damas.ui.theme.PurpleDetails
import com.example.damas.ui.theme.SquareWhite

@Composable
fun GenericButton(
    text: String,
    onClick: () -> Unit,
    isMain: Boolean = true,
    fontSize: TextUnit = FontSize.lg2,
) {
    Button(
        modifier = Modifier
            .width(Size.xxl2)
            .border(width = Size.xs3, shape = RoundedCornerShape(Size.sm1), color = if (isMain) Color.Transparent else PurpleDetails),
        shape = RoundedCornerShape(Size.sm1),
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isMain) PurpleDetails else SquareWhite,
            contentColor = if (isMain) Color.White else PurpleDetails,
        ),
        onClick = onClick
    ) {
        Text(text, fontWeight = FontWeight.Bold, fontSize = fontSize)
    }
}

@Composable
fun GenericTextButton(
    onClick: () -> Unit,
    color: Color = PurpleDetails,
    text: String
) {
    TextButton(
        onClick = onClick,
    ) {
        Text(
            text,
            style = MaterialTheme.typography.titleMedium,
            color = color
        )
    }
}

@Composable
fun IconButtonComponent(
    painter: Painter,
    onClick: () -> Unit,
    size: Dp,
    tint: Color
) {
    IconButton(onClick = onClick) {
        Icon(
            painter = painter,
            contentDescription = "",
            modifier = Modifier.size(size),
            tint = tint
        )
    }
}

@Composable
fun ReturnButton(onClick: () -> Unit) {
    IconButtonComponent(
        painter = painterResource(id = R.drawable.back_icon),
        onClick = onClick,
        size = Size.md3,
        tint = SquareWhite
    )
}