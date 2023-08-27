package hu.zsof.restaurantappjetpacknew.ui.owner

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import hu.zsof.restaurantappjetpacknew.R
import hu.zsof.restaurantappjetpacknew.ui.common.screen.PlaceListItem

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
                        PlaceListItem(
                            place = it,
                            onClickPlaceItem = onClickPlaceInReviewItem,
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
                        PlaceListItem(place = it, onClickPlaceItem = onClickPlaceItem)
                    }
                }
            }
        },
    )
}
