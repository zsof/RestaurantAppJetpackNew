package hu.zsof.restaurantappjetpacknew.ui.homelist

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.FilterAlt
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import hu.zsof.restaurantappjetpacknew.R
import hu.zsof.restaurantappjetpacknew.model.Place
import hu.zsof.restaurantappjetpacknew.model.enums.Price
import hu.zsof.restaurantappjetpacknew.network.repository.LocalDataStateService
import hu.zsof.restaurantappjetpacknew.ui.common.PlaceListItem
import hu.zsof.restaurantappjetpacknew.ui.common.SearchTextField
import hu.zsof.restaurantappjetpacknew.util.Constants.ROLE_OWNER
import hu.zsof.restaurantappjetpacknew.util.extension.imageUrl

@OptIn(ExperimentalPermissionsApi::class, ExperimentalComposeUiApi::class)
@ExperimentalMaterial3Api
@Composable
fun HomeListScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onFabClick: () -> Unit,
    onClickPlaceItem: (Long) -> Unit,
    onFilterClick: () -> Unit,
) {
    // LaunchedEffect should be used when you want that some action must be taken
    // when your composable is first launched/relaunched (or when the key parameter has changed).
    // For example, when you want to request some data from your ViewModel or run some sort of animation

    val places = viewModel.places.observeAsState(listOf())
    val user = viewModel.userData.observeAsState().value

    // User által beállításokban beállított alapértelmezett szűrők
    var userFilteredPlaces = mutableListOf<Place>()
    if (user != null) {
        userFilteredPlaces = places.value.filter { place ->
            user.filterItems.convertToList().compare(place.filter.convertToList())
        }.toMutableList()
    }

    // Keresésben az elemek
    val searchItems = mutableListOf<Place>()
    // Keresésben elemek, csak observable
    val searchFilteredPlaces = LocalDataStateService.searchedPlaces.observeAsState()
    // Globális szűrés, felülírja az alap beállított szűrést -> minden elemen keres
    val globalFilteredPlaces = LocalDataStateService.filteredPlaces.observeAsState()

    // A homeScreen minden változáskor lefut, ezáltal a viewmodelles dolgok is, ha nem lennének launchedeffectben
    LaunchedEffect(key1 = "HomeList") {
        viewModel.showPlaces()
        // Ha ez a homeListItem-ben lenne, akkor meg minden egyes item-re lefutna, de elég egyszer lekérni ezt
        viewModel.getUser()
    }

    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        modifier = Modifier.padding(bottom = 16.dp),
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
                Row() {
                    SearchTextField(
                        onValueChange = {
                            viewModel.searchText.value = it
                            search(
                                globalFilteredPlaces.value,
                                userFilteredPlaces,
                                viewModel,
                                searchItems,
                            )
                        },
                        modifier = Modifier
                            .weight(4f)
                            .padding(8.dp)
                            .fillMaxWidth(),
                        value = viewModel.searchText.value,
                        label = stringResource(id = R.string.search),
                        placeholder = stringResource(id = R.string.search),
                        keyboardActions = KeyboardActions(onSearch = { keyboardController?.hide() }),
                    )

                    IconButton(
                        onClick = onFilterClick,
                        modifier = Modifier
                            .padding(8.dp)
                            .weight(1f)
                            .align(Alignment.CenterVertically),
                    ) {
                        Icon(
                            imageVector = Icons.Filled.FilterAlt,
                            contentDescription = null,
                            modifier = Modifier.size(36.dp),
                            tint = MaterialTheme.colorScheme.primary,
                        )
                    }
                }

                // Keresés a legerőseb, ha van ->  keresés globális szűrésben levő elemeken (ha létezik ilyen lista), vagy ha nem létezik, akkor user által mentett szűrésben levő elemeken
                if (!searchFilteredPlaces.value.isNullOrEmpty()) {
                    LazyColumn(
                        contentPadding = PaddingValues(8.dp),
                    ) {
                        items(searchFilteredPlaces.value!!) {
                            PlaceListItem(
                                place = it,
                                onClickPlaceItem = onClickPlaceItem,
                                favIdList = viewModel.favPlaceIds.observeAsState().value,
                                needFavButton = true,
                                addOrRemoveFavIdList = { viewModel.addOrRemoveFavPlace(it.id) },
                            )
                        }
                    }
                    // Ha van globális szűrés, erősebb mint a user mentett szűrői
                } else if (!globalFilteredPlaces.value.isNullOrEmpty()) {
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

fun search(
    globalFilteredPlaces: List<Place>?,
    userFilteredPlaces: MutableList<Place>,
    viewModel: HomeViewModel,
    searchItems: MutableList<Place>,
) {
    if (viewModel.searchText.value.isNotEmpty()) {
        LocalDataStateService.searchedPlaces.value = mutableListOf()
        searchItems.clear()

        if (!globalFilteredPlaces.isNullOrEmpty()) {
            globalFilteredPlaces.forEach { place ->
                if (place.name.contains(
                        viewModel.searchText.value,
                        ignoreCase = true,
                    )
                ) {
                    searchItems.add(place)
                }
            }
        } else {
            userFilteredPlaces.forEach { place ->
                if (place.name.contains(
                        viewModel.searchText.value,
                        ignoreCase = true,
                    )
                ) {
                    searchItems.add(place)
                }
            }
        }
        LocalDataStateService.searchedPlaces.value = searchItems
    } else {
        LocalDataStateService.searchedPlaces.value = mutableListOf()
    }
}
