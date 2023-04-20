package hu.zsof.restaurantappjetpacknew.network.repository

import hu.zsof.restaurantappjetpacknew.model.User
import hu.zsof.restaurantappjetpacknew.network.ApiService
import javax.inject.Inject

class AdminRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun getUserById(userId: Long): User? {
        return try {
            apiService.getUserById(userId)
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    suspend fun getAllUser(): List<User> {
        return try {
            apiService.getAllUser()
        } catch (e: Exception) {
            e.printStackTrace()
            mutableListOf()
        }
    }

    suspend fun deleteUserById(userId: Long) {
        try {
            apiService.deleteUserById(userId)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}