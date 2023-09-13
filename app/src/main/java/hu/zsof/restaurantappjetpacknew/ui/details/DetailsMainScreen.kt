package hu.zsof.restaurantappjetpacknew.ui.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.hilt.navigation.compose.hiltViewModel
import hu.zsof.restaurantappjetpacknew.model.enums.PlaceType
import hu.zsof.restaurantappjetpacknew.module.AppState
import hu.zsof.restaurantappjetpacknew.ui.common.screen.CommonDetailsScreen

@Composable
fun DetailsMainScreen(
    viewModel: DetailsViewModel = hiltViewModel(),
    placeId: Long,
    onEditPlaceClick: (Long) -> Unit,
) {
    val place = viewModel.placeById.observeAsState().value
    LaunchedEffect(key1 = "Details") {
        viewModel.getPlaceById(placeId)
    }

    /*   if (viewModel.ratingDialogOpen.value) {
           RateDialog(viewModel = viewModel, onDismiss = { viewModel.ratingDialogOpen.value = false })
       }
   */

    viewModel.isPlaceByOwner.value = AppState.loggedUser.value?.id == place?.creatorId

    CommonDetailsScreen(
        placeId = placeId,
        onEditPlaceClick = onEditPlaceClick,
        place = place,
        placeType = PlaceType.PLACE,
        isPlaceByOwner = viewModel.isPlaceByOwner.value,
        openingHoursOpen = viewModel.openingHoursOpenDetails
    )
}

/*@Composable
fun RateDialog(viewModel: DetailsViewModel, onDismiss: () -> Unit) {
    if (viewModel.ratingDialogOpen.value) {
        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(
                dismissOnClickOutside = true,
                dismissOnBackPress = true,
                usePlatformDefaultWidth = false,
            ),
        ) {
            Surface(
                modifier = Modifier
                    .padding(32.dp)
                    .wrapContentHeight(),
                shape = RoundedCornerShape(8.dp),
            ) {
                Column {
                }
            }
        }
    }
}*/
