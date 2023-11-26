package hu.zsof.restaurantappjetpacknew.util

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.res.stringResource
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionRequired
import com.google.accompanist.permissions.PermissionState
import hu.zsof.restaurantappjetpacknew.R

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun GalleryPermission(
    permissionState: PermissionState,
    selectedImageUri: MutableState<Uri?>
) {
    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            selectedImageUri.value = uri
        },
    )


    PermissionRequired(
        permissionState = permissionState,
        permissionNotGrantedContent = {
            /**
             * If there was already a Manifest.permission request, but the user rejected it
             */
            if (permissionState.permissionRequested) {
                AlertDialog(
                    onDismissRequest = {},
                    confirmButton = {
                        Button(onClick = { permissionState.launchPermissionRequest() }) {
                            Text(stringResource(id = R.string.request_permission))
                        }
                    },
                    text = { Text(stringResource(R.string.permission_gallery_explanation)) },
                )
            }
        },
        /**
         * If the user has already denied permission twice
         */
        permissionNotAvailableContent = {
            AlertDialog(
                onDismissRequest = {},
                text = { Text(stringResource(R.string.permission_gallery_denied_message)) },
                confirmButton = {},
            )
        },
    ) {
        SideEffect {
            singlePhotoPickerLauncher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
            )
        }
    }
}



