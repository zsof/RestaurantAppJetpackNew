package hu.zsof.restaurantappjetpacknew.module

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import hu.zsof.restaurantappjetpacknew.model.Place
import hu.zsof.restaurantappjetpacknew.model.User

object AppState {

    val isModifiedPlace = MutableLiveData(false)
    val place = MutableLiveData<Place?>(null)

    val loggedUser = MutableLiveData<User?>(null)

    val filteredPlaces = MutableLiveData<List<Place>>()
    val isPlacesFiltered = MutableLiveData(false)

    val searchedPlaces = MutableLiveData<List<Place>>()
    val darkTheme = MutableLiveData<Boolean>()

    val mapInfoClicked = mutableStateOf(false)

    private var latLng: LatLng? = null

    fun getLatLng(): LatLng {
        if (latLng == null) {
            throw Exception("Hiba történt, nincs ilyen koordináta")
        } else {
            return latLng!!
        }
    }

    fun setLatLng(latLng: LatLng) {
        AppState.latLng = latLng
    }
}
