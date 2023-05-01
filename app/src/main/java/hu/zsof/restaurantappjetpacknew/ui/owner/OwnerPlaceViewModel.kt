package hu.zsof.restaurantappjetpacknew.ui.owner

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.zsof.restaurantappjetpacknew.model.Place
import hu.zsof.restaurantappjetpacknew.network.repository.PlaceOwnerRepository
import hu.zsof.restaurantappjetpacknew.network.repository.PlaceRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OwnerPlaceViewModel @Inject constructor(
    private val ownerRepository: PlaceOwnerRepository,
    private val placeRepository: PlaceRepository,
) :
    ViewModel() {

    fun deletePlace(placeId: Long) {
        viewModelScope.launch {
            ownerRepository.deletePlace(placeId)
        }
    }

    var ownerPlaces = MutableLiveData<List<Place>>()

    fun showPlaces() {
        viewModelScope.launch {
            ownerPlaces.postValue(placeRepository.getAllPlace())
        }
    }
}
