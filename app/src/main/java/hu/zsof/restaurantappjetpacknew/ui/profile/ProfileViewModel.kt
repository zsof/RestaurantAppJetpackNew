package hu.zsof.restaurantappjetpacknew.ui.profile

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.zsof.restaurantappjetpacknew.model.CustomFilter
import hu.zsof.restaurantappjetpacknew.model.User
import hu.zsof.restaurantappjetpacknew.network.repository.UserRepository
import hu.zsof.restaurantappjetpacknew.network.request.UserUpdateProfileRequest
import hu.zsof.restaurantappjetpacknew.util.SharedPreferences
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val sharedPref: SharedPreferences,
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

    var switchCheckedState = mutableStateOf(false)

    val userProfile = MutableLiveData<User>()
    fun getUserProfile() {
        viewModelScope.launch {
            userProfile.postValue(userRepository.getUserProfile())
        }
    }

    fun updateUserProfile() {
        viewModelScope.launch {
            userRepository.updateUserProfile(
                UserUpdateProfileRequest(
                    filters = CustomFilter(
                        glutenFree = glutenFreeChecked.value,
                        lactoseFree = lactoseFreeChecked.value,
                        vegetarian = vegetarianChecked.value,
                        vegan = veganChecked.value,
                        fastFood = fastFoodChecked.value,
                        parkingAvailable = parkingChecked.value,
                        dogFriendly = dogFriendlyChecked.value,
                        familyPlace = familyPlaceChecked.value,
                        delivery = deliveryChecked.value,
                        creditCard = creditCardChecked.value,
                    ),
                ),
            )
        }
    }

    fun <T> setAppPreference(key: String, value: T) {
        sharedPref.setPreference(key, value)
    }

    fun <T> getAppPreference(key: String): T {
        return sharedPref.getPreference(key)
    }
}
