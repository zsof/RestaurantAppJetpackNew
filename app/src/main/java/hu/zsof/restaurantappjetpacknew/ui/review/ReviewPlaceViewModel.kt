package hu.zsof.restaurantappjetpacknew.ui.review

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.zsof.restaurantappjetpacknew.model.PlaceInReview
import hu.zsof.restaurantappjetpacknew.model.User
import hu.zsof.restaurantappjetpacknew.network.repository.PlaceInReviewRepository
import hu.zsof.restaurantappjetpacknew.network.repository.UserRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewPlaceViewModel @Inject constructor(
    private val placeInReviewRepository: PlaceInReviewRepository,
    private val userRepository: UserRepository,
) :
    ViewModel() {
    // val places = mutableListOf<Place>()

    var placesInReview = MutableLiveData<List<PlaceInReview>>()
    fun showPlacesInReview() {
        viewModelScope.launch {
            placesInReview.postValue(placeInReviewRepository.getAllPlaceFromInReview())
        }
    }

    suspend fun addOrRemoveFavPlace(placeId: Long): User? {
        return userRepository.addOrRemoveFavPlace(placeId)
    }
}
