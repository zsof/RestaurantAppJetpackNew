package hu.zsof.restaurantappjetpacknew.network.repository

import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import hu.zsof.restaurantappjetpacknew.model.Place
import hu.zsof.restaurantappjetpacknew.util.Constants.ROLE_USER

object LocalDataStateService {

    var place: Place = Place()
    private var latLng: LatLng? = null

    var userType = MutableLiveData(ROLE_USER)

    var filteredPlaces = MutableLiveData<List<Place>>()

    fun getLatLng(): LatLng {
        if (latLng == null) {
            throw Exception("Hiba történt, nincs ilyen koordináta")
        } else {
            return latLng!!
        }
    }

    fun setLatLng(latLng: LatLng) {
        LocalDataStateService.latLng = latLng
    }
}
