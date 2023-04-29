package hu.zsof.restaurantappjetpacknew.ui.homelist

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.zsof.restaurantappjetpacknew.model.Place
import hu.zsof.restaurantappjetpacknew.model.User
import hu.zsof.restaurantappjetpacknew.network.repository.PlaceRepository
import hu.zsof.restaurantappjetpacknew.network.repository.UserRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val placeRepository: PlaceRepository,
    private val userRepository: UserRepository,
) :
    ViewModel() {

    var places = MutableLiveData<List<Place>>()
    fun showPlaces() {
        viewModelScope.launch {
            places.postValue(placeRepository.getAllPlace())
        }
    }

    fun addOrRemoveFavPlace(placeId: Long) {
        viewModelScope.launch {
            val user = userRepository.addOrRemoveFavPlace(placeId)
            favPlaceIds.postValue(user?.favPlaceIds)
        }
    }

    var favPlaceIds = MutableLiveData<List<Long>?>()
    var userData = MutableLiveData<User>()
    fun getUser() {
        viewModelScope.launch {
            val user = userRepository.getUserProfile()
            favPlaceIds.postValue(user.favPlaceIds)
            userData.postValue(user)
        }
    }
}
