package hu.zsof.restaurantappjetpacknew.ui.profile

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.zsof.restaurantappjetpacknew.model.CustomFilter
import hu.zsof.restaurantappjetpacknew.module.AppState
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
    var userProfile = AppState.loggedUser

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

    val photoDialogOpen = mutableStateOf(false)
    var galleryPermissionOpen = mutableStateOf(false)
    var cameraPermissionOpen = mutableStateOf(false)
    val selectedImageUri = mutableStateOf<Uri?>(null)

    val changeNameDialogOpen = mutableStateOf(false)

    val userName = mutableStateOf(userProfile.value?.name)

    fun getUserProfile() {
        viewModelScope.launch {
            val user = userRepository.getUserProfile()
            userProfile.postValue(user)
            userName.value = user.name
        }
    }

    fun updateUserProfile() {
        viewModelScope.launch {
            // todo update userporfile o localdata
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

    fun updateUserProfileName() {
        viewModelScope.launch {
            AppState.loggedUser.postValue(userRepository.updateUserProfile(
                UserUpdateProfileRequest(
                    name = userName.value
                ),
            ))
        }
    }

    fun <T> setAppPreference(key: String, value: T) {
        sharedPref.setPreference(key, value)
    }

    fun <T> getAppPreference(key: String): T {
        return sharedPref.getPreference(key)
    }
}
