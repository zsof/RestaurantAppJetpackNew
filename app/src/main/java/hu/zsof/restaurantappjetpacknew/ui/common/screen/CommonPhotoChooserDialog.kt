package hu.zsof.restaurantappjetpacknew.ui.common.screen

import android.Manifest
import android.net.Uri
import android.os.Build
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import hu.zsof.restaurantappjetpacknew.R
import hu.zsof.restaurantappjetpacknew.util.CameraPermission
import hu.zsof.restaurantappjetpacknew.util.GalleryPermission

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PhotoChooserDialog(
    showPhotoPickerDialog: Boolean,
    onDismiss: () -> Unit,
    selectedImageUri: MutableState<Uri?>,
    galleryOpenPermission: MutableState<Boolean>,
    cameraOpenPermission: MutableState<Boolean>,
    onCameraOpenClick: () -> Unit,
) {
    val permissionStateGallery = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        rememberPermissionState(permission = Manifest.permission.READ_MEDIA_IMAGES)
    else rememberPermissionState(permission = Manifest.permission.READ_EXTERNAL_STORAGE)

    GalleryPermission(
        permissionState = permissionStateGallery,
        selectedImageUri = selectedImageUri,
        galleryOpenPermission = galleryOpenPermission,
    )

    val permissionStateCamera =
        rememberPermissionState(permission = Manifest.permission.CAMERA)
    CameraPermission(
        permissionState = permissionStateCamera,
        selectedImageUri = selectedImageUri,
        cameraOpenPermission = cameraOpenPermission,
        onCameraOpenClick = onCameraOpenClick
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
                                cameraOpenPermission.value =
                                    true // Necessary to be able to close the dialog
                                onDismiss()
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
                                    galleryOpenPermission.value =
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
