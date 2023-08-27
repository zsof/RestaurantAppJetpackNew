package hu.zsof.restaurantappjetpacknew.ui.common.field

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
        RoundedCornerShape(20.dp)

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        shape = shape,
        modifier = modifier
            .fillMaxWidth().padding(vertical = 4.dp, horizontal = 16.dp),
        singleLine = true,
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
