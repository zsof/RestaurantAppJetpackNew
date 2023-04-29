package hu.zsof.restaurantappjetpacknew.ui.filter

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.zsof.restaurantappjetpacknew.model.CustomFilter
import hu.zsof.restaurantappjetpacknew.model.enums.Price
import hu.zsof.restaurantappjetpacknew.model.enums.Type
import hu.zsof.restaurantappjetpacknew.network.repository.PlaceRepository
import hu.zsof.restaurantappjetpacknew.network.request.FilterRequest
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class FilterPlaceDialogViewModel @Inject constructor(private val placeRepository: PlaceRepository) :
    ViewModel() {

    var dialogOpen = mutableStateOf(true)
    var expandedCategoryMenu = mutableStateOf(false)

    var sliderValue = mutableStateOf(0f)
    var priceValue = Price.LOW

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

    fun filterPlaces(typeValue: Type?) = runBlocking {
        placeRepository.filterPlaces(
            FilterRequest(
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
                type = typeValue,
            ),
        )
    }
}
