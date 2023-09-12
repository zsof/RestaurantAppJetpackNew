package hu.zsof.restaurantappjetpacknew.ui.owner

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import hu.zsof.restaurantappjetpacknew.model.enums.PlaceType
import hu.zsof.restaurantappjetpacknew.ui.common.screen.CommonDetailsScreen

@Composable
fun OwnerDetailsScreen(
    viewModel: OwnerPlaceViewModel = hiltViewModel(),
    placeId: Long,
    onEditPlaceInReviewClick: (Long) -> Unit,
) {
    val placeInReview = viewModel.reviewPlaceById.observeAsState().value
    LaunchedEffect(key1 = "ReviewDetails") {
        viewModel.getReviewPlaceById(placeId)
    }

    CommonDetailsScreen(
        placeId = placeId,
        onEditPlaceClick = onEditPlaceInReviewClick,
        place = placeInReview,
        placeType = PlaceType.PLACE_BY_OWNER_IN_REVIEW,
        isPlaceByOwner = true,
        showProblemDialog = viewModel.showProblemDialog,
        openingHoursOpen = viewModel.openingHoursOpenDetails
    )
}
