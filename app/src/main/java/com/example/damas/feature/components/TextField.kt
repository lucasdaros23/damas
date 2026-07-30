package com.example.damas.feature.components

import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import com.example.damas.resources.Size

@Composable
fun DefaultTextField(
    value: String,
    onValueChanged: (String) -> Unit,
    placeholder: String,
    type: TextFieldType = TextFieldType.DEFAULT,
    leadingIcon: @Composable (() -> Unit) = {},
    trailingIcon: @Composable (() -> Unit) = {},
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChanged,
        modifier = Modifier
            .width(Size.xxl2),
        label = {
            Text(placeholder)
        },
        shape = RoundedCornerShape(Size.sm2),
        visualTransformation = when (type) {
            TextFieldType.PASSWORD -> PasswordVisualTransformation()
            else -> VisualTransformation.None
        },
        trailingIcon = trailingIcon,
    )
}

@Composable
fun PasswordTextField(
    value: String,
    onValueChanged: (String) -> Unit,
    placeholder: String,
) {
    DefaultTextField(
        value = value,
        onValueChanged = onValueChanged,
        placeholder = placeholder,
        type = TextFieldType.PASSWORD
    )
}

@Preview(showBackground = true)
@Composable
private fun EmptyDefaultTextFieldPreview() {
    DefaultTextField(
        "",
        {},
        "texto"
    )
}

@Preview(showBackground = true)
@Composable
private fun FilledDefaultTextFieldPreview() {
    DefaultTextField(
        "blablablabla",
        {},
        "texto"
    )
}

@Preview(showBackground = true)
@Composable
private fun PasswordDefaultTextFieldPreview() {
    DefaultTextField(
        "blablablabla",
        {},
        "texto",
        type = TextFieldType.PASSWORD
    )
}

enum class TextFieldType{
    DEFAULT,
    PASSWORD,
    EMAIL,
}