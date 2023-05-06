package hu.zsof.restaurantappjetpacknew.ui.details

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings.*
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionRequired
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberPermissionState
import hu.zsof.restaurantappjetpacknew.R
import java.util.*

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
        ChoosePhotoDialog(
            showPhotoPickerDialog = viewModel.photoDialogOpen.value,
            onDismiss = { viewModel.photoDialogOpen.value = false },
            selectedImageUri = viewModel.selectedImageUri,
            viewModel = viewModel,
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

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ChoosePhotoDialog(
    showPhotoPickerDialog: Boolean,
    onDismiss: () -> Unit,
    selectedImageUri: MutableState<Uri?>,
    viewModel: DetailsViewModel,
) {
    val permissionStateGallery =
        rememberPermissionState(permission = Manifest.permission.READ_EXTERNAL_STORAGE)
    GalleryPermission(
        permissionState = permissionStateGallery,
        selectedImageUri = selectedImageUri,
        viewModel,
    )

    val permissionStateCamera =
        rememberPermissionState(permission = Manifest.permission.CAMERA)
    CameraPermission(
        permissionState = permissionStateCamera,
        selectedImageUri = selectedImageUri,
        viewModel,
    )

    if (showPhotoPickerDialog) {
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
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(all = 16.dp),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = stringResource(R.string.photo_picker_title),
                            style = TextStyle(
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                            ),
                        )
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = null,
                            tint = colorResource(android.R.color.darker_gray),
                            modifier = Modifier
                                .width(30.dp)
                                .height(30.dp)
                                .clickable(onClick = onDismiss),
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable(onClick = {
                                permissionStateCamera.launchPermissionRequest()
                                viewModel.cameraPermissionOpen.value =
                                    true // Necessary to be able to close the dialog
                            }),
                        horizontalArrangement = Arrangement.Start,
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.camera),
                            contentDescription = null,
                            modifier = Modifier
                                .width(42.dp)
                                .height(42.dp)
                                .padding(0.dp, 0.dp, 8.dp, 0.dp),
                        )
                        Text(
                            text = stringResource(R.string.take_photo),
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontStyle = FontStyle.Italic,
                            ),
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable(
                                onClick = {
                                    permissionStateGallery.launchPermissionRequest()
                                    viewModel.galleryPermissionOpen.value =
                                        true // Necessary to be able to close the dialog
                                },
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,

                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.gallery),
                            contentDescription = null,
                            modifier = Modifier
                                .width(42.dp)
                                .height(42.dp)
                                .padding(0.dp, 0.dp, 8.dp, 0.dp),
                        )
                        Text(
                            text = stringResource(R.string.choose_gallery),
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontStyle = FontStyle.Italic,
                            ),
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraPermission(
    permissionState: PermissionState,
    selectedImageUri: MutableState<Uri?>,
    viewModel: DetailsViewModel,
) {
    val context = LocalContext.current
    if (viewModel.cameraPermissionOpen.value) {
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
                        viewModel.cameraPermissionOpen.value = false
                    },
                    text = { Text("You can't use this feature because the permission was denied. Please go to Settings and add permission to use the Camera") },
                    confirmButton = {},
                )
            },
        ) {
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun GalleryPermission(
    permissionState: PermissionState,
    selectedImageUri: MutableState<Uri?>,
    viewModel: DetailsViewModel,
) {
    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> selectedImageUri.value = uri },
    )

    val context = LocalContext.current

    if (viewModel.galleryPermissionOpen.value) {
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
                        viewModel.galleryPermissionOpen.value = false
                    },
                    text = { Text("You could not use this function because the permission was denied. Please go to Settings and add permission to use the Gallery") },
                    confirmButton = {
                        // The Google Play store has a policy that limits usage of MANAGE_EXTERNAL_STORAGE
                        if (Build.VERSION.SDK_INT >= 30) {
                            Button(onClick = {
                                val i = Intent(ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                                context.startActivity(i)
                            }) {
                                Text("Go to Settings")
                            }
                        }
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
