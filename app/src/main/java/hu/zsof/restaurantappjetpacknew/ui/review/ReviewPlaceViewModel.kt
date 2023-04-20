package hu.zsof.restaurantappjetpacknew.ui.review

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.zsof.restaurantappjetpacknew.model.PlaceInReview
import hu.zsof.restaurantappjetpacknew.network.repository.PlaceInReviewRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewPlaceViewModel @Inject constructor(
    private val placeInReviewRepository: PlaceInReviewRepository,
) :
    ViewModel() {

    var placesInReview = MutableLiveData<List<PlaceInReview>>()
    fun showPlacesInReview() {
        viewModelScope.launch {
            placesInReview.postValue(placeInReviewRepository.getAllPlaceFromInReview())
        }
    }

    val reviewPlaceById = MutableLiveData<PlaceInReview>()
    fun getReviewPlaceById(placeId: Long) {
        viewModelScope.launch {
            reviewPlaceById.postValue(placeInReviewRepository.getPlaceByIdFromInReview(placeId))
        }
    }

    val isPlaceAccepted = MutableLiveData(false)

    fun acceptPlace(placeId: Long) {
        viewModelScope.launch {
            placeInReviewRepository.acceptPlaceFromInReview(placeId)
            isPlaceAccepted.value = true

            println("viewmodel ${isPlaceAccepted.value}")
        }
    }
    fun reportProblem(placeId: Long, problemMessage: String) {
        viewModelScope.launch {
            placeInReviewRepository.reportProblemPlaceInReview(placeId, problemMessage)
        }
    }
}
