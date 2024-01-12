package com.tommannson.paymentorganizer.components.texfield

import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.tommannson.paymentorganizer.theme.CMTheme

@Composable
fun AppTextField(
    modifier: Modifier = Modifier,
    value: String,
    label: String,
    minLines: Int = 1,
    enabled: Boolean = true,
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        enabled = enabled,
        minLines = minLines,
        label = {
            Text(text = label)
        },
        onValueChange = onValueChange
    )
}

@Preview
@Composable
private fun AppTextFieldPreview() {
    CMTheme {
        AppTextField(
            label = "Label",
            value = "Value",
            onValueChange = {})
    }
}