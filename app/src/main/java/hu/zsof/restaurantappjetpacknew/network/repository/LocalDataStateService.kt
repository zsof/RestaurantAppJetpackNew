package hu.zsof.restaurantappjetpacknew.network.repository

import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import hu.zsof.restaurantappjetpacknew.model.Place
import hu.zsof.restaurantappjetpacknew.model.PlaceInReview
import hu.zsof.restaurantappjetpacknew.model.User
import hu.zsof.restaurantappjetpacknew.util.Constants.AUTH_GRAPH_ROUTE
import hu.zsof.restaurantappjetpacknew.util.Constants.ROLE_USER

object LocalDataStateService {

    var isModifiedPlace: Boolean = false
    var place: Place = Place()
    var placeInReview: PlaceInReview = PlaceInReview()
    private var latLng: LatLng? = null

    var userType = MutableLiveData(ROLE_USER)
    var isUserLogged = false
    var loggedUser: User? = null
    var startDestination = AUTH_GRAPH_ROUTE

    var filteredPlaces = MutableLiveData<List<Place>>()
    var searchedPlaces = MutableLiveData<List<Place>>()

    var darkTheme = MutableLiveData<Boolean>()

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
