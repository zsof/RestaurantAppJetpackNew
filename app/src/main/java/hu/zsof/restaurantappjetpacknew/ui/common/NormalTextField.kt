package hu.zsof.restaurantappjetpacknew.ui.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@ExperimentalMaterial3Api
@Composable
fun NormalTextField(
    value: String,
    label: String,
    onValueChange: (String) -> Unit,
    leadingIcon: @Composable (() -> Unit)?,
    trailingIcon: @Composable (() -> Unit)?,
    modifier: Modifier = Modifier,
    isError: Boolean = false,
    placeholder: String,
    keyboardOptions: KeyboardOptions,
    onDone: (KeyboardActionScope.() -> Unit)?,
) {
    val shape =
        RoundedCornerShape(20.dp)

    OutlinedTextField(
        value = value.trim(),
        onValueChange = onValueChange,
        label = { Text(text = label) },
        shape = shape,
        leadingIcon = leadingIcon,
        trailingIcon = if (isError) {
            {
                Icon(imageVector = Icons.Default.ErrorOutline, contentDescription = null)
            }
        } else {
            {
                if (trailingIcon != null) {
                    trailingIcon()
                }
            }
        },
        modifier = modifier
            .fillMaxWidth().padding(vertical = 4.dp),
        singleLine = true,
        isError = isError,
        textStyle = TextStyle(color = Color.Black),
        placeholder = { Text(text = placeholder) },
        keyboardOptions = keyboardOptions,
        keyboardActions = KeyboardActions(
            onDone = onDone,
        ),
    )
}

@ExperimentalMaterial3Api
@Preview
@Composable
fun NormalTextView_Error_Preview() {
    NormalTextField(
        value = "abc",
        label = "Mennyiség (kg)",
        onValueChange = {},
        leadingIcon = {},
        trailingIcon = {},
        onDone = {},
        keyboardOptions = KeyboardOptions(),
        isError = true,
        placeholder = "alma",
    )
}
