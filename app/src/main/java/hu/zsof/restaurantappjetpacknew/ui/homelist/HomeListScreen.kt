package hu.zsof.restaurantappjetpacknew.ui.homelist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import hu.zsof.restaurantappjetpacknew.R
import hu.zsof.restaurantappjetpacknew.model.Place
import hu.zsof.restaurantappjetpacknew.model.enums.Price
import hu.zsof.restaurantappjetpacknew.network.repository.LocalDataStateService
import hu.zsof.restaurantappjetpacknew.util.Constants.ROLE_OWNER

@OptIn(ExperimentalPermissionsApi::class)
@ExperimentalMaterial3Api
@Composable
fun HomeListScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onFabClick: () -> Unit,
    onClickPlaceItem: (Long) -> Unit,
    onFilterClick: () -> Unit,
    navController: NavHostController,
) {
    // LaunchedEffect should be used when you want that some action must be taken
    // when your composable is first launched/relaunched (or when the key parameter has changed).
    // For example, when you want to request some data from your ViewModel or run some sort of animation

    val places = viewModel.places.observeAsState(listOf())
    val user = viewModel.userData.observeAsState().value

    var userFilteredPlaces = mutableListOf<Place>()
    if (user != null) {
        userFilteredPlaces = places.value.filter { place ->
            user.filterItems.convertToList().compare(place.filter.convertToList())
        }.toMutableList()
    }

    // A homeScreen minden változáskor lefut, ezáltal a viewmodelles dolgok is, ha nem lennének launchedeffectben
    LaunchedEffect(key1 = "HomeList") {
        viewModel.showPlaces()
        // Ha ez a homeListItem-ben lenne, akkor meg minden egyes item-re lefutna, de elég egyszer lekérni ezt
        viewModel.getUser()
    }

    val globalFilteredPlaces = LocalDataStateService.filteredPlaces.observeAsState()

    Scaffold(
        modifier = Modifier.padding(bottom = 36.dp),
        floatingActionButton = {
            if (user?.userType == ROLE_OWNER) {
                FloatingActionButton(
                    onClick = onFabClick,
                    modifier = Modifier.padding(PaddingValues(bottom = 44.dp)),
                ) {
                    Icon(imageVector = Icons.Filled.Add, contentDescription = null)
                }
            }
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(0.dp, 0.dp, 0.dp, 38.dp)
                    .background(MaterialTheme.colorScheme.background),
            ) {
                IconButton(
                    onClick = onFilterClick,
                    modifier = Modifier
                        .align(Alignment.End)
                        .padding(8.dp),
                ) {
                    Icon(
                        imageVector = Icons.Filled.FilterAlt,
                        contentDescription = null,
                        modifier = Modifier.size(36.dp),
                        tint = Color.DarkGray,
                    )
                }

                if (!globalFilteredPlaces.value.isNullOrEmpty()) {
                    Text(
                        text = stringResource(id = R.string.clear_filters),
                        modifier = Modifier
                            .align(Alignment.End)
                            .padding(8.dp)
                            .clickable(onClick = {
                                LocalDataStateService.filteredPlaces.value = mutableListOf()
                            }),
                    )
                    LazyColumn(
                        contentPadding = PaddingValues(8.dp),
                    ) {
                        items(globalFilteredPlaces.value!!) { filteredPlace ->
                            HomeListItem(
                                place = filteredPlace,
                                onClickPlaceItem = onClickPlaceItem,
                            )
                        }
                    }
                } else {
                    LazyColumn(
                        contentPadding = PaddingValues(8.dp),
                    ) {
                        items(userFilteredPlaces) {
                            HomeListItem(place = it, onClickPlaceItem = onClickPlaceItem)
                        }
                    }
                }
            }
        },
    )
}

@ExperimentalMaterial3Api
@Composable
private fun HomeListItem(
    place: Place,
    viewModel: HomeViewModel = hiltViewModel(),
    onClickPlaceItem: (Long) -> Unit,
) {
    val favIdList = viewModel.favPlaceIds.observeAsState().value

    val favouriteIcon = if (favIdList?.contains(place.id) == true) {
        Icons.Default.Favorite
    } else {
        Icons.Default.FavoriteBorder
    }

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
                    Image(
                        painter = painterResource(id = R.drawable.ic_launcher_round),
                        contentDescription = null,
                        modifier = Modifier
                            .size(70.dp, 70.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop,
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
                        Spacer(modifier = Modifier.weight(1f))
                        IconButton(onClick = {
                            // Ide nem kell launchedeffect, mert ez csak akkor fut le, ha gombnyomás történik, ez már nem a homescreen content-jében van, hanem a gombbéban
                            if (favIdList?.contains(place.id) == true) {
                                viewModel.addOrRemoveFavPlace(place.id)
                            } else {
                                viewModel.addOrRemoveFavPlace(place.id)
                            }
                        }) {
                            Icon(
                                imageVector = favouriteIcon,
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
