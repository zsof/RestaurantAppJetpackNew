package hu.zsof.restaurantappjetpacknew.ui.details

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.zsof.restaurantappjetpacknew.model.Place
import hu.zsof.restaurantappjetpacknew.network.repository.PlaceRepository
import hu.zsof.restaurantappjetpacknew.util.SharedPreferences
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val placeRepository: PlaceRepository,
    private val sharedPref: SharedPreferences,
) :
    ViewModel() {

    val galleryPermissionOpen = mutableStateOf(false)

    val cameraPermissionOpen = mutableStateOf(false)
    val photoDialogOpen = mutableStateOf(false)

    val openingHoursOpenDetails = mutableStateOf(false)

    val selectedImageUri = mutableStateOf<Uri?>(null)

    val ratingDialogOpen = mutableStateOf(false)
    val rating = mutableStateOf(0f)

    val isPlaceByOwner = mutableStateOf(false)

    val placeById = MutableLiveData<Place>()
    fun getPlaceById(placeId: Long) {
        viewModelScope.launch {
            placeById.postValue(placeRepository.getPlaceById(placeId))
        }
    }

    fun <T> setAppPreference(key: String, value: T) {
        sharedPref.setPreference(key, value)
    }

    fun <T> getAppPreference(key: String): T {
        return sharedPref.getPreference(key)
    }
}
