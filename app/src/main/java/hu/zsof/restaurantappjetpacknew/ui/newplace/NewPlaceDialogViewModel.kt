package hu.zsof.restaurantappjetpacknew.ui.newplace

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.zsof.restaurantappjetpacknew.model.enums.Price
import hu.zsof.restaurantappjetpacknew.model.enums.Type
import hu.zsof.restaurantappjetpacknew.network.repository.LocalDataStateService
import hu.zsof.restaurantappjetpacknew.network.repository.PlaceOwnerRepository
import hu.zsof.restaurantappjetpacknew.network.repository.ResourceRepository
import hu.zsof.restaurantappjetpacknew.network.request.PlaceDataRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewPlaceDialogViewModel @Inject constructor(
    private val placeOwnerRepository: PlaceOwnerRepository,
    private val resourceRepository: ResourceRepository,
) : ViewModel() {

    private val latLang: LatLng = try {
        LocalDataStateService.getLatLng()
    } catch (e: Exception) {
        e.printStackTrace()
        LatLng(0.0, 0.0)
    }

    var dialogOpen = mutableStateOf(true)
    var expanded = mutableStateOf(false)

    var galleryPermissionOpen = mutableStateOf(false)

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
    fun addNewPlace(
        typeValue: Type,
        priceValue: Price,
        // filterValue: CustomFilter,
        image: String,
    ) {
        try {
            // viewmodel scope megszűnik, amint bezáródik a dialog, ezért kell coroutinescope
            // todo valami szebb megoldás

            CoroutineScope(Job() + Dispatchers.IO).launch {
                val placeResponse = placeOwnerRepository.addNewPlace(
                    PlaceDataRequest(
                        placeNameValue.value,
                        addressValue,
                        websiteValue.value,
                        emailValue.value,
                        phoneValue.value,
                        type = typeValue,
                        price = priceValue,
                        // filter = filterValue,
                        latitude = latLang.latitude,
                        longitude = latLang.longitude,
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
    }
}
