package hu.zsof.restaurantappjetpacknew.ui.homelist

import androidx.compose.runtime.mutableStateOf
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
    val searchText = mutableStateOf("")
    val isFavPlace = mutableStateOf(false)
    val isSearchListNotMatchWithPlaces = mutableStateOf(false)

    val places = MutableLiveData<List<Place>>()
    fun showPlaces() {
        viewModelScope.launch {
            places.postValue(placeRepository.getAllPlace())
        }
    }

    val favPlaceIds = MutableLiveData<List<Long>?>()

    fun addOrRemoveFavPlace(place: Place) {
        viewModelScope.launch {
            val user = userRepository.addOrRemoveFavPlace(place.id)
            favPlaceIds.postValue(user?.favPlaceIds)

            if (isFavPlace.value) {
                userRepository.insertFav(place)
            } else {
                userRepository.deleteFav(place)
            }
        }
    }

    val userData = MutableLiveData<User>()
    fun getUser() {
        viewModelScope.launch {
            val user = userRepository.getUserProfile()
            favPlaceIds.postValue(user.favPlaceIds)
            userData.postValue(user)
        }
    }
}
