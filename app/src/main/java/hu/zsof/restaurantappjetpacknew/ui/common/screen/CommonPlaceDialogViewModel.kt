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
open class CommonPlaceDialogViewModel @Inject constructor() : ViewModel() {
    open fun addOrEditPlace(
        typeValue: Type,
        priceValue: Price,
        image: String,
    ) = Unit

    val place = AppState.place

    var dialogOpen = mutableStateOf(true)
    var expandedCategoryMenu = mutableStateOf(false)

    var galleryPermissionOpen = mutableStateOf(false)
    var cameraPermissionOpen = mutableStateOf(false)

    val photoDialogOpen = mutableStateOf(false)
    val selectedImageUri = mutableStateOf<Uri?>(null)

    var sliderValue = mutableStateOf(0f)
    var priceValue = Price.LOW

    var placeNameValue = mutableStateOf(place.value?.name ?: "")
    var placeNameError = mutableStateOf(false)

    var addressValue = mutableStateOf(place.value?.address?: "")
    var addressError = mutableStateOf(false)

    var websiteValue = mutableStateOf(place.value?.web ?: "")
    var emailValue = mutableStateOf(place.value?.email ?: "")
    var phoneValue = mutableStateOf(place.value?.phoneNumber ?: "")

    open var glutenFreeChecked = mutableStateOf(place.value?.filter?.glutenFree ?: false)
    open var lactoseFreeChecked = mutableStateOf(place.value?.filter?.lactoseFree ?: false)
    open var vegetarianChecked = mutableStateOf(place.value?.filter?.vegetarian ?: false)
    open var veganChecked = mutableStateOf(place.value?.filter?.vegan ?: false)
    open var fastFoodChecked = mutableStateOf(place.value?.filter?.fastFood ?: false)
    open var parkingChecked = mutableStateOf(place.value?.filter?.parkingAvailable ?: false)
    open var familyPlaceChecked = mutableStateOf(place.value?.filter?.familyPlace ?: false)
    open var dogFriendlyChecked = mutableStateOf(place.value?.filter?.dogFriendly ?: false)
    open var deliveryChecked = mutableStateOf(place.value?.filter?.delivery ?: false)
    open var creditCardChecked = mutableStateOf(place.value?.filter?.creditCard ?: false)

    var openingHoursOpen = mutableStateOf(false)
    var sameOpeningHoursEveryday = mutableStateOf(false)
    var isOpenHourSet = mutableStateOf(true)

    var basicOpen = mutableStateOf(place.value?.openDetails?.basicOpen ?: "")
    var basicClose = mutableStateOf(place.value?.openDetails?.basicClose ?: "")
    var mondayOpen = mutableStateOf(place.value?.openDetails?.mondayOpen ?: "")
    var mondayClose = mutableStateOf(place.value?.openDetails?.mondayClose ?: "")
    var tuesdayOpen = mutableStateOf(place.value?.openDetails?.tuesdayOpen ?: "")
    var tuesdayClose = mutableStateOf(place.value?.openDetails?.tuesdayClose ?: "")
    var wednesdayOpen = mutableStateOf(place.value?.openDetails?.wednesdayOpen ?: "")
    var wednesdayClose = mutableStateOf(place.value?.openDetails?.wednesdayClose ?: "")
    var thursdayOpen = mutableStateOf(place.value?.openDetails?.thursdayOpen ?: "")
    var thursdayClose = mutableStateOf(place.value?.openDetails?.thursdayClose ?: "")
    var fridayOpen = mutableStateOf(place.value?.openDetails?.fridayOpen ?: "")
    var fridayClose = mutableStateOf(place.value?.openDetails?.fridayClose ?: "")
    var saturdayOpen = mutableStateOf(place.value?.openDetails?.saturdayOpen ?: "")
    var saturdayClose = mutableStateOf(place.value?.openDetails?.saturdayClose ?: "")
    var sundayOpen = mutableStateOf(place.value?.openDetails?.sundayOpen ?: "")
    var sundayClose = mutableStateOf(place.value?.openDetails?.sundayClose ?: "")

    var mondayCheckbox = mutableStateOf(place.value?.openDetails?.monday ?: true)
    var tuesdayCheckbox = mutableStateOf(place.value?.openDetails?.tuesday ?: true)
    var wednesdayCheckbox = mutableStateOf(place.value?.openDetails?.wednesday ?: true)
    var thursdayCheckbox = mutableStateOf(place.value?.openDetails?.thursday ?: true)
    var fridayCheckbox = mutableStateOf(place.value?.openDetails?.friday ?: true)
    var saturdayCheckbox = mutableStateOf(place.value?.openDetails?.saturday ?: true)
    var sundayCheckbox = mutableStateOf(place.value?.openDetails?.sunday ?: true)

}
