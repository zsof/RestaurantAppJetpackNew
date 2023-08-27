package hu.zsof.restaurantappjetpacknew.ui.favorite

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import hu.zsof.restaurantappjetpacknew.ui.common.screen.PlaceListItem

@ExperimentalMaterial3Api
@Composable
fun FavoriteListScreen(
    viewModel: FavoriteViewModel = hiltViewModel(),
    onClickPlaceItem: (Long) -> Unit,
) {
    val places = viewModel.favPlaces.observeAsState(listOf())
    LaunchedEffect(key1 = "FavList") {
        viewModel.showFavPlaces()
    }

    Scaffold(
        modifier = Modifier.padding(bottom = 36.dp),
        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(0.dp, 0.dp, 0.dp, 38.dp)
                    .background(MaterialTheme.colorScheme.background),
            ) {
                LazyColumn(
                    contentPadding = PaddingValues(8.dp),
                ) {
                    items(places.value) {
                        PlaceListItem(place = it, onClickPlaceItem = onClickPlaceItem)
                    }
                }
            }
        },
    )
}
