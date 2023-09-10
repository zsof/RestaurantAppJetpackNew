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

    var placeNameValue = mutableStateOf(place?.name ?: "")
    var placeNameError = mutableStateOf(false)

    var addressValue = mutableStateOf(place?.address?: "")
    var addressError = mutableStateOf(false)

    var websiteValue = mutableStateOf(place?.web ?: "")
    var emailValue = mutableStateOf(place?.email ?: "")
    var phoneValue = mutableStateOf(place?.phoneNumber ?: "")

    open var glutenFreeChecked = mutableStateOf(place?.filter?.glutenFree ?: false)
    open var lactoseFreeChecked = mutableStateOf(place?.filter?.lactoseFree ?: false)
    open var vegetarianChecked = mutableStateOf(place?.filter?.vegetarian ?: false)
    open var veganChecked = mutableStateOf(place?.filter?.vegan ?: false)
    open var fastFoodChecked = mutableStateOf(place?.filter?.fastFood ?: false)
    open var parkingChecked = mutableStateOf(place?.filter?.parkingAvailable ?: false)
    open var familyPlaceChecked = mutableStateOf(place?.filter?.familyPlace ?: false)
    open var dogFriendlyChecked = mutableStateOf(place?.filter?.dogFriendly ?: false)
    open var deliveryChecked = mutableStateOf(place?.filter?.delivery ?: false)
    open var creditCardChecked = mutableStateOf(place?.filter?.creditCard ?: false)

    var openingHoursOpen = mutableStateOf(false)
    var sameOpeningHoursEveryday = mutableStateOf(false)
    var isOpenHourSet = mutableStateOf(true)

    var basicOpen = mutableStateOf(place?.openDetails?.basicOpen ?: "")
    var basicClose = mutableStateOf(place?.openDetails?.basicClose ?: "")
    var mondayOpen = mutableStateOf(place?.openDetails?.mondayOpen ?: "")
    var mondayClose = mutableStateOf(place?.openDetails?.mondayClose ?: "")
    var tuesdayOpen = mutableStateOf(place?.openDetails?.tuesdayOpen ?: "")
    var tuesdayClose = mutableStateOf(place?.openDetails?.tuesdayClose ?: "")
    var wednesdayOpen = mutableStateOf(place?.openDetails?.wednesdayOpen ?: "")
    var wednesdayClose = mutableStateOf(place?.openDetails?.wednesdayClose ?: "")
    var thursdayOpen = mutableStateOf(place?.openDetails?.thursdayOpen ?: "")
    var thursdayClose = mutableStateOf(place?.openDetails?.thursdayClose ?: "")
    var fridayOpen = mutableStateOf(place?.openDetails?.fridayOpen ?: "")
    var fridayClose = mutableStateOf(place?.openDetails?.fridayClose ?: "")
    var saturdayOpen = mutableStateOf(place?.openDetails?.saturdayOpen ?: "")
    var saturdayClose = mutableStateOf(place?.openDetails?.saturdayClose ?: "")
    var sundayOpen = mutableStateOf(place?.openDetails?.sundayOpen ?: "")
    var sundayClose = mutableStateOf(place?.openDetails?.sundayClose ?: "")

    var mondayCheckbox = mutableStateOf(place?.openDetails?.monday ?: false)
    var tuesdayCheckbox = mutableStateOf(place?.openDetails?.tuesday ?: false)
    var wednesdayCheckbox = mutableStateOf(place?.openDetails?.wednesday ?: false)
    var thursdayCheckbox = mutableStateOf(place?.openDetails?.thursday ?: false)
    var fridayCheckbox = mutableStateOf(place?.openDetails?.friday ?: false)
    var saturdayCheckbox = mutableStateOf(place?.openDetails?.saturday ?: false)
    var sundayCheckbox = mutableStateOf(place?.openDetails?.sunday ?: false)

}
