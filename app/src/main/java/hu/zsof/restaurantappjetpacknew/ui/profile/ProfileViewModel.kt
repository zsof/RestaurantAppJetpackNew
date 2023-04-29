package hu.zsof.restaurantappjetpacknew.ui.profile

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.zsof.restaurantappjetpacknew.model.User
import hu.zsof.restaurantappjetpacknew.network.repository.UserRepository
import hu.zsof.restaurantappjetpacknew.network.request.UserUpdateProfileRequest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
) :
    ViewModel() {

    var glutenFreeChecked = mutableStateOf(false)
    var lactoseFreeChecked = mutableStateOf(false)
    var vegetarianChecked = mutableStateOf(false)
    var veganChecked = mutableStateOf(false)
    var fastFoodChecked = mutableStateOf(false)
    var parkingChecked = mutableStateOf(false)
    var familyPlaceChecked = mutableStateOf(false)
    var dogFriendlyChecked = mutableStateOf(false)
    var deliveryChecked = mutableStateOf(false)
    var creditCardChecked = mutableStateOf(false)

    val userProfile = MutableLiveData<User>()
    fun getUserProfile() {
        viewModelScope.launch {
            userProfile.postValue(userRepository.getUserProfile())
        }
    }

    fun updateUserProfile(userUpdateProfileRequest: UserUpdateProfileRequest) {
        viewModelScope.launch {
            userRepository.updateUserProfile(userUpdateProfileRequest)
            // todo check if userProfile refreshes after update
        }
    }
}
