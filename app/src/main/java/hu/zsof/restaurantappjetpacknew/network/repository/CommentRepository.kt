package hu.zsof.restaurantappjetpacknew.network.repository

import hu.zsof.restaurantappjetpacknew.model.Comment
import hu.zsof.restaurantappjetpacknew.network.ApiService
import hu.zsof.restaurantappjetpacknew.network.request.CommentDataRequest
import javax.inject.Inject

class CommentRepository @Inject constructor(private val apiService: ApiService) {
    suspend fun getCommentsById(placeId: Long): List<Comment> {
        return try {
            apiService.getCommentsByPlaceId(placeId)
        } catch (e: Exception) {
            e.printStackTrace()
            mutableListOf()
        }
    }

    suspend fun addNewComment(
        commentDataRequest: CommentDataRequest
    ): Comment? {
        return try {
            apiService.addNewComment(commentDataRequest)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun deleteComment(id: Long) {
        try {
            apiService.deleteComment(id)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
