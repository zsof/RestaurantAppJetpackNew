package hu.zsof.restaurantappjetpacknew.module

import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import hu.zsof.restaurantappjetpacknew.model.Place
import hu.zsof.restaurantappjetpacknew.model.User
import hu.zsof.restaurantappjetpacknew.util.Constants.LOGIN_START

object AppState {

    var isModifiedPlace: Boolean = false
    var place: Place? = Place()

    private var latLng: LatLng? = null

    val loggedUser = MutableLiveData<User?>(null)
    val startDestination = MutableLiveData(LOGIN_START)

    var filteredPlaces = MutableLiveData<List<Place>>()
    var searchedPlaces = MutableLiveData<List<Place>>()

    val darkTheme = MutableLiveData<Boolean>()

    // todo mutable statek ne legyen var -ok
    // todo state dolgai legyen mind mutablestate /mutableLiveData hiszen ezek az állapot leírói
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
