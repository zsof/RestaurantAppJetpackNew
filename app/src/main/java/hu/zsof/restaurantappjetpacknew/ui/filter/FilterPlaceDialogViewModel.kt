package hu.zsof.restaurantappjetpacknew.ui.filter

import androidx.compose.runtime.mutableStateOf
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.zsof.restaurantappjetpacknew.model.CustomFilter
import hu.zsof.restaurantappjetpacknew.model.enums.Price
import hu.zsof.restaurantappjetpacknew.model.enums.Type
import hu.zsof.restaurantappjetpacknew.network.repository.PlaceRepository
import hu.zsof.restaurantappjetpacknew.network.request.FilterRequest
import hu.zsof.restaurantappjetpacknew.ui.common.screen.CommonPlaceDialogViewModel
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class FilterPlaceDialogViewModel @Inject constructor(private val placeRepository: PlaceRepository) :
    CommonPlaceDialogViewModel() {

    override val dialogOpen = mutableStateOf(true)
    override val expandedCategoryMenu = mutableStateOf(false)

    override val sliderValue = mutableStateOf(0f)
    override val priceValue = mutableStateOf(Price.LOW)
    val priceSelectedToFilter = mutableStateOf(false)

    val typeSelectedToFilter = mutableStateOf(false)

    override val glutenFreeChecked = mutableStateOf(false)
    override val lactoseFreeChecked = mutableStateOf(false)
    override val vegetarianChecked = mutableStateOf(false)
    override val veganChecked = mutableStateOf(false)
    override val fastFoodChecked = mutableStateOf(false)
    override val parkingChecked = mutableStateOf(false)
    override val familyPlaceChecked = mutableStateOf(false)
    override val dogFriendlyChecked = mutableStateOf(false)
    override val deliveryChecked = mutableStateOf(false)
    override val creditCardChecked = mutableStateOf(false)

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
                type = if (typeSelectedToFilter.value) typeValue else null,
                price = if (priceSelectedToFilter.value) priceValue.value else null
            ),
        )
    }
}
