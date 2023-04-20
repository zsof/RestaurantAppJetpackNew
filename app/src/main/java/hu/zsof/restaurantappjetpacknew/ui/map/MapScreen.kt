package hu.zsof.restaurantappjetpacknew.ui.map

import android.Manifest
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import hu.zsof.restaurantappjetpacknew.network.repository.LocalDataStateService

@OptIn(ExperimentalPermissionsApi::class)
@Composable
@ExperimentalMaterial3Api
fun MapScreen(onLongClick: (latLng: LatLng) -> Unit) {
    val multiplePermissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
        ),
    )

    LocationPermissions(multiplePermissionState = multiplePermissionState, onLongClick)

    // This way, the permission request is immediately started when the Screen is loaded (it could also be started by pressing a button)
    LaunchedEffect(Unit) {
        multiplePermissionState.launchMultiplePermissionRequest()
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LocationPermissions(
    multiplePermissionState: MultiplePermissionsState,
    onLongClick: (latLng: LatLng) -> Unit,
    viewModel: MapViewModel = hiltViewModel(),
) {
    PermissionsRequired(
        multiplePermissionsState = multiplePermissionState,
        permissionsNotGrantedContent = {
            // if there was already a Manifest.permission request, but the user rejected it
            if (multiplePermissionState.permissionRequested) {
                AlertDialog(
                    onDismissRequest = {},
                    confirmButton = {
                        Button(onClick = { multiplePermissionState.launchMultiplePermissionRequest() }) {
                            Text("Request permission")
                        }
                    },
                    text = { Text("The Location is important for this app. Please grant the Permission.") },
                )
            }
        },
        // if the user has already denied permission twice
        permissionsNotAvailableContent = { // TODO
        },
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
            onMapLongClick = {
                LocalDataStateService.setLatLng(it)
                onLongClick(it)
            },
        ) {
            PlaceMarkers(viewModel)
        }
    }
}

@Composable
fun PlaceMarkers(viewModel: MapViewModel) {
    LaunchedEffect(key1 = Unit) {
        viewModel.requestPlaceData()
    }
    val places = viewModel.places.observeAsState(listOf())
    for (place in places.value) {
        Marker(
            state = MarkerState(LatLng(place.latitude, place.longitude)),
            title = place.name,
            icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED),
        )
    }
}