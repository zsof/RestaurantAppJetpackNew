package hu.zsof.restaurantappjetpacknew.network.repository

import hu.zsof.restaurantappjetpacknew.model.Place
import hu.zsof.restaurantappjetpacknew.model.PlaceInReview
import hu.zsof.restaurantappjetpacknew.network.ApiService
import hu.zsof.restaurantappjetpacknew.network.request.PlaceDataRequest
import hu.zsof.restaurantappjetpacknew.util.recordErrorToFirebase
import javax.inject.Inject

class PlaceOwnerRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun addNewPlace(
        placeDataRequest: PlaceDataRequest,
    ): PlaceInReview? {
        return try {
            apiService.addNewPlace(placeDataRequest)
        } catch (e: Exception) {
            recordErrorToFirebase(e)
            null
        }
    }

    suspend fun deletePlace(placeId: Long) {
        try {
            apiService.deletePlace(placeId)
        } catch (e: Exception) {
            recordErrorToFirebase(e)
        }
    }

    suspend fun getAllPlaceByOwner(): List<Place> {
        return try {
            apiService.getAllPlaceByOwner()
        } catch (e: Exception) {
            recordErrorToFirebase(e)
            mutableListOf()
        }
    }

    suspend fun getAllPlaceInReviewByOwner(): List<PlaceInReview> {
        return try {
            apiService.getAllPlaceInReviewByOwner()
        } catch (e: Exception) {
            recordErrorToFirebase(e)
            mutableListOf()
        }
    }

    suspend fun updatePlace(placeDataRequest: PlaceDataRequest): Place? {
        return try {
            apiService.updatePlace(placeDataRequest)
        } catch (e: Exception) {
            recordErrorToFirebase(e)
            null
        }
    }
}
