package hu.zsof.restaurantappjetpacknew.ui.map

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.zsof.restaurantappjetpacknew.network.repository.PlaceRepository
import hu.zsof.restaurantappjetpacknew.network.response.PlaceMapResponse
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapViewModel @Inject constructor(private val placeRepository: PlaceRepository) :
    ViewModel() {

    var places = MutableLiveData<List<PlaceMapResponse>>()
    fun requestPlaceData() {
        viewModelScope.launch {
            places.postValue(placeRepository.getAllPlaceInMap())
        }
    }
}
