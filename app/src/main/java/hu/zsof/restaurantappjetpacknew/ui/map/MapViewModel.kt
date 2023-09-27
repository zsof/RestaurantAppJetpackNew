package hu.zsof.restaurantappjetpacknew.ui.map

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.zsof.restaurantappjetpacknew.model.User
import hu.zsof.restaurantappjetpacknew.module.AppState
import hu.zsof.restaurantappjetpacknew.network.repository.PlaceRepository
import hu.zsof.restaurantappjetpacknew.network.repository.UserRepository
import hu.zsof.restaurantappjetpacknew.network.response.PlaceMapResponse
import hu.zsof.restaurantappjetpacknew.util.SharedPreferences
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(
    private val placeRepository: PlaceRepository,
    private val userRepository: UserRepository,
    private val sharedPref: SharedPreferences,
) : ViewModel() {

    val isBottomSheetOpen = AppState.mapInfoClicked

    val places = MutableLiveData<List<PlaceMapResponse>>()
    fun requestPlaceData() {
        viewModelScope.launch {
            places.postValue(placeRepository.getAllPlaceInMap())
        }
    }

    val favPlaceIds = MutableLiveData<List<Long>?>()
    val userData = MutableLiveData<User>()
    fun getUser() {
        viewModelScope.launch {
            val user = userRepository.getUserProfile()
            favPlaceIds.postValue(user.favPlaceIds)
            userData.postValue(user)
        }
    }

    fun <T> getAppPreference(key: String): T {
        return sharedPref.getPreference(key)
    }
}
