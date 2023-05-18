package hu.zsof.restaurantappjetpacknew.ui.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActionScope
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ErrorOutline
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.material.icons.rounded.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@ExperimentalMaterial3Api
@Composable
fun PasswordTextField(
    value: String,
    label: String,
    modifier: Modifier = Modifier,
    onValueChange: (String) -> Unit,
    isError: Boolean = false,
    onDone: (KeyboardActionScope.() -> Unit)?,
    isVisible: Boolean = true,
    onVisibilityChanged: () -> Unit,
) {
    val visibilityIcon = if (isVisible) {
        Icons.Rounded.VisibilityOff
    } else {
        Icons.Rounded.Visibility
    }

    val shape =
        RoundedCornerShape(20.dp)

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        shape = shape,
        label = { Text(text = label) },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = null,
            )
        },
        trailingIcon = if (isError) {
            {
                Icon(
                    imageVector = Icons.Default.ErrorOutline,
                    contentDescription = null,
                )
            }
        } else {
            {
                IconButton(onClick = onVisibilityChanged) {
                    Icon(imageVector = visibilityIcon, contentDescription = null)
                }
            }
        },
        modifier = modifier.fillMaxWidth().padding(vertical = 4.dp, horizontal = 16.dp),
        singleLine = true,
        isError = isError,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Password,
            imeAction = ImeAction.Done,
        ),
        keyboardActions = KeyboardActions(
            onDone = onDone,
        ),
        visualTransformation = if (isVisible) VisualTransformation.None else PasswordVisualTransformation(),
    )
}
