package hu.zsof.restaurantappjetpacknew.ui.edit

import dagger.hilt.android.lifecycle.HiltViewModel
import hu.zsof.restaurantappjetpacknew.model.CustomFilter
import hu.zsof.restaurantappjetpacknew.model.OpenDetails
import hu.zsof.restaurantappjetpacknew.model.enums.Price
import hu.zsof.restaurantappjetpacknew.model.enums.Type
import hu.zsof.restaurantappjetpacknew.network.repository.PlaceOwnerRepository
import hu.zsof.restaurantappjetpacknew.network.repository.ResourceRepository
import hu.zsof.restaurantappjetpacknew.network.request.PlaceDataRequest
import hu.zsof.restaurantappjetpacknew.ui.common.screen.CommonPlaceDialogViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class EditPlaceViewModel @Inject constructor(
    private val placeOwnerRepository: PlaceOwnerRepository,
    private val resourceRepository: ResourceRepository,
) : CommonPlaceDialogViewModel() {
    override fun addOrEditPlace(
        typeValue: Type,
        priceValue: Price,
        image: File,
    ) {
        try {
            CoroutineScope(Job() + Dispatchers.IO).launch {
                val placeResponse = placeOwnerRepository.updatePlace(
                    PlaceDataRequest(
                        id = placeId.value,
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
                        latitude = latitudeValue.value,
                        longitude = longitudeValue.value,
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
                        file = image,
                        type = "place",
                        itemId = placeResponse.id,
                    )
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
