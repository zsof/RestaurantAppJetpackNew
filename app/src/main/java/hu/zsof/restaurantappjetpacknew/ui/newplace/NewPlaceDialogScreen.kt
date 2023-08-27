package hu.zsof.restaurantappjetpacknew.ui.newplace

import android.annotation.SuppressLint
import android.location.Geocoder
import android.os.Build
import android.provider.Settings.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.*
import com.google.maps.android.compose.*
import hu.zsof.restaurantappjetpacknew.network.repository.LocalDataStateService
import hu.zsof.restaurantappjetpacknew.ui.common.CommonPlaceDialogScreen
import java.io.*
import java.util.*

@SuppressLint("ResourceType")
@Composable
fun NewPlaceDialogScreen(
    viewModel: NewPlaceDialogViewModel = hiltViewModel(),
    onDialogClose: () -> Unit,
) {
    val context = LocalContext.current

    Geocoder(context, Locale.getDefault()).getAddress(
        LocalDataStateService.getLatLng().latitude,
        LocalDataStateService.getLatLng().longitude,
    ) { address: android.location.Address? ->
        if (address != null) {
            viewModel.addressValue = address.getAddressLine(0)
        }
    }

    CommonPlaceDialogScreen(
        viewModel = viewModel,
        onDialogClose = onDialogClose,
    )
}

@Suppress("DEPRECATION")
fun Geocoder.getAddress(
    latitude: Double,
    longitude: Double,
    address: (android.location.Address?) -> Unit,
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getFromLocation(latitude, longitude, 1) { address(it.firstOrNull()) }
        return
    }

    try {
        address(getFromLocation(latitude, longitude, 1)?.firstOrNull())
    } catch (e: Exception) {
        // will catch if there is an internet problem
        address(null)
    }
}
