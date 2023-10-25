package hu.zsof.restaurantappjetpacknew.ui.details

import android.content.ContentResolver
import android.provider.MediaStore
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import hu.zsof.restaurantappjetpacknew.model.enums.PlaceType
import hu.zsof.restaurantappjetpacknew.module.AppState
import hu.zsof.restaurantappjetpacknew.ui.common.screen.CommonDetailsScreen
import hu.zsof.restaurantappjetpacknew.ui.common.screen.PhotoChooserDialog
import okio.use

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

    viewModel.isPlaceByOwner.value = AppState.loggedUser.value?.id == place?.creatorId

    val context = LocalContext.current
    val projection = arrayOf(MediaStore.Images.ImageColumns.DATA)
    val contentResolver: ContentResolver = context.contentResolver
    var imagePath = ""

    viewModel.selectedImageUri.value?.let {
        contentResolver.query(
            it,
            projection,
            null,
            null,
            null,
        )
    }
        ?.use { metaCursor ->
            if (metaCursor.moveToFirst()) {
                imagePath = metaCursor.getString(0) ?: ""
            }
        }


    if (viewModel.photoDialogOpen.value) {
        PhotoChooserDialog(
            showPhotoPickerDialog = viewModel.photoDialogOpen,
            onDismiss = { viewModel.photoDialogOpen.value = false },
            selectedImageUri = viewModel.selectedImageUri,
            galleryOpenPermission = viewModel.galleryPermissionOpen,
            cameraOpenPermission = viewModel.cameraPermissionOpen,
            onCameraOpenClick = {}
        )
        if (viewModel.selectedImageUri.value != null) {
            viewModel.photoDialogOpen.value = false
            viewModel.updatePlaceImage(
                imagePath = imagePath,
                placeId = placeId,
                previousImage = place?.image
            )
        }
    }

    CommonDetailsScreen(
        placeId = placeId,
        onEditPlaceClick = onEditPlaceClick,
        onEditImageClick = { viewModel.photoDialogOpen.value = true },
        place = place,
        placeType = PlaceType.PLACE,
        isPlaceByOwner = viewModel.isPlaceByOwner.value,
        openingHoursOpen = viewModel.openingHoursOpenDetails,
        showProblemDialog = viewModel.showProblemDialog,
        selectedImage = viewModel.selectedImageUri.value
    )
}
