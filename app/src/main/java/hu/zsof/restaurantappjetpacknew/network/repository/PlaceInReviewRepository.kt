package hu.zsof.restaurantappjetpacknew.network.repository

import hu.zsof.restaurantappjetpacknew.model.Place
import hu.zsof.restaurantappjetpacknew.model.PlaceInReview
import hu.zsof.restaurantappjetpacknew.network.ApiService
import hu.zsof.restaurantappjetpacknew.util.recordErrorToFirebase
import javax.inject.Inject

class PlaceInReviewRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getAllPlaceFromInReview(): List<PlaceInReview> {
        return try {
            apiService.getAllPlaceFromInReview()
        } catch (e: Exception) {
            recordErrorToFirebase(e)
            mutableListOf()
        }
    }

    suspend fun getAllModifiedPlace(): List<Place> {
        return try {
            apiService.getAllModifiedPlace()
        } catch (e: Exception) {
            recordErrorToFirebase(e)
            mutableListOf()
        }
    }

    suspend fun getPlaceByIdFromInReview(placeId: Long): PlaceInReview? {
        return try {
            apiService.getPlaceByIdFromInReview(placeId)
        } catch (e: Exception) {
            recordErrorToFirebase(e)
            null
        }
    }

    suspend fun acceptPlaceFromInReview(placeId: Long, isModifiedPlace: Boolean) {
        try {
            apiService.acceptPlaceFromInReview(placeId, isModifiedPlace)
        } catch (e: Exception) {
            recordErrorToFirebase(e)
        }
    }

    suspend fun reportProblemPlaceInReview(
        placeId: Long,
        problemMessage: String,
        isModifiedPlace: Boolean,
    ) {
        try {
            apiService.reportProblemPlaceInReview(placeId, problemMessage, isModifiedPlace)
        } catch (e: Exception) {
            recordErrorToFirebase(e)
        }
    }

    suspend fun deletePlaceFromInReview(placeId: Long) {
        try {
            apiService.deletePlaceFromInReview(placeId)
        } catch (e: Exception) {
            recordErrorToFirebase(e)
        }
    }
}
