package hu.zsof.restaurantappjetpacknew.ui.owner

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.zsof.restaurantappjetpacknew.model.Place
import hu.zsof.restaurantappjetpacknew.model.PlaceInReview
import hu.zsof.restaurantappjetpacknew.network.repository.PlaceInReviewRepository
import hu.zsof.restaurantappjetpacknew.network.repository.PlaceOwnerRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OwnerPlaceViewModel @Inject constructor(
    private val placeOwnerRepository: PlaceOwnerRepository,
    private val placeInReviewRepository: PlaceInReviewRepository,
) :
    ViewModel() {

    val showProblemDialog = mutableStateOf(false)
    val isPlaceByOwner = mutableStateOf(false)
    val isPlaceInReviewByOwner = mutableStateOf(false)

    val openingHoursOpenDetails = mutableStateOf(false)

    fun deletePlace(placeId: Long) {
        viewModelScope.launch {
            placeOwnerRepository.deletePlace(placeId)
        }
    }

    fun deletePlaceInReview(placeId: Long) {
        viewModelScope.launch {
            placeInReviewRepository.deletePlaceFromInReview(placeId)
        }
    }

    var ownerPlaces = MutableLiveData<List<Place>>()

    fun showPlaces() {
        viewModelScope.launch {
            ownerPlaces.postValue(placeOwnerRepository.getAllPlaceByOwner())
        }
    }

    var ownerPlacesInReview = MutableLiveData<List<PlaceInReview>>()

    fun showPlacesInReview() {
        viewModelScope.launch {
            ownerPlacesInReview.postValue(placeOwnerRepository.getAllPlaceInReviewByOwner())
        }
    }

    val reviewPlaceById = MutableLiveData<PlaceInReview>()
    fun getReviewPlaceById(placeId: Long) {
        viewModelScope.launch {
            reviewPlaceById.postValue(placeInReviewRepository.getPlaceByIdFromInReview(placeId))
        }
    }
}
