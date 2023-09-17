package hu.zsof.restaurantappjetpacknew.ui.owner

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import hu.zsof.restaurantappjetpacknew.model.enums.PlaceType
import hu.zsof.restaurantappjetpacknew.ui.common.screen.CommonDetailsScreen

//Owner's place's details in place in review
@Composable
fun OwnerDetailsScreen(
    viewModel: OwnerPlaceViewModel = hiltViewModel(),
    placeId: Long,
) {
    val placeInReview = viewModel.reviewPlaceById.observeAsState().value
    LaunchedEffect(key1 = "ReviewDetails") {
        viewModel.getReviewPlaceById(placeId)
    }

    CommonDetailsScreen(
        placeId = placeId,
        place = placeInReview,
        placeType = PlaceType.MODIFIED_PLACE,
        isPlaceByOwner = true,
        showProblemDialog = viewModel.showProblemDialog,
        openingHoursOpen = viewModel.openingHoursOpenDetails
    )
}
