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
import hu.zsof.restaurantappjetpacknew.model.convertToPlaceMapResponse
import hu.zsof.restaurantappjetpacknew.module.AppState
import hu.zsof.restaurantappjetpacknew.network.response.PlaceMapResponse
import hu.zsof.restaurantappjetpacknew.util.Constants
import hu.zsof.restaurantappjetpacknew.util.Constants.ROLE_OWNER

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

    LocationPermissions(
        multiplePermissionState = multiplePermissionState,
        onLongClick,
    )

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
    val userType = viewModel.getAppPreference<String>(Constants.Prefs.USER_TYPE)
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
                .padding(bottom = 36.dp),
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
                if (userType == ROLE_OWNER) {
                    AppState.setLatLng(it)
                    onLongClick(it)
                }
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
        viewModel.getUser()
    }

    val places = viewModel.places.observeAsState(listOf())
    val user = viewModel.userData.observeAsState().value
    val favIdList = viewModel.favPlaceIds.observeAsState().value

    // User által (profilban) beállított alap filterek, ha nincs benne semmi, marad a simma places
    var userFilteredPlaces = mutableListOf<PlaceMapResponse>()
    if (user != null) {
        userFilteredPlaces = places.value.filter { place ->
            user.filterItems.convertToList().compare(place.filterItems.convertToList())
        }.toMutableList()
    }

    // Szűrőben beállított ideiglenes filterek
    val globalFilteredPlaces = AppState.filteredPlaces.observeAsState(listOf())

    val mapPlacesToShow = if (!globalFilteredPlaces.value.isNullOrEmpty()) {
        globalFilteredPlaces.value.convertToPlaceMapResponse()
    } else {
        userFilteredPlaces
    }

    for (place in mapPlacesToShow) {
        val mapIcon = if (favIdList?.contains(place.id) == true) {
            BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_CYAN)
        } else {
            BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
        }

        Marker(
            state = MarkerState(LatLng(place.latitude, place.longitude)),
            title = place.name,
            icon = mapIcon,
            /*onInfoWindowClick = {
                ScreenModel.NavigationScreen.Details.passPlaceId(
                    place.id,
                )
            },*/
        )
    }
}
