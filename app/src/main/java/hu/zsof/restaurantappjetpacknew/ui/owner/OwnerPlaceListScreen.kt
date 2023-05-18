package hu.zsof.restaurantappjetpacknew.ui.owner

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.filled.ReportProblem
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import hu.zsof.restaurantappjetpacknew.R
import hu.zsof.restaurantappjetpacknew.model.Place
import hu.zsof.restaurantappjetpacknew.model.PlaceInReview
import hu.zsof.restaurantappjetpacknew.model.enums.Price
import hu.zsof.restaurantappjetpacknew.util.extension.imageUrl

@ExperimentalMaterial3Api
@Composable
fun OwnerPlaceListScreen(
    viewModel: OwnerPlaceViewModel = hiltViewModel(),
    onClickPlaceItem: (Long) -> Unit,
    onClickPlaceInReviewItem: (Long) -> Unit,
) {
    val places = viewModel.ownerPlaces.observeAsState(listOf())
    val placesInReview = viewModel.ownerPlacesInReview.observeAsState(listOf())
    LaunchedEffect(key1 = 1) {
        viewModel.showPlaces()
        viewModel.showPlacesInReview()
    }

    Scaffold(
        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(0.dp, 0.dp, 0.dp, 38.dp)
                    .background(MaterialTheme.colorScheme.background),
            ) {
                Text(
                    text = stringResource(id = R.string.wait_for_accept),
                    color = Color.Gray,
                    modifier = Modifier
                        .padding(16.dp, 8.dp, 0.dp, 0.dp),
                )
                LazyColumn(
                    contentPadding = PaddingValues(8.dp),
                ) {
                    items(placesInReview.value) {
                        OwnerListItemReview(
                            placeInReview = it,
                            onClickPlaceInReviewItem = onClickPlaceInReviewItem,
                        )
                    }
                }
                Text(
                    text = stringResource(id = R.string.accepted_places),
                    color = Color.Gray,
                    modifier = Modifier
                        .padding(16.dp, 8.dp, 0.dp, 0.dp),
                )
                LazyColumn(
                    contentPadding = PaddingValues(8.dp),
                ) {
                    items(places.value) {
                        OwnerListItem(place = it, onClickPlaceItem = onClickPlaceItem)
                    }
                }
            }
        },
    )
}

@ExperimentalMaterial3Api
@Composable
private fun OwnerListItem(
    place: Place,
    onClickPlaceItem: (Long) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
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
                            .size(70.dp, 70.dp)
                            .padding(8.dp)
                            .border(2.dp, color = MaterialTheme.colorScheme.primary, CircleShape)
                            .clip(CircleShape),
                    )
                }
                Column() {
                    Row() {
                        Text(
                            modifier = Modifier
                                .padding(start = 8.dp, top = 8.dp, end = 8.dp),
                            text = place.name,
                            style = TextStyle(fontWeight = FontWeight.Bold),
                            fontSize = 20.sp,
                            maxLines = 3,
                        )
                    }
                    Row() {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = null,
                            modifier = Modifier
                                .size(34.dp)
                                .padding(2.dp, 4.dp, 0.dp, 0.dp),
                            tint = Color(0xFFFFC107),

                        )
                        Text(
                            text = place.rate.toString(),
                            style = TextStyle(
                                fontStyle = FontStyle.Italic,
                                fontWeight = FontWeight.Bold,
                            ),
                            modifier = Modifier
                                .padding(8.dp, 8.dp, 8.dp, 8.dp),
                            fontSize = 16.sp,
                        )
                        Text(
                            modifier = Modifier
                                .padding(16.dp, 8.dp, 0.dp, 0.dp),
                            text = when (place.price) {
                                Price.LOW -> {
                                    "$"
                                }
                                Price.MIDDLE -> {
                                    "$$"
                                }
                                else -> "$$$"
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

@ExperimentalMaterial3Api
@Composable
private fun OwnerListItemReview(
    placeInReview: PlaceInReview,
    onClickPlaceInReviewItem: (Long) -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .clickable(
                onClick = { onClickPlaceInReviewItem(placeInReview.id) },
            ),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(8.dp),
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            Row(modifier = Modifier.padding(0.dp, 8.dp, 8.dp, 8.dp)) {
                Column(modifier = Modifier.padding(0.dp, 0.dp, 8.dp, 0.dp)) {
                    AsyncImage(
                        model = placeInReview.image.imageUrl(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(70.dp, 70.dp)
                            .padding(8.dp)
                            .border(2.dp, color = MaterialTheme.colorScheme.primary, CircleShape)
                            .clip(CircleShape),
                    )
                }
                Column() {
                    Row() {
                        Text(
                            modifier = Modifier
                                .padding(start = 8.dp, top = 8.dp, end = 8.dp),
                            text = placeInReview.name,
                            style = TextStyle(fontWeight = FontWeight.Bold),
                            fontSize = 20.sp,
                            maxLines = 3,
                        )
                        Spacer(modifier = Modifier.weight(1f))

                        if (!placeInReview.problem.isNullOrEmpty()) {
                            Icon(
                                imageVector = Icons.Filled.ReportProblem,
                                contentDescription = null,
                            )
                        }
                    }
                    Row() {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = null,
                            modifier = Modifier
                                .size(34.dp)
                                .padding(2.dp, 4.dp, 0.dp, 0.dp),
                            tint = Color(0xFFFFC107),

                        )
                        Text(
                            text = placeInReview.rate.toString(),
                            style = TextStyle(
                                fontStyle = FontStyle.Italic,
                                fontWeight = FontWeight.Bold,
                            ),
                            modifier = Modifier
                                .padding(8.dp, 8.dp, 8.dp, 8.dp),
                            fontSize = 16.sp,
                        )
                        Text(
                            modifier = Modifier
                                .padding(16.dp, 8.dp, 0.dp, 0.dp),
                            text = when (placeInReview.price) {
                                Price.LOW -> {
                                    "$"
                                }
                                Price.MIDDLE -> {
                                    "$$"
                                }
                                else -> "$$$"
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
                    text = placeInReview.address,
                    style = TextStyle(fontStyle = FontStyle.Italic),
                    fontSize = 18.sp,
                    maxLines = 3,
                )
            }
        }
    }
}
