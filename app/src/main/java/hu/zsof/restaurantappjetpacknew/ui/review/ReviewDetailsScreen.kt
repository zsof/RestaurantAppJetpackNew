package hu.zsof.restaurantappjetpacknew.ui.review

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import hu.zsof.restaurantappjetpacknew.R
import hu.zsof.restaurantappjetpacknew.model.enums.FabButton
import hu.zsof.restaurantappjetpacknew.model.enums.PlaceType
import hu.zsof.restaurantappjetpacknew.ui.common.screen.CommonDetailsScreen

@Composable
fun ReviewDetailsScreen(
    viewModel: ReviewPlaceViewModel = hiltViewModel(),
    placeId: Long,
) {
    val placeInReviewOrModifiedPlace = viewModel.reviewPlaceById.observeAsState().value
    LaunchedEffect(key1 = "ReviewDetails") {
        viewModel.getReviewPlaceById(placeId)
    }

    val items = listOf(
        FabItem(
            icon = ImageBitmap.imageResource(id = R.drawable.ic_report_problem),
            identifier = FabButton.REPORT.name,
            color = Color.Red,

        ),
        FabItem(
            icon = ImageBitmap.imageResource(id = R.drawable.ic_accept),
            identifier = FabButton.ACCEPT.name,
            color = Color.Green,

        ),
    )

    val showProblemDialog = remember {
        mutableStateOf(false)
    }

    if (showProblemDialog.value) {
        AlertDialog(
            onDismissRequest = { showProblemDialog.value = false },
            confirmButton = {
                Button(onClick = { showProblemDialog.value = false }) {
                    Text(stringResource(R.string.ok_btn))
                }
            },
            // TODO valamiért a "" is visszajön backendtpl "szöveg"  <-- így, ezt meg kellene szüntetni
            text = {
                placeInReviewOrModifiedPlace?.problem?.substringAfter('"')
                    ?.let { Text(text = it.substringBeforeLast('"')) }
            },
        )
    }

    CommonDetailsScreen(
        placeId = placeId,
        place = placeInReviewOrModifiedPlace,
        placeType = PlaceType.PLACE_IN_REVIEW,
        isPlaceByOwner = false,
        showProblemDialog = viewModel.showProblemDialog,
        multiFloatingState = viewModel.multiFloatingState,
        fabItems = items,
    )
}
