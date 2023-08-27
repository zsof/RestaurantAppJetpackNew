package hu.zsof.restaurantappjetpacknew.ui.owner

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.zsof.restaurantappjetpacknew.model.Place
import hu.zsof.restaurantappjetpacknew.model.PlaceInReview
import hu.zsof.restaurantappjetpacknew.network.repository.PlaceInReviewRepository
import hu.zsof.restaurantappjetpacknew.network.repository.PlaceOwnerRepository
import hu.zsof.restaurantappjetpacknew.ui.common.screen.CommonPlaceDialogViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OwnerPlaceViewModel @Inject constructor(
    private val ownerRepository: PlaceOwnerRepository,
    private val placeInReviewRepository: PlaceInReviewRepository,
) :
    CommonPlaceDialogViewModel() {

    val showProblemDialog = mutableStateOf(false)

    fun deletePlace(placeId: Long) {
        viewModelScope.launch {
            ownerRepository.deletePlace(placeId)
        }
    }

    var ownerPlaces = MutableLiveData<List<Place>>()

    fun showPlaces() {
        viewModelScope.launch {
            ownerPlaces.postValue(ownerRepository.getAllPlaceByOwner())
        }
    }

    var ownerPlacesInReview = MutableLiveData<List<PlaceInReview>>()

    fun showPlacesInReview() {
        viewModelScope.launch {
            ownerPlacesInReview.postValue(ownerRepository.getAllPlaceInReviewByOwner())
        }
    }

    val reviewPlaceById = MutableLiveData<PlaceInReview>()
    fun getReviewPlaceById(placeId: Long) {
        viewModelScope.launch {
            reviewPlaceById.postValue(placeInReviewRepository.getPlaceByIdFromInReview(placeId))
        }
    }
}
