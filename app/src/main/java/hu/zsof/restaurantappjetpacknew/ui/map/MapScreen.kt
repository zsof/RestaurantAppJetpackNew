package hu.zsof.restaurantappjetpacknew.ui.map

import android.Manifest
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.*
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@OptIn(ExperimentalPermissionsApi::class)
@Composable
@ExperimentalMaterial3Api
fun MapScreen() {
    val context = LocalContext.current

    val multiplePermissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
        ),
    )

    LocationPermissions(multiplePermissionState = multiplePermissionState)

    LaunchedEffect(Unit) {
        multiplePermissionState.launchMultiplePermissionRequest()
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LocationPermissions(
    multiplePermissionState: MultiplePermissionsState,
) {
    PermissionsRequired(
        multiplePermissionsState = multiplePermissionState,
        permissionsNotGrantedContent = {
            val textToShow = if (multiplePermissionState.shouldShowRationale) {
                // If the user has denied the permission but the rationale can be shown,
                // then gently explain why the app requires this permission
                "The location is important for this app. Please grant the permission."
            } else {
                // If it's the first time the user lands on this feature, or the user
                // doesn't want to be asked again for this permission, explain that the
                // permission is required
                "Location permission required for this feature to be available. " +
                    "Please grant the permission"
            }
            AlertDialog(
                onDismissRequest = {
                    // Dismiss the dialog when the user clicks outside the dialog or on the back
                    // button. If you want to disable that functionality, simply use an empty
                    // onCloseRequest.
                    // openDialog.value = false
                },
                confirmButton = {
                    Button(onClick = { multiplePermissionState.launchMultiplePermissionRequest() }) {
                        Text("Request permission")
                    }
                },
                text = { Text(textToShow) },
            )
        },
        permissionsNotAvailableContent = {},
    ) {
        GoogleMap(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 42.dp),
            uiSettings = MapUiSettings(
                zoomControlsEnabled = true,
                compassEnabled = true,
                myLocationButtonEnabled = true,
                rotationGesturesEnabled = true,
            ),
            properties = MapProperties(mapType = MapType.NORMAL, isMyLocationEnabled = true),
            cameraPositionState = CameraPositionState(
                CameraPosition(LatLng(47.497913, 19.040236), 11f, 0f, 0f), // Budapest
            ),
        )
    }
}
