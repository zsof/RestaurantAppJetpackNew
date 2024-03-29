package hu.zsof.restaurantappjetpacknew.ui.details

import android.Manifest
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import hu.zsof.restaurantappjetpacknew.model.enums.PlaceType
import hu.zsof.restaurantappjetpacknew.module.AppState
import hu.zsof.restaurantappjetpacknew.ui.common.screen.CommonDetailsScreen
import hu.zsof.restaurantappjetpacknew.util.GalleryPermission
import hu.zsof.restaurantappjetpacknew.util.extension.getRealPathFromURI

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun DetailsMainScreen(
    viewModel: DetailsViewModel = hiltViewModel(),
    placeId: Long,
    onEditPlaceClick: (Long) -> Unit,
) {
    val context = LocalContext.current

    val place = viewModel.placeById.observeAsState().value
    LaunchedEffect(key1 = "Details") {
        viewModel.getPlaceById(placeId)
    }

    viewModel.isPlaceByOwner.value = AppState.loggedUser.value?.id == place?.creatorId

    var imagePath = ""
    viewModel.selectedImageUri.value?.let {
        imagePath = getRealPathFromURI(it, context) ?: ""
    }

    val permissionStateGallery = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        rememberPermissionState(permission = Manifest.permission.READ_MEDIA_IMAGES)
    else rememberPermissionState(permission = Manifest.permission.READ_EXTERNAL_STORAGE)

    if (viewModel.photoPickerOpen.value) {
        GalleryPermission(
            permissionState = permissionStateGallery,
            selectedImageUri = viewModel.selectedImageUri,
        )
        if (viewModel.selectedImageUri.value != null) {
            viewModel.photoPickerOpen.value = false
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
        onEditImageClick = { viewModel.photoPickerOpen.value = true },
        permissionStateGallery = permissionStateGallery,
        place = place,
        placeType = PlaceType.PLACE,
        isPlaceByOwner = viewModel.isPlaceByOwner.value,
        openingHoursOpen = viewModel.openingHoursOpenDetails,
        showProblemDialog = viewModel.showProblemDialog,
        selectedImage = viewModel.selectedImageUri.value
    )
}
