package hu.zsof.restaurantappjetpacknew.ui.newplace

import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.zsof.restaurantappjetpacknew.model.CustomFilter
import hu.zsof.restaurantappjetpacknew.model.OpenDetails
import hu.zsof.restaurantappjetpacknew.model.enums.Price
import hu.zsof.restaurantappjetpacknew.model.enums.Type
import hu.zsof.restaurantappjetpacknew.module.AppState
import hu.zsof.restaurantappjetpacknew.network.repository.PlaceOwnerRepository
import hu.zsof.restaurantappjetpacknew.network.repository.ResourceRepository
import hu.zsof.restaurantappjetpacknew.network.request.PlaceDataRequest
import hu.zsof.restaurantappjetpacknew.ui.common.screen.CommonPlaceDialogViewModel
import hu.zsof.restaurantappjetpacknew.util.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewPlaceDialogViewModel @Inject constructor(
    private val placeOwnerRepository: PlaceOwnerRepository,
    private val resourceRepository: ResourceRepository,
) : CommonPlaceDialogViewModel() {

    private val latLang: LatLng = try {
        AppState.getLatLng()
    } catch (e: Exception) {
        e.printStackTrace()
        LatLng(0.0, 0.0)
    }

    override fun addOrEditPlace(
        typeValue: Type,
        priceValue: Price,
        image: String,
    ) {
        try {
            CoroutineScope(Job() + Dispatchers.IO).launch {
                val placeResponse = placeOwnerRepository.addNewPlace(
                    PlaceDataRequest(
                        name = placeNameValue.value,
                        address = addressValue.value,
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
                        latitude = latLang.latitude,
                        longitude = latLang.longitude,
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
                        type = Constants.IMAGE_PLACE_IN_REVIEW_TYPE,
                        itemId = placeResponse.id
                    )
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
