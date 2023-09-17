package hu.zsof.restaurantappjetpacknew.ui.homelist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FilterAlt
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
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import hu.zsof.restaurantappjetpacknew.R
import hu.zsof.restaurantappjetpacknew.model.Place
import hu.zsof.restaurantappjetpacknew.module.AppState
import hu.zsof.restaurantappjetpacknew.ui.common.field.SearchTextField
import hu.zsof.restaurantappjetpacknew.ui.common.screen.PlaceListItem
import hu.zsof.restaurantappjetpacknew.ui.common.search
import hu.zsof.restaurantappjetpacknew.util.Constants.ROLE_OWNER

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
    val searchFilteredPlaces = AppState.searchedPlaces.observeAsState()
    // Globális szűrés, felülírja az alap beállított szűrést -> minden elemen keres
    val globalFilteredPlaces = AppState.filteredPlaces.observeAsState()

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
                if (!searchFilteredPlaces.value.isNullOrEmpty() || viewModel.isSearchListNotMatchWithPlaces.value) {
                    LazyColumn(
                        contentPadding = PaddingValues(8.dp),
                    ) {
                        items(searchFilteredPlaces.value!!) {
                            PlaceListItem(
                                place = it,
                                onClickPlaceItem = onClickPlaceItem,
                                favIdList = viewModel.favPlaceIds.observeAsState().value,
                                needFavButton = true,
                                addOrRemoveFavIdList = { viewModel.addOrRemoveFavPlace(it) },
                                isFavPlace = viewModel.isFavPlace,
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
                                AppState.filteredPlaces.value = mutableListOf()
                            }),
                    )
                    LazyColumn(
                        contentPadding = PaddingValues(8.dp),
                    ) {
                        items(globalFilteredPlaces.value!!) { filteredPlace ->
                            PlaceListItem(
                                place = filteredPlace,
                                onClickPlaceItem = onClickPlaceItem,
                                favIdList = viewModel.favPlaceIds.observeAsState().value,
                                needFavButton = true,
                                addOrRemoveFavIdList = { viewModel.addOrRemoveFavPlace(filteredPlace) },
                                isFavPlace = viewModel.isFavPlace,
                            )
                        }
                    }
                } else {
                    LazyColumn(
                        contentPadding = PaddingValues(8.dp),
                    ) {
                        items(userFilteredPlaces) { userFilteredPlaces ->
                            PlaceListItem(
                                place = userFilteredPlaces,
                                onClickPlaceItem = onClickPlaceItem,
                                favIdList = viewModel.favPlaceIds.observeAsState().value,
                                needFavButton = true,
                                addOrRemoveFavIdList = {
                                    viewModel.addOrRemoveFavPlace(
                                        userFilteredPlaces,
                                    )
                                },
                                isFavPlace = viewModel.isFavPlace,
                            )
                        }
                    }
                }
            }
        },
    )
}