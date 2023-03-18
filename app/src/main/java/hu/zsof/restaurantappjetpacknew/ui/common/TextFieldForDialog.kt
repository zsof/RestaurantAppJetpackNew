package hu.zsof.restaurantappjetpacknew.ui.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
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
fun TextFieldForDialog(
    value: String,
    label: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String,
    keyboardOptions: KeyboardOptions,
    onDone: (KeyboardActionScope.() -> Unit)?,
) {
    val shape =
        RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp, bottomStart = 20.dp, bottomEnd = 20.dp)

    OutlinedTextField(
        value = value.trim(),
        onValueChange = onValueChange,
        label = { Text(text = label) },
        shape = shape,
        modifier = modifier
            .fillMaxWidth().padding(vertical = 4.dp),
        singleLine = true,
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
fun TextFieldForDialog_Preview() {
    TextFieldForDialog(
        value = "abc",
        label = "Mennyiség (kg)",
        onValueChange = {},
        onDone = {},
        keyboardOptions = KeyboardOptions(),
        placeholder = "alma",
    )
}
