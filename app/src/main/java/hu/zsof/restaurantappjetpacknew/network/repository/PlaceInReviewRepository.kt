package hu.zsof.restaurantappjetpacknew.network.repository

import hu.zsof.restaurantappjetpacknew.model.Place
import hu.zsof.restaurantappjetpacknew.model.PlaceInReview
import hu.zsof.restaurantappjetpacknew.network.ApiService
import javax.inject.Inject

class PlaceInReviewRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getAllPlaceFromInReview(): List<PlaceInReview> {
        return try {
            apiService.getAllPlaceFromInReview()
        } catch (e: Exception) {
            e.printStackTrace()
            mutableListOf()
        }
    }

    suspend fun getAllModifiedPlace(): List<Place> {
        return try {
            apiService.getAllModifiedPlace()
        } catch (e: Exception) {
            e.printStackTrace()
            mutableListOf()
        }
    }

    suspend fun getPlaceByIdFromInReview(placeId: Long): PlaceInReview? {
        return try {
            apiService.getPlaceByIdFromInReview(placeId)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun acceptPlaceFromInReview(placeId: Long, isModifiedPlace: Boolean) {
        try {
            apiService.acceptPlaceFromInReview(placeId, isModifiedPlace)
        } catch (e: Exception) {
            e.printStackTrace()
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
            e.printStackTrace()
        }
    }

    suspend fun deletePlaceFromInReview(placeId: Long) {
        try {
            apiService.deletePlaceFromInReview(placeId)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
