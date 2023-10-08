package hu.zsof.restaurantappjetpacknew.ui.owner

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
import hu.zsof.restaurantappjetpacknew.module.AppState
import hu.zsof.restaurantappjetpacknew.ui.common.screen.PlaceListItem

@ExperimentalMaterial3Api
@Composable
fun OwnerPlaceListScreen(
    viewModel: OwnerPlaceViewModel = hiltViewModel(),
    onClickPlaceItem: (Long) -> Unit,
    onClickPlaceInReviewItem: (Long) -> Unit,
    placeType: PlaceType
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
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxWidth(),
            ) {
                if (placeType == PlaceType.PLACE) {
                    LazyColumn(
                        contentPadding = PaddingValues(8.dp),
                    ) {
                        items(places.value.sortedBy { it.name }) { place ->
                            viewModel.isPlaceByOwner.value =
                                AppState.loggedUser.value?.id == place.creatorId

                            PlaceListItem(
                                place = place,
                                onClickPlaceItem = onClickPlaceItem,
                                isPlaceByOwner = viewModel.isPlaceByOwner.value,
                                deletePlace = { viewModel.deletePlace(it) },
                                showDeleteConfirmDialog = viewModel.showDeleteConfirmDialog
                            )
                        }
                    }
                } else if (placeType == PlaceType.PLACE_IN_REVIEW) {
                    LazyColumn(
                        contentPadding = PaddingValues(8.dp),
                    ) {
                        items(placesInReview.value.sortedBy { it.name }) { placeInReview ->
                            viewModel.isPlaceInReviewByOwner.value =
                                AppState.loggedUser.value?.id == placeInReview.creatorId

                            PlaceListItem(
                                place = placeInReview,
                                onClickPlaceItem = onClickPlaceInReviewItem,
                                isPlaceByOwner = viewModel.isPlaceInReviewByOwner.value,
                                deletePlace = { viewModel.deletePlaceInReview(it) },
                                showDeleteConfirmDialog = viewModel.showDeleteConfirmDialog
                            )
                        }
                    }
                }
            }
        }
    )
}
