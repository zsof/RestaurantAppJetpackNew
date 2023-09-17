package hu.zsof.restaurantappjetpacknew.ui.owner

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.zsof.restaurantappjetpacknew.model.Place
import hu.zsof.restaurantappjetpacknew.model.PlaceInReview
import hu.zsof.restaurantappjetpacknew.network.repository.PlaceInReviewRepository
import hu.zsof.restaurantappjetpacknew.network.repository.PlaceOwnerRepository
import hu.zsof.restaurantappjetpacknew.network.repository.ResourceRepository
import hu.zsof.restaurantappjetpacknew.ui.common.screen.CommonPlaceDialogViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OwnerPlaceViewModel @Inject constructor(
    private val placeOwnerRepository: PlaceOwnerRepository,
    private val placeInReviewRepository: PlaceInReviewRepository,
    private val resourceRepository: ResourceRepository,
) :
    CommonPlaceDialogViewModel() {

    val showProblemDialog = mutableStateOf(false)
    val isPlaceByOwner = mutableStateOf(false)
    val isPlaceInReviewByOwner = mutableStateOf(false)

    val openingHoursOpenDetails = mutableStateOf(false)

    fun deletePlace(placeId: Long) {
        viewModelScope.launch {
            placeOwnerRepository.deletePlace(placeId)
        }
    }

    fun deletePlaceInReview(placeId: Long) {
        viewModelScope.launch {
            placeInReviewRepository.deletePlaceFromInReview(placeId)
        }
    }

    var ownerPlaces = MutableLiveData<List<Place>>()

    fun showPlaces() {
        viewModelScope.launch {
            ownerPlaces.postValue(placeOwnerRepository.getAllPlaceByOwner())
        }
    }

    var ownerPlacesInReview = MutableLiveData<List<PlaceInReview>>()

    fun showPlacesInReview() {
        viewModelScope.launch {
            ownerPlacesInReview.postValue(placeOwnerRepository.getAllPlaceInReviewByOwner())
        }
    }

    val reviewPlaceById = MutableLiveData<PlaceInReview>()
    fun getReviewPlaceById(placeId: Long) {
        viewModelScope.launch {
            reviewPlaceById.postValue(placeInReviewRepository.getPlaceByIdFromInReview(placeId))
        }
    }

   /* override fun addOrEditPlace(
        typeValue: Type,
        priceValue: Price,
        image: String,
    ) {
        try {
            CoroutineScope(Job() + Dispatchers.IO).launch {
                val placeResponse = placeOwnerRepository.updatePlace(
                    PlaceDataRequest(
                        id = place.value?.id ?: -1,
                        name = placeNameValue.value,
                        web = websiteValue.value,
                        email = emailValue.value,
                        phoneNumber = phoneValue.value,
                        type = typeValue,
                        price = priceValue,
                        filter = CustomFilter(
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
                        openDetails = OpenDetails(
                            basicOpen = basicOpen.value,
                            basicClose = basicClose.value,
                            mondayOpen = mondayOpen.value,
                            mondayClose = mondayClose.value,
                            tuesdayOpen = tuesdayOpen.value,
                            tuesdayClose = tuesdayClose.value,
                            wednesdayOpen = wednesdayOpen.value,
                            wednesdayClose = wednesdayClose.value,
                            thursdayOpen = thursdayOpen.value,
                            thursdayClose = thursdayClose.value,
                            fridayOpen = fridayOpen.value,
                            fridayClose = fridayClose.value,
                            saturdayOpen = saturdayOpen.value,
                            saturdayClose = saturdayClose.value,
                            sundayOpen = sundayOpen.value,
                            sundayClose = sundayClose.value,
                            monday = mondayCheckbox.value,
                            tuesday = tuesdayCheckbox.value,
                            wednesday = wednesdayCheckbox.value,
                            thursday = thursdayCheckbox.value,
                            friday = fridayCheckbox.value,
                            saturday = saturdayCheckbox.value,
                            sunday = sundayCheckbox.value,
                        ),
                    ),
                )

                if (placeResponse != null) {
                    resourceRepository.addNewImage(
                        filePath = image,
                        type = "place",
                        itemId = placeResponse.id,
                    )
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }*/
}
