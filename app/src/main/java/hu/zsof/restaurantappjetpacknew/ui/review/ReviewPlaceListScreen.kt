package hu.zsof.restaurantappjetpacknew.ui.review

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import hu.zsof.restaurantappjetpacknew.model.enums.PlaceType
import hu.zsof.restaurantappjetpacknew.ui.common.screen.PlaceListItem

@ExperimentalMaterial3Api
@Composable
fun ReviewPlaceListScreen(
    viewModel: ReviewPlaceViewModel = hiltViewModel(),
    onClickPlaceItem: (Long) -> Unit,
    placeType: PlaceType
) {
    val newPlaces = viewModel.placesInReview.observeAsState(listOf())
    val modifiedPlaces = viewModel.modifiedPlaces.observeAsState(listOf())
    LaunchedEffect(key1 = 1) {
        viewModel.showPlacesInReview()
        viewModel.showModifiedPlaces()
    }

    Scaffold(
        content = { _ ->
            Column(
                modifier = Modifier
                    .padding(0.dp, 0.dp, 0.dp, 38.dp)
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxWidth()
            ) {
                if (placeType == PlaceType.MODIFIED_PLACE)
                    LazyColumn(
                        contentPadding = PaddingValues(8.dp),
                    ) {
                        // To save isModified value for backend
                        items(modifiedPlaces.value.sortedBy { it.name }) {
                            PlaceListItem(
                                place = it,
                                onClickPlaceItem = onClickPlaceItem,
                            )
                        }
                    }
                else if (placeType == PlaceType.PLACE_IN_REVIEW)
                    LazyColumn(
                        contentPadding = PaddingValues(8.dp),
                    ) {
                        items(newPlaces.value.sortedBy { it.name }) {
                            PlaceListItem(
                                place = it,
                                onClickPlaceItem = onClickPlaceItem,
                            )
                        }
                    }
            }
        }
    )
}
