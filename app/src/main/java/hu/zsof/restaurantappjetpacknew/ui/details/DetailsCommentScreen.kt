package hu.zsof.restaurantappjetpacknew.ui.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import hu.zsof.restaurantappjetpacknew.R
import hu.zsof.restaurantappjetpacknew.ui.common.field.SearchTextField

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun DetailsCommentScreen(
    viewModel: DetailsViewModel = hiltViewModel(),
    placeId: Long,
) {
    val comments = viewModel.comments.observeAsState(listOf())
    LaunchedEffect(key1 = "DetailsComment") {
        viewModel.getCommentsByPlaceId(placeId)
    }

    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 44.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                LazyColumn(
                    contentPadding = PaddingValues(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    items(comments.value) {
                        ChatBoxItem(
                            message = it.message
                        )
                    }
                }
                Row(verticalAlignment = Alignment.Bottom) {
                    SearchTextField(
                        value = viewModel.newComment.value,
                        label = stringResource(id = R.string.comment_label),
                        onValueChange = { newValue ->
                            viewModel.newComment.value = newValue
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Send,
                        ),
                        leadingIcon = null,
                        placeholder = stringResource(id = R.string.comment_label),
                        keyboardActions = KeyboardActions(onSearch = { keyboardController?.hide() })
                    )
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Filled.Send,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.surfaceVariant
                        )
                    }
                }
            }
        },
    )
}

@Composable
fun ChatBoxItem(message: String) {
    Box(
        modifier = Modifier
            .clip(
                RoundedCornerShape(
                    topStart = 48f,
                    topEnd = 48f,
                    bottomStart = 0f,
                    bottomEnd = 48f
                )
            )
            .background(MaterialTheme.colorScheme.surfaceVariant)
            .padding(16.dp)
    ) {
        Text(text = message)
    }
}
