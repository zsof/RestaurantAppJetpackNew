package hu.zsof.restaurantappjetpacknew.ui.common.field

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@ExperimentalMaterial3Api
@Composable
fun SearchTextField(
    value: String,
    label: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String,
    leadingIcon: ImageVector? = Icons.Default.Search,
    keyboardActions: KeyboardActions,
    keyboardOptions: KeyboardOptions = KeyboardOptions(
        keyboardType = KeyboardType.Text,
        imeAction = ImeAction.Search,
    )
) {
    val shape =
        RoundedCornerShape(20.dp)

    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        shape = shape,
        prefix = {
            if (leadingIcon != null) {
                Icon(imageVector = leadingIcon, contentDescription = null)
            }
        },
        modifier = modifier,
        singleLine = true,
        placeholder = { Text(text = placeholder) },
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
    )
}
