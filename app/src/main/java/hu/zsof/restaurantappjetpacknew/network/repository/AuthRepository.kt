package hu.zsof.restaurantappjetpacknew.network.repository

import hu.zsof.restaurantappjetpacknew.network.ApiService
import hu.zsof.restaurantappjetpacknew.network.request.LoginDataRequest
import hu.zsof.restaurantappjetpacknew.network.response.LoggedUserResponse
import hu.zsof.restaurantappjetpacknew.network.response.NetworkResponse
import javax.inject.Inject

class AuthRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun registerUser(loginDataRequest: LoginDataRequest): NetworkResponse {
        return try {
            apiService.registerUser(loginDataRequest)
        } catch (e: Exception) {
            e.printStackTrace()
            NetworkResponse(false, e.localizedMessage ?: "Network error")
        }
    }

    suspend fun loginUser(loginDataRequest: LoginDataRequest): LoggedUserResponse {
        return try {
            apiService.loginUser(loginDataRequest)
        } catch (e: Exception) {
            e.printStackTrace()
            LoggedUserResponse(false, e.localizedMessage ?: "Network error")
        }
    }
}
