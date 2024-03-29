package hu.zsof.restaurantappjetpacknew.ui.profile

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.zsof.restaurantappjetpacknew.model.CustomFilter
import hu.zsof.restaurantappjetpacknew.module.AppState
import hu.zsof.restaurantappjetpacknew.network.repository.ResourceRepository
import hu.zsof.restaurantappjetpacknew.network.repository.UserRepository
import hu.zsof.restaurantappjetpacknew.network.request.UserUpdateProfileRequest
import hu.zsof.restaurantappjetpacknew.util.Constants
import hu.zsof.restaurantappjetpacknew.util.SharedPreferences
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val resourceRepository: ResourceRepository,
    private val sharedPref: SharedPreferences,
) :
    ViewModel() {
    val userProfile = AppState.loggedUser

    val glutenFreeChecked = mutableStateOf(false)
    val lactoseFreeChecked = mutableStateOf(false)
    val vegetarianChecked = mutableStateOf(false)
    val veganChecked = mutableStateOf(false)
    val fastFoodChecked = mutableStateOf(false)
    val parkingChecked = mutableStateOf(false)
    val familyPlaceChecked = mutableStateOf(false)
    val dogFriendlyChecked = mutableStateOf(false)
    val deliveryChecked = mutableStateOf(false)
    val creditCardChecked = mutableStateOf(false)

    val switchCheckedState = mutableStateOf(false)

    val photoPickerOpen = mutableStateOf(false)
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

    fun updateUserProfileFilters() {
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

    fun updateUserProfileName() {
        viewModelScope.launch {
            AppState.loggedUser.postValue(
                userRepository.updateUserProfile(
                    UserUpdateProfileRequest(
                        name = userName.value
                    ),
                )
            )
        }
    }

    fun updateUserProfileImage(image: String) {
        viewModelScope.launch {
            if (userProfile.value != null) {
                resourceRepository.addNewImage(
                    filePath = image,
                    type = Constants.IMAGE_USER_TYPE,
                    itemId = userProfile.value!!.id,
                    previousImagePath = userProfile.value?.image
                )
            }
        }
    }

    fun <T> setAppPreference(key: String, value: T) {
        sharedPref.setPreference(key, value)
    }

    fun <T> getAppPreference(key: String): T {
        return sharedPref.getPreference(key)
    }
}
