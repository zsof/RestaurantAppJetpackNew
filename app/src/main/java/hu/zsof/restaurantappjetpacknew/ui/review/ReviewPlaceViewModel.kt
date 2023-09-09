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

    //TODO módosított helyre kattintva a place in review.ban keres, félig megoldva, má id átadva nav graph-ban!!
    var problemMessage = mutableStateOf("")
    var problemMessageError = mutableStateOf(false)
    val problemReportDialogOpen = mutableStateOf(false)
    val showProblemDialog = mutableStateOf(false)
    var multiFloatingState = mutableStateOf(MultiFloatingState.COLLAPSED)

    var placesInReview = MutableLiveData<List<PlaceInReview>>()
    fun showPlacesInReview() {
        viewModelScope.launch {
            placesInReview.postValue(placeInReviewRepository.getAllPlaceFromInReview())
        }
    }

    var modifiedPlaces = MutableLiveData<List<Place>>()

    fun showModifiedPlaces() {
        viewModelScope.launch {
            modifiedPlaces.postValue(placeInReviewRepository.getAllModifiedPlace())
        }
    }

    val reviewPlaceById = MutableLiveData<BasePlace>()

    fun getReviewPlaceById(placeId: Long) {
        viewModelScope.launch {
            if (AppState.isModifiedPlace) {
                reviewPlaceById.postValue(
                    placeInReviewRepository.getAllModifiedPlace().find { it.id == placeId })
            } else {
                reviewPlaceById.postValue(placeInReviewRepository.getPlaceByIdFromInReview(placeId))
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
