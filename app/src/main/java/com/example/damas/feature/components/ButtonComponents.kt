package com.example.damas.feature.components

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.damas.R
import com.example.damas.ui.theme.PurpleDetails
import com.example.damas.ui.theme.SquareWhite

@Composable
fun GenericButton(text: String, onClick: () -> Unit) {
    Button(
        modifier = Modifier
            .width(360.dp),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = PurpleDetails,
            contentColor = Color.White
        ),
        onClick = onClick
    ) {
        Text(text, fontWeight = FontWeight.Bold, fontSize = 30.sp)
    }
}

@Composable
fun IconButtonComponent(
    painter: Painter,
    onClick: () -> Unit,
    size: Int,
    tint: Color
) {
    IconButton(onClick = onClick) {
        Icon(
            painter = painter,
            contentDescription = "",
            modifier = Modifier.size(size.dp),
            tint = tint
        )
    }
}

@Composable
fun ReturnButton(onClick: () -> Unit) {
    IconButtonComponent(
        painter = painterResource(R.drawable.back_icon),
        onClick = onClick,
        size = 40,
        tint = SquareWhite
    )
}