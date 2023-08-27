package hu.zsof.restaurantappjetpacknew.ui.common.screen

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.zsof.restaurantappjetpacknew.model.enums.Price
import hu.zsof.restaurantappjetpacknew.model.enums.Type
import javax.inject.Inject

@HiltViewModel
open class CommonPlaceDialogViewModel @Inject constructor() : ViewModel() {
    open fun addNewPlace(
        typeValue: Type,
        priceValue: Price,
        image: String,
    ) = Unit

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

    var basicOpen = mutableStateOf("")
    var basicClose = mutableStateOf("")
    var mondayOpen = mutableStateOf("")
    var mondayClose = mutableStateOf("")
    var tuesdayOpen = mutableStateOf("")
    var tuesdayClose = mutableStateOf("")
    var wednesdayOpen = mutableStateOf("")
    var wednesdayClose = mutableStateOf("")
    var thursdayOpen = mutableStateOf("")
    var thursdayClose = mutableStateOf("")
    var fridayOpen = mutableStateOf("")
    var fridayClose = mutableStateOf("")
    var saturdayOpen = mutableStateOf("")
    var saturdayClose = mutableStateOf("")
    var sundayOpen = mutableStateOf("")
    var sundayClose = mutableStateOf("")

    var mondayCheckbox = mutableStateOf(false)
    var tuesdayCheckbox = mutableStateOf(false)
    var wednesdayCheckbox = mutableStateOf(false)
    var thursdayCheckbox = mutableStateOf(false)
    var fridayCheckbox = mutableStateOf(false)
    var saturdayCheckbox = mutableStateOf(false)
    var sundayCheckbox = mutableStateOf(false)
}
