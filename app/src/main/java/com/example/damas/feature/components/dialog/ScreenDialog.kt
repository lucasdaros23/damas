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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.Dialog
import com.example.damas.domain.model.DialogModel
import com.example.damas.feature.components.GenericTextButton
import com.example.damas.resources.Size
import com.example.damas.ui.theme.PurpleDetails

@Composable
fun ScreenDialog(
    dialog: DialogModel,
    onConfirm: () -> Unit,
    onCancel: () -> Unit,
) {
    Dialog(
        onDismissRequest = onCancel,
        content = {
            Surface(
                shape = RoundedCornerShape(Size.md1),
                color = MaterialTheme.colorScheme.background,
                border = BorderStroke(width = Size.xs1, color = PurpleDetails.copy(alpha = .3f))
            ) {
                Column(
                    Modifier
                        .padding(Size.sm3)
                ) {
                    with(dialog) {
                        title?.let {
                            Text(
                                text = title,
                                style = MaterialTheme.typography.titleLarge
                            )
                        }
                        message?.let {
                            Spacer(Modifier.size(Size.sm1))
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
                                GenericTextButton(
                                    onClick = onCancel,
                                    text = cancelText
                                )
                            }
                            confirmText?.let {
                                GenericTextButton(
                                    onClick = onConfirm,
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

@Preview
@Composable
private fun ScreenDialogPreview() {
    ScreenDialog(
        DialogModel(),
        {},
        {}
    )
}