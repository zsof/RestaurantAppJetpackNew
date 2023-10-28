package hu.zsof.restaurantappjetpacknew.ui.owner

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import hu.zsof.restaurantappjetpacknew.model.enums.PlaceType
import hu.zsof.restaurantappjetpacknew.ui.common.screen.CommonDetailsScreen

//Owner's place's details in place in review
@OptIn(ExperimentalPermissionsApi::class)
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
        showProblemDialog = viewModel.showProblemDialog,
        placeType = PlaceType.MODIFIED_PLACE,
        isPlaceByOwner = true,
        openingHoursOpen = viewModel.openingHoursOpenDetails
    )
}
