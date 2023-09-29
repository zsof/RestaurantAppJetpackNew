package hu.zsof.restaurantappjetpacknew.ui.review

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.zsof.restaurantappjetpacknew.model.BasePlace
import hu.zsof.restaurantappjetpacknew.model.Place
import hu.zsof.restaurantappjetpacknew.model.PlaceInReview
import hu.zsof.restaurantappjetpacknew.module.AppState
import hu.zsof.restaurantappjetpacknew.network.repository.PlaceInReviewRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewPlaceViewModel @Inject constructor(
    private val placeInReviewRepository: PlaceInReviewRepository,
) :
    ViewModel() {

    val problemMessage = mutableStateOf("")
    val problemMessageError = mutableStateOf(false)
    val problemReportDialogOpen = mutableStateOf(false)
    val showProblemDialog = mutableStateOf(false)
    val multiFloatingState = mutableStateOf(MultiFloatingState.COLLAPSED)
    val openingHoursOpenDetails = mutableStateOf(false)


    val placesInReview = MutableLiveData<List<PlaceInReview>>()
    fun showPlacesInReview() {
        viewModelScope.launch {
            placesInReview.postValue(placeInReviewRepository.getAllPlaceFromInReview())
        }
    }

    val modifiedPlaces = MutableLiveData<List<Place>>()

    fun showModifiedPlaces() {
        viewModelScope.launch {
            modifiedPlaces.postValue(placeInReviewRepository.getAllModifiedPlace())
        }
    }

    val reviewPlaceById = MutableLiveData<BasePlace>()

    fun getReviewPlaceById(placeId: Long) {
        viewModelScope.launch {
            val isPlaceModified =
                placeInReviewRepository.getAllModifiedPlace().any { it.id == placeId }
            if (isPlaceModified) {
                reviewPlaceById.postValue(
                    placeInReviewRepository.getAllModifiedPlace().find { it.id == placeId })

                //To save for floating button if is place or place in review in acceptPlace
                AppState.isModifiedPlace.value = true
            } else {
                reviewPlaceById.postValue(placeInReviewRepository.getPlaceByIdFromInReview(placeId))

                AppState.isModifiedPlace.value = false
            }
        }
    }

    val isPlaceAccepted = MutableLiveData(false)

    fun acceptPlace(placeId: Long, isModifiedPlace: Boolean) {
        viewModelScope.launch {
            placeInReviewRepository.acceptPlaceFromInReview(placeId, isModifiedPlace)
            isPlaceAccepted.value = true
        }
    }

    val isPlaceReported = MutableLiveData(false)
    fun reportProblem(placeId: Long, isModifiedPlace: Boolean) {
        viewModelScope.launch {
            placeInReviewRepository.reportProblemPlaceInReview(
                placeId,
                problemMessage.value,
                isModifiedPlace,
            )
            isPlaceReported.value = true
        }
    }
}
