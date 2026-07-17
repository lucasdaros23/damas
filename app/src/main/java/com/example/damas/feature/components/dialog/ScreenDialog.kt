package com.example.damas.feature.components.dialog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.damas.domain.model.Dialog
import com.example.damas.ui.theme.PurpleDetails

@Composable
fun ScreenDialog(dialog: Dialog) {
    Dialog(
        onDismissRequest = dialog.onCancel,
        content = {
            Surface(
                shape = RoundedCornerShape(20.dp),
                color = MaterialTheme.colorScheme.background,
                border = BorderStroke(width = 1.dp, color = PurpleDetails.copy(alpha = .3f))
            ) {
                Column(
                    Modifier
                        .padding(20.dp)
                ) {
                    with(dialog) {
                        title?.let {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.titleLarge
                            )
                        }
                        message?.let {
                            Spacer(Modifier.size(10.dp))
                            Text(
                                text = message,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                        Row(
                            horizontalArrangement = Arrangement.End,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            cancelText?.let {
                                AlertDialogTextButton(
                                    onClick = dialog.onCancel,
                                    color = PurpleDetails,
                                    text = cancelText
                                )
                            }
                            confirmText?.let {
                                AlertDialogTextButton(
                                    onClick = onConfirm,
                                    color = PurpleDetails,
                                    text = confirmText
                                )
                            }
                        }

                    }
                }
            }
        }
    )
}

@Composable
private fun AlertDialogTextButton(
    onClick: () -> Unit,
    color: Color,
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

@Preview
@Composable
private fun ScreenDialogPreview() {
    ScreenDialog(
        Dialog(),
    )
}