package hu.zsof.restaurantappjetpacknew.ui.favorite

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import hu.zsof.restaurantappjetpacknew.R
import hu.zsof.restaurantappjetpacknew.ui.common.screen.PlaceListItem
import hu.zsof.restaurantappjetpacknew.util.internet.ConnectionState
import hu.zsof.restaurantappjetpacknew.util.internet.connectivityState

@ExperimentalMaterial3Api
@Composable
fun FavoriteListScreen(
    viewModel: FavoriteViewModel = hiltViewModel(),
    onClickPlaceItem: (Long) -> Unit,
) {
    val connection by connectivityState()
    viewModel.isNetworkConnected.value = connection === ConnectionState.Available

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
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxWidth(),
            ) {
                Text(
                    text = stringResource(id = R.string.favs),
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Bold,
                    ),
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.CenterHorizontally),
                )
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
