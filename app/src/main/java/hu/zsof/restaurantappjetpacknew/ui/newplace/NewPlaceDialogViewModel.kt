package hu.zsof.restaurantappjetpacknew.ui.newplace

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.zsof.restaurantappjetpacknew.network.repository.PlaceOwnerRepository
import hu.zsof.restaurantappjetpacknew.network.repository.ResourceRepository
import hu.zsof.restaurantappjetpacknew.network.request.PlaceDataRequest
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewPlaceDialogViewModel @Inject constructor(
    private val placeOwnerRepository: PlaceOwnerRepository,
    private val resourceRepository: ResourceRepository,
) : ViewModel() {

    fun addNewPlace(
        placeDataRequest: PlaceDataRequest,
        image: String,
    ) {
        try {
            // viewmodel scope megszűnik, amint bezáródik a dialog, ezért kell coroutinescope
            // todo valami szebb megoldás

            CoroutineScope(Job() + Dispatchers.IO).launch {
                val placeResponse = placeOwnerRepository.addNewPlace(placeDataRequest)

                if (placeResponse != null) {
                    resourceRepository.addNewImage(
                        filePath = image,
                        type = "place",
                        typeId = placeResponse.id,
                    )
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
