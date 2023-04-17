package hu.zsof.restaurantappjetpacknew.ui.favorite

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.zsof.restaurantappjetpacknew.model.Place
import hu.zsof.restaurantappjetpacknew.network.repository.UserRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val userRepository: UserRepository,
) :
    ViewModel() {

    var favPlaces = MutableLiveData<List<Place>>()
    fun showFavPlaces() {
        viewModelScope.launch {
            favPlaces.postValue(userRepository.getFavPlacesByUser())
        }
    }
}
