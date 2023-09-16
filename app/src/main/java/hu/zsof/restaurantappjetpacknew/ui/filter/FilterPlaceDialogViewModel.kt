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

    val dialogOpen = mutableStateOf(true)
    val expandedCategoryMenu = mutableStateOf(false)

    val sliderValue = mutableStateOf(0f)
    var priceValue = Price.LOW
    val priceSelectedToFilter = mutableStateOf(false)

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
                price = if (priceSelectedToFilter.value) {
                    priceValue
                } else null
            ),
        )
    }
}
