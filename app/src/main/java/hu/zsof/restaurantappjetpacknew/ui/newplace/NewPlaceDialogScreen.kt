package hu.zsof.restaurantappjetpacknew.ui.newplace

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import hu.zsof.restaurantappjetpacknew.R

@Composable
fun NewPlaceDialogScreen(dialogOpen: () -> Unit) {
    var dialogOpen by remember {
        mutableStateOf(false)
    }

    if (dialogOpen) {
        androidx.compose.material.AlertDialog(
            onDismissRequest = { dialogOpen = false },
            confirmButton = {
                TextButton(onClick = { dialogOpen = false }) {
                    Text(text = stringResource(id = R.string.save_btn))
                }
            },
            dismissButton = {
                TextButton(onClick = { dialogOpen = false }) {
                    Text(text = stringResource(id = R.string.cancel_btn))
                }
            },
            title = { Text(text = stringResource(id = R.string.add_new_place_title)) },
            text = { Text(text = "Desc") },
            modifier = Modifier.fillMaxWidth().padding(32.dp),
            shape = RoundedCornerShape(5.dp),

        )
    }
}
