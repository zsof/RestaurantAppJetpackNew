package hu.zsof.restaurantappjetpacknew.ui.owner

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.zsof.restaurantappjetpacknew.model.Place
import hu.zsof.restaurantappjetpacknew.model.PlaceInReview
import hu.zsof.restaurantappjetpacknew.model.enums.Price
import hu.zsof.restaurantappjetpacknew.network.repository.PlaceInReviewRepository
import hu.zsof.restaurantappjetpacknew.network.repository.PlaceOwnerRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OwnerPlaceViewModel @Inject constructor(
    private val ownerRepository: PlaceOwnerRepository,
    private val placeInReviewRepository: PlaceInReviewRepository,
) :
    ViewModel() {

    val showProblemDialog = mutableStateOf(false)
    val editPlaceDialog = mutableStateOf(false)

    fun deletePlace(placeId: Long) {
        viewModelScope.launch {
            ownerRepository.deletePlace(placeId)
        }
    }

    var ownerPlaces = MutableLiveData<List<Place>>()

    fun showPlaces() {
        viewModelScope.launch {
            ownerPlaces.postValue(ownerRepository.getAllPlaceByOwner())
        }
    }

    var ownerPlacesInReview = MutableLiveData<List<PlaceInReview>>()

    fun showPlacesInReview() {
        viewModelScope.launch {
            ownerPlacesInReview.postValue(ownerRepository.getAllPlaceInReviewByOwner())
        }
    }

    val reviewPlaceById = MutableLiveData<PlaceInReview>()
    fun getReviewPlaceById(placeId: Long) {
        viewModelScope.launch {
            reviewPlaceById.postValue(placeInReviewRepository.getPlaceByIdFromInReview(placeId))
        }
    }

    var dialogOpen = mutableStateOf(true)
    var expandedCategoryMenu = mutableStateOf(false)

    var galleryPermissionOpen = mutableStateOf(false)
    var cameraPermissionOpen = mutableStateOf(false)

    val photoDialogOpen = mutableStateOf(false)
    val selectedImageUri = mutableStateOf<Uri?>(null)

    var sliderValue = mutableStateOf(0f)
    var priceValue = Price.LOW

    var placeNameValue = mutableStateOf("")
    var placeNameError = mutableStateOf(false)

    var addressValue = ""
    var addressError = mutableStateOf(false)

    var websiteValue = mutableStateOf("")
    var emailValue = mutableStateOf("")
    var phoneValue = mutableStateOf("")

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

    var openingHoursOpen = mutableStateOf(false)
    var sameOpeningHoursEveryday = mutableStateOf(false)
    var isOpenHourSet = mutableStateOf(true)
}
