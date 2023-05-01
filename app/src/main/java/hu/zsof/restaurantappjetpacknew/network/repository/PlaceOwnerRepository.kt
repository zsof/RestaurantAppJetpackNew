package hu.zsof.restaurantappjetpacknew.network.repository

import hu.zsof.restaurantappjetpacknew.model.Place
import hu.zsof.restaurantappjetpacknew.model.PlaceInReview
import hu.zsof.restaurantappjetpacknew.network.ApiService
import hu.zsof.restaurantappjetpacknew.network.request.PlaceDataRequest
import javax.inject.Inject

class PlaceOwnerRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun addNewPlace(
        placeDataRequest: PlaceDataRequest,
    ): PlaceInReview? {
        return try {
            apiService.addNewPlace(placeDataRequest)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun deletePlace(placeId: Long) {
        try {
            apiService.deletePlace(placeId)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    suspend fun getAllPlaceByOwner(): List<Place> {
        return try {
            apiService.getAllPlaceByOwner()
        } catch (e: Exception) {
            e.printStackTrace()
            mutableListOf()
        }
    }

    suspend fun getAllPlaceInReviewByOwner(): List<PlaceInReview> {
        return try {
            apiService.getAllPlaceInReviewByOwner()
        } catch (e: Exception) {
            e.printStackTrace()
            mutableListOf()
        }
    }
}
