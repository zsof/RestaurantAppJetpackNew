package hu.zsof.restaurantappjetpacknew.ui.map

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*

@Composable
@ExperimentalMaterial3Api
fun MapScreen() {
    val context = LocalContext.current
    GoogleMap(
        modifier = Modifier.fillMaxSize().padding(bottom = 42.dp),
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
