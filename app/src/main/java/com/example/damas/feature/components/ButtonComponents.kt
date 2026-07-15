package com.example.damas.feature.components

import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.damas.ui.theme.PurpleDetails

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