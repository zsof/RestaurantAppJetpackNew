package hu.zsof.restaurantappjetpacknew.ui.owner

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import hu.zsof.restaurantappjetpacknew.R
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

    if (viewModel.showProblemDialog.value) {
        AlertDialog(
            onDismissRequest = { viewModel.showProblemDialog.value = false },
            confirmButton = {
                Button(onClick = { viewModel.showProblemDialog.value = false }) {
                    Text(stringResource(R.string.ok_btn))
                }
            },
            // TODO valamiért a "" is visszajön backendtpl "szöveg"  <-- így, ezt meg kellene szüntetni
            text = {
                placeInReview?.problem?.substringAfter('"')
                    ?.let { Text(text = it.substringBeforeLast('"')) }
            },
        )
    }

    CommonDetailsScreen(
        placeId = placeId,
        onEditPlaceClick = onEditPlaceInReviewClick,
        place = placeInReview,
        placeType = PlaceType.PLACE_BY_OWNER_IN_REVIEW,
        isPlaceByOwner = true,
        showProblemDialog = viewModel.showProblemDialog,
    )
}
