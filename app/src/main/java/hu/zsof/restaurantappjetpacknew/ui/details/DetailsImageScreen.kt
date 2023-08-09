package hu.zsof.restaurantappjetpacknew.ui.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import hu.zsof.restaurantappjetpacknew.ui.common.PhotoChooserDialog

@Composable
fun DetailsImageScreen(
    viewModel: DetailsViewModel = hiltViewModel(),
    placeId: Long,
) {
    val place = viewModel.placeById.observeAsState().value
    LaunchedEffect(key1 = "DetailsImage") {
        viewModel.getPlaceById(placeId)
    }

    if (viewModel.photoDialogOpen.value) {
        PhotoChooserDialog(
            showPhotoPickerDialog = viewModel.photoDialogOpen.value,
            onDismiss = { viewModel.photoDialogOpen.value = false },
            selectedImageUri = viewModel.selectedImageUri,
            galleryOpenPermission = viewModel.galleryPermissionOpen,
            cameraOpenPermission = viewModel.cameraPermissionOpen,
        )
        if (viewModel.selectedImageUri.value != null) {
            viewModel.photoDialogOpen.value = false
        }
    }

    Surface(
        content = {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
            ) {
                Button(
                    onClick = { viewModel.photoDialogOpen.value = true },
                ) {
                    Icon(imageVector = Icons.Filled.PhotoCamera, contentDescription = null)
                }
            }
        },
    )
}
