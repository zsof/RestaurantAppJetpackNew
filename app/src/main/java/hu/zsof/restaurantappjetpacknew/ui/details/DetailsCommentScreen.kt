package hu.zsof.restaurantappjetpacknew.ui.details

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import hu.zsof.restaurantappjetpacknew.R
import hu.zsof.restaurantappjetpacknew.module.AppState
import hu.zsof.restaurantappjetpacknew.ui.common.field.SearchTextField

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class,
)
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
                    .padding(bottom = 56.dp)
                    .fillMaxSize(),
            ) {
                Row {
                    SearchTextField(
                        value = viewModel.newComment.value,
                        label = stringResource(id = R.string.comment_label),
                        onValueChange = { newValue ->
                            viewModel.newComment.value = newValue
                        },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done,
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(5f)
                            .padding(8.dp),
                        leadingIcon = null,
                        placeholder = stringResource(id = R.string.comment_label),
                        keyboardActions = KeyboardActions(onSearch = { keyboardController?.hide() })
                    )
                    IconButton(
                        onClick = {
                            viewModel.addComment(placeId)
                            keyboardController?.hide()
                            viewModel.newComment.value = ""
                        },
                        modifier = Modifier
                            .padding(8.dp)
                            .weight(1f)
                            .align(Alignment.CenterVertically),
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Send,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(36.dp),
                        )
                    }
                }
                LazyColumn(
                    contentPadding = PaddingValues(8.dp),
                    modifier = Modifier.weight(1f)
                ) {
                    items(comments.value) {
                        MessageBoxItem(
                            message = it.message,
                            creatorId = it.userId,
                            creatorName = it.userName
                        )
                    }
                }
            }
        },
    )
}

@Composable
fun MessageBoxItem(message: String, creatorId: Long, creatorName: String) {
    val loggedUser = AppState.loggedUser
    val ownComment = loggedUser.value?.id == creatorId
    Column(modifier = Modifier.fillMaxSize()) {
        Box(
            modifier = Modifier
                .align(if (ownComment) Alignment.End else Alignment.Start)
                .clip(
                    RoundedCornerShape(
                        topStart = 48f,
                        topEnd = 48f,
                        bottomStart = if (ownComment) 48f else 0f,
                        bottomEnd = if (ownComment) 0f else 48f
                    )
                )
                .background(MaterialTheme.colorScheme.primary)
                .padding(16.dp)
        ) {
            Column {
                Text(text = message, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(
                    text = creatorName,
                    color = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier.align(Alignment.End),
                    fontSize = 12.sp
                )
            }
        }
        Spacer(modifier = Modifier.padding(top = 16.dp))
    }
}
