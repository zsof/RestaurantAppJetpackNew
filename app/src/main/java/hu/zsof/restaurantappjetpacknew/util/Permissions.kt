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
import androidx.compose.ui.platform.LocalContext
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionRequired
import com.google.accompanist.permissions.PermissionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun GalleryPermission(
    permissionState: PermissionState,
    selectedImageUri: MutableState<Uri?>,
    galleryOpenPermission: MutableState<Boolean>,
) {
    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            selectedImageUri.value = uri
        },
    )

    if (galleryOpenPermission.value) {
        PermissionRequired(
            permissionState = permissionState,
            permissionNotGrantedContent = {
                // if there was already a Manifest.permission request, but the user rejected it
                if (permissionState.permissionRequested) {
                    AlertDialog(
                        onDismissRequest = {},
                        confirmButton = {
                            Button(onClick = { permissionState.launchPermissionRequest() }) {
                                Text("Request permission")
                            }
                        },
                        text = { Text("Using the gallery is important for this feature to be available. Please grant the permission.") },
                    )
                }
            },
            // if the user has already denied permission twice
            permissionNotAvailableContent = {
                AlertDialog(
                    onDismissRequest = {
                        galleryOpenPermission.value = false
                    },
                    text = { Text("You could not use this function because the permission was denied. Please go to Settings and add permission to use the Gallery") },
                    confirmButton = {
                        // The Google Play store has a policy that limits usage of MANAGE_EXTERNAL_STORAGE
                        /*  if (Build.VERSION.SDK_INT >= 30) {
                              Button(onClick = {
                                  val i = Intent(ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                                  context.startActivity(i)
                              }) {
                                  Text("Go to Settings")
                              }
                          }*/
                    },
                )
            },
        ) {
            singlePhotoPickerLauncher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly),
            )
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraPermission(
    permissionState: PermissionState,
    selectedImageUri: MutableState<Uri?>,
    cameraOpenPermission: MutableState<Boolean>,
) {
    val context = LocalContext.current
    if (cameraOpenPermission.value) {
        PermissionRequired(
            permissionState = permissionState,
            permissionNotGrantedContent = {
                // if there was already a Manifest.permission request, but the user rejected it
                if (permissionState.permissionRequested) {
                    AlertDialog(
                        onDismissRequest = {
                        },
                        confirmButton = {
                            Button(onClick = { permissionState.launchPermissionRequest() }) {
                                Text("Request permission")
                            }
                        },
                        text = { Text("Using the camera is important for this feature to be available. Please grant the permission.") },
                    )
                }
            },
            // if the user has already denied permission twice
            permissionNotAvailableContent = {
                AlertDialog(
                    onDismissRequest = {
                        cameraOpenPermission.value = false
                    },
                    text = { Text("You can't use this feature because the permission was denied. Please go to Settings and add permission to use the Camera") },
                    confirmButton = {},
                )
            },
        ) {
        }
    }
}
