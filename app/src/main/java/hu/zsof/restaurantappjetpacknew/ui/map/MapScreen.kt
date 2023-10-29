package hu.zsof.restaurantappjetpacknew.ui.map

import android.Manifest
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_CYAN
import com.google.android.gms.maps.model.BitmapDescriptorFactory.HUE_RED
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import hu.zsof.restaurantappjetpacknew.R
import hu.zsof.restaurantappjetpacknew.model.convertToPlaceMapResponse
import hu.zsof.restaurantappjetpacknew.module.AppState
import hu.zsof.restaurantappjetpacknew.network.response.PlaceMapResponse
import hu.zsof.restaurantappjetpacknew.util.Constants
import hu.zsof.restaurantappjetpacknew.util.Constants.ROLE_OWNER
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class)
@Composable
@ExperimentalMaterial3Api
fun MapScreen(onLongClick: (latLng: LatLng) -> Unit, onMarkerInfoClick: (Long) -> Unit) {
    val multiplePermissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
        ),
    )

    LocationPermissions(
        multiplePermissionState = multiplePermissionState,
        onLongClick,
        onMarkerInfoClick
    )

    // This way, the permission request is immediately started when the Screen is loaded (it could also be started by pressing a button)
    LaunchedEffect(Unit) {
        multiplePermissionState.launchMultiplePermissionRequest()
    }
}

@OptIn(
    ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class,
)
@Composable
fun LocationPermissions(
    multiplePermissionState: MultiplePermissionsState,
    onLongClick: (latLng: LatLng) -> Unit,
    onMarkerInfoClick: (Long) -> Unit,
    viewModel: MapViewModel = hiltViewModel(),
) {
    val userType = viewModel.getAppPreference<String>(Constants.Prefs.USER_TYPE)
    val coroutineScope = rememberCoroutineScope()

    val modalSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false,
        confirmValueChange = { viewModel.isBottomSheetOpen.value },
    )

    BackHandler(modalSheetState.isVisible) {
        coroutineScope.launch { modalSheetState.hide() }
    }

    PermissionsRequired(
        multiplePermissionsState = multiplePermissionState,
        permissionsNotGrantedContent = {
            // if there was already a Manifest.permission request, but the user rejected it
            if (multiplePermissionState.permissionRequested) {
                AlertDialog(
                    onDismissRequest = {},
                    confirmButton = {
                        Button(onClick = { multiplePermissionState.launchMultiplePermissionRequest() }) {
                            Text(stringResource(R.string.request_permission))
                        }
                    },
                    text = { Text(stringResource(R.string.permission_map_explanation)) },
                )
            }
        },
        // if the user has already denied permission twice
        permissionsNotAvailableContent = {
            AlertDialog(
                onDismissRequest = {},
                text = { Text(stringResource(R.string.permission_map_denied_message)) },
                confirmButton = {},
            )
        },
    ) {
        if (viewModel.isBottomSheetOpen.value) {
            BottomSheet() {
                viewModel.isBottomSheetOpen.value = false
            }
        }

        GoogleMap(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 56.dp),
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
            PlaceMarkers(viewModel, onMarkerInfoClick)
        }
    }
}

@Composable
fun PlaceMarkers(
    viewModel: MapViewModel,
    onMarkerInfoClick: (Long) -> Unit
) {
    LaunchedEffect(key1 = Unit) {
        viewModel.requestPlaceData()
        viewModel.getUser()
    }

    val places = viewModel.places.observeAsState(listOf())
    val user = viewModel.userData.observeAsState().value
    val favIdList = viewModel.favPlaceIds.observeAsState().value

    /**
     *  User által (profilban) beállított alap filterek, ha nincs benne semmi, marad a sima places
     */
    var userFilteredPlaces = mutableListOf<PlaceMapResponse>()
    if (user != null) {
        userFilteredPlaces = places.value.filter { place ->
            user.filterItems
                .convertToList()
                .compare(place.filterItems.convertToList())
        }.toMutableList()
    }

    // Szűrőben beállított ideiglenes filterek
    val globalFilteredPlaces = AppState.filteredPlaces.observeAsState(listOf())

    val mapPlacesToShow = if (!globalFilteredPlaces.value.isNullOrEmpty()) {
        globalFilteredPlaces.value.convertToPlaceMapResponse()
    } else {
        userFilteredPlaces
    }

    val scope = rememberCoroutineScope()

    for (place in mapPlacesToShow) {
        val mapIcon = if (favIdList?.contains(place.id) == true) {
            BitmapDescriptorFactory.defaultMarker(HUE_CYAN)
        } else {
            BitmapDescriptorFactory.defaultMarker(HUE_RED)
        }

        Marker(
            state = MarkerState(LatLng(place.latitude, place.longitude)),
            title = place.name,
            icon = mapIcon,
            onInfoWindowClick = {
                scope.launch {
                    onMarkerInfoClick(place.id)
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BottomSheet(onDismiss: () -> Unit) {
    val modalBottomSheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        onDismissRequest = { onDismiss() },
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
        Column(modifier = Modifier.padding(bottom = 16.dp, start = 16.dp, end = 16.dp)) {
            Text(
                text = stringResource(R.string.map_bottom_sheet_title),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(bottom = 24.dp)
            )
            Text(
                text = stringResource(R.string.map_bottom_sheet_places),
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(vertical = 8.dp)
            )
            Text(
                text = stringResource(R.string.map_bottom_sheet_place_description),
                modifier = Modifier
                    .padding(8.dp)
            )
            Text(
                text = stringResource(R.string.map_bottom_sheet_new_place),
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .padding(vertical = 8.dp)
            )
            Text(
                text = stringResource(R.string.map_bottom_sheet_new_place_description),
                modifier = Modifier
                    .padding(8.dp)
            )
        }
    }
}
