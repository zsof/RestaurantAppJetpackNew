package hu.zsof.restaurantappjetpacknew.ui.details

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.zsof.restaurantappjetpacknew.model.Comment
import hu.zsof.restaurantappjetpacknew.model.Place
import hu.zsof.restaurantappjetpacknew.network.repository.CommentRepository
import hu.zsof.restaurantappjetpacknew.network.repository.PlaceRepository
import hu.zsof.restaurantappjetpacknew.network.request.CommentDataRequest
import hu.zsof.restaurantappjetpacknew.util.SharedPreferences
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val placeRepository: PlaceRepository,
    private val commentRepository: CommentRepository,
    private val sharedPref: SharedPreferences,
) :
    ViewModel() {
    val openingHoursOpenDetails = mutableStateOf(false)

    val isPlaceByOwner = mutableStateOf(false)
    val newComment = mutableStateOf("")

    val comments = MutableLiveData<List<Comment>>()
    fun getCommentsByPlaceId(placeId: Long) {
        viewModelScope.launch {
            comments.postValue(commentRepository.getCommentsById(placeId))
        }
    }

    fun addComment(placeId: Long) {
        viewModelScope.launch {
            commentRepository.addNewComment(CommentDataRequest(placeId, newComment.value))
            comments.postValue(commentRepository.getCommentsById(placeId))
        }
    }

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
