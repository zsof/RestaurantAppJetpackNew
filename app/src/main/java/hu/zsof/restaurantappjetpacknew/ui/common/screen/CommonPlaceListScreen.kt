package hu.zsof.restaurantappjetpacknew.ui.common.screen

import android.content.Context
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.filled.ReportProblem
import androidx.compose.material.icons.outlined.EditLocationAlt
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import hu.zsof.restaurantappjetpacknew.R
import hu.zsof.restaurantappjetpacknew.model.BasePlace
import hu.zsof.restaurantappjetpacknew.model.enums.Price
import hu.zsof.restaurantappjetpacknew.util.Constants
import hu.zsof.restaurantappjetpacknew.util.extension.imageUrl

@ExperimentalMaterial3Api
@Composable
fun PlaceListItem(
    place: BasePlace,
    onClickPlaceItem: (Long) -> Unit,
    favIdList: List<Long>? = emptyList(),
    needFavButton: Boolean = false,
    addOrRemoveFavIdList: (() -> Unit)? = null,
    showDeleteConfirmDialog: MutableState<Boolean>? = null,
    isFavPlace: MutableState<Boolean>? = null,
    isPlaceByOwner: Boolean = false,
    deletePlace: ((Long) -> Unit)? = null
) {
    val favouriteIcon = if (favIdList?.contains(place.id) == true) {
        Icons.Default.Favorite
    } else {
        Icons.Default.FavoriteBorder
    }

    val context = LocalContext.current

    val sharedPref =
        context.getSharedPreferences(Constants.Prefs.SHARED_PREFERENCES, Context.MODE_PRIVATE)

    val drawableResource = if (sharedPref.getBoolean(Constants.Prefs.DARK_MODE, false))
        R.drawable.loading_blue
    else R.drawable.loading_yellow

    if (showDeleteConfirmDialog != null) {
        if (showDeleteConfirmDialog.value) {
            AlertDialog(
                onDismissRequest = { showDeleteConfirmDialog.value = false },
                confirmButton = {
                    Button(onClick = {
                        if (deletePlace != null) {
                            deletePlace(place.id)
                        }
                        showDeleteConfirmDialog.value = false
                    }) {
                        Text(stringResource(R.string.ok_btn))
                    }
                },
                dismissButton = {
                    Button(onClick = {
                        showDeleteConfirmDialog.value = false
                    }) {
                        Text(stringResource(R.string.cancel_btn))
                    }
                },
                title = {
                    Text(
                        text = stringResource(R.string.confirm_delete),
                        textAlign = TextAlign.Start,
                        fontWeight = FontWeight.Bold
                    )
                },
                text = {
                    Text(
                        text = stringResource(R.string.delete_place_text),
                    )
                },
            )
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp, start = 4.dp, end = 4.dp, top = 4.dp)
            .clickable(
                onClick = {
                    onClickPlaceItem(place.id)
                },
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(8.dp),
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row(modifier = Modifier.padding(0.dp, 8.dp, 8.dp, 8.dp)) {
                Column(modifier = Modifier.padding(0.dp, 0.dp, 8.dp, 0.dp)) {
                    AsyncImage(
                        model = place.image.imageUrl(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(100.dp, 100.dp)
                            .padding(8.dp)
                            .border(2.dp, color = MaterialTheme.colorScheme.primary, CircleShape)
                            .clip(CircleShape),
                        placeholder = painterResource(id = drawableResource),
                    )
                }
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            modifier = Modifier
                                .padding(start = 8.dp, top = 8.dp, end = 4.dp)
                                .weight(20f, fill = false),
                            text = place.name,
                            style = TextStyle(fontWeight = FontWeight.Bold),
                            fontSize = 20.sp,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis
                        )

                        if (!place.problem.isNullOrEmpty()) {
                            Icon(
                                imageVector = Icons.Filled.ReportProblem,
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(start = 4.dp, top = 10.dp)
                            )
                        }

                        if (place.isModified) {
                            Spacer(modifier = Modifier.weight(5f))
                            Icon(
                                imageVector = Icons.Outlined.EditLocationAlt,
                                contentDescription = null,
                                modifier = Modifier
                                    .padding(start = 4.dp, top = 12.dp),
                            )
                        }

                        if (needFavButton) {
                            Spacer(modifier = Modifier.weight(1f))
                            IconButton(onClick = {
                                if (favIdList?.contains(place.id) == true) {
                                    addOrRemoveFavIdList?.invoke()
                                    isFavPlace?.value = false
                                } else {
                                    addOrRemoveFavIdList?.invoke()
                                    isFavPlace?.value = true
                                }
                            }) {
                                Icon(
                                    imageVector = favouriteIcon,
                                    contentDescription = null,
                                )
                            }
                        }

                        if (isPlaceByOwner) {
                            Spacer(modifier = Modifier.weight(1f))
                            IconButton(onClick = {
                                showDeleteConfirmDialog?.value = true
                            }) {
                                Icon(
                                    imageVector = Icons.Filled.Delete,
                                    tint = Color.Red,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .padding(start = 8.dp),
                                )
                            }
                        }
                    }

                    Row() {
                        Text(
                            modifier = Modifier
                                .padding(8.dp, 0.dp, 0.dp, 0.dp),
                            text = when (place.price) {
                                Price.LOW -> {
                                    stringResource(R.string.price_low)
                                }

                                Price.MIDDLE -> {
                                    stringResource(R.string.price_middle)
                                }

                                else -> stringResource(R.string.price_high)
                            },
                            fontSize = 16.sp,
                            style = TextStyle(
                                fontStyle = FontStyle.Italic,
                                fontWeight = FontWeight.Bold,
                            ),
                        )
                    }
                }
            }
            Row() {
                Icon(
                    imageVector = Icons.Filled.PushPin,
                    contentDescription = null,
                    tint = Color(0xFFF44336),
                    modifier = Modifier.padding(4.dp, 4.dp, 0.dp, 0.dp),
                )
                Text(
                    modifier = Modifier
                        .padding(8.dp, 0.dp),
                    text = place.address,
                    style = TextStyle(fontStyle = FontStyle.Italic),
                    fontSize = 18.sp,
                    maxLines = 3,
                )
            }
        }
    }
}
