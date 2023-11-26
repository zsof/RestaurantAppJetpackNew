package hu.zsof.restaurantappjetpacknew.ui.common.screen

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.zsof.restaurantappjetpacknew.model.enums.Price
import hu.zsof.restaurantappjetpacknew.model.enums.Type
import hu.zsof.restaurantappjetpacknew.module.AppState
import javax.inject.Inject

@HiltViewModel
open class CommonPlaceDialogViewModel @Inject constructor(
) : ViewModel() {
    open fun addOrEditPlace(
        typeValue: Type,
        priceValue: Price,
        image: String,
    ) = Unit

    val place = AppState.place

    open val dialogOpen = mutableStateOf(true)
    open val expandedCategoryMenu = mutableStateOf(false)
    val selectedOptionIndex = when (place.value?.type) {
        Type.RESTAURANT -> mutableStateOf(0)
        Type.CAFE -> mutableStateOf(1)
        Type.PATISSERIE -> mutableStateOf(2)
        Type.BAKERY -> mutableStateOf(3)
        Type.BAR -> mutableStateOf(4)
        Type.FAST_FOOD -> mutableStateOf(5)
        else -> mutableStateOf(0)
    }

    val photoPickerOpen = mutableStateOf(false)
    val selectedImageUri = mutableStateOf<Uri?>(null)

    open val sliderValue = when (place.value?.price) {
        Price.LOW -> mutableStateOf(0f)
        Price.MIDDLE -> mutableStateOf(5.0f)
        Price.HIGH -> mutableStateOf(10f)
        else -> mutableStateOf(0f)
    }

    open val priceValue = mutableStateOf(place.value?.price ?: Price.LOW)

    val placeId = mutableStateOf(place.value?.id ?: -1)
    val latitudeValue = mutableStateOf(place.value?.latitude ?: 0.0)
    val longitudeValue = mutableStateOf(place.value?.longitude ?: 0.0)

    val placeNameValue = mutableStateOf(place.value?.name ?: "")
    val placeNameError = mutableStateOf(false)

    val addressValue = mutableStateOf(place.value?.address ?: "")
    val addressError = mutableStateOf(false)

    val websiteValue = mutableStateOf(place.value?.web ?: "")
    val emailValue = mutableStateOf(place.value?.email ?: "")
    val phoneValue = mutableStateOf(place.value?.phoneNumber ?: "")

    open val glutenFreeChecked = mutableStateOf(place.value?.filter?.glutenFree ?: false)
    open val lactoseFreeChecked = mutableStateOf(place.value?.filter?.lactoseFree ?: false)
    open val vegetarianChecked = mutableStateOf(place.value?.filter?.vegetarian ?: false)
    open val veganChecked = mutableStateOf(place.value?.filter?.vegan ?: false)
    open val fastFoodChecked = mutableStateOf(place.value?.filter?.fastFood ?: false)
    open val parkingChecked = mutableStateOf(place.value?.filter?.parkingAvailable ?: false)
    open val familyPlaceChecked = mutableStateOf(place.value?.filter?.familyPlace ?: false)
    open val dogFriendlyChecked = mutableStateOf(place.value?.filter?.dogFriendly ?: false)
    open val deliveryChecked = mutableStateOf(place.value?.filter?.delivery ?: false)
    open val creditCardChecked = mutableStateOf(place.value?.filter?.creditCard ?: false)

    val openingHoursOpen = mutableStateOf(false)
    val sameOpeningHoursEveryday = mutableStateOf(false)
    val isOpenHourSet = mutableStateOf(true)

    val basicOpen = mutableStateOf(place.value?.openDetails?.basicOpen ?: "")
    val basicClose = mutableStateOf(place.value?.openDetails?.basicClose ?: "")
    val mondayOpen = mutableStateOf(place.value?.openDetails?.mondayOpen ?: "")
    val mondayClose = mutableStateOf(place.value?.openDetails?.mondayClose ?: "")
    val tuesdayOpen = mutableStateOf(place.value?.openDetails?.tuesdayOpen ?: "")
    val tuesdayClose = mutableStateOf(place.value?.openDetails?.tuesdayClose ?: "")
    val wednesdayOpen = mutableStateOf(place.value?.openDetails?.wednesdayOpen ?: "")
    val wednesdayClose = mutableStateOf(place.value?.openDetails?.wednesdayClose ?: "")
    val thursdayOpen = mutableStateOf(place.value?.openDetails?.thursdayOpen ?: "")
    val thursdayClose = mutableStateOf(place.value?.openDetails?.thursdayClose ?: "")
    val fridayOpen = mutableStateOf(place.value?.openDetails?.fridayOpen ?: "")
    val fridayClose = mutableStateOf(place.value?.openDetails?.fridayClose ?: "")
    val saturdayOpen = mutableStateOf(place.value?.openDetails?.saturdayOpen ?: "")
    val saturdayClose = mutableStateOf(place.value?.openDetails?.saturdayClose ?: "")
    val sundayOpen = mutableStateOf(place.value?.openDetails?.sundayOpen ?: "")
    val sundayClose = mutableStateOf(place.value?.openDetails?.sundayClose ?: "")

    val mondayCheckbox = mutableStateOf(place.value?.openDetails?.monday ?: true)
    val tuesdayCheckbox = mutableStateOf(place.value?.openDetails?.tuesday ?: true)
    val wednesdayCheckbox = mutableStateOf(place.value?.openDetails?.wednesday ?: true)
    val thursdayCheckbox = mutableStateOf(place.value?.openDetails?.thursday ?: true)
    val fridayCheckbox = mutableStateOf(place.value?.openDetails?.friday ?: true)
    val saturdayCheckbox = mutableStateOf(place.value?.openDetails?.saturday ?: true)
    val sundayCheckbox = mutableStateOf(place.value?.openDetails?.sunday ?: true)
}
