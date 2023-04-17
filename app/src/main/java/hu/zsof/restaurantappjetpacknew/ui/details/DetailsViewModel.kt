package hu.zsof.restaurantappjetpacknew.ui.details

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.zsof.restaurantappjetpacknew.model.Place
import hu.zsof.restaurantappjetpacknew.network.repository.PlaceRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val placeRepository: PlaceRepository) :
    ViewModel() {

    val placeById = MutableLiveData<Place>()

    fun getPlaceById(placeId: Long) {
        viewModelScope.launch {
            placeById.postValue(placeRepository.getPlaceById(placeId))
        }
    }
}
