package hu.zsof.restaurantappjetpacknew.network.repository

import android.util.Base64
import hu.zsof.restaurantappjetpacknew.network.ApiService
import hu.zsof.restaurantappjetpacknew.network.request.LoginDataRequest
import hu.zsof.restaurantappjetpacknew.network.response.LoggedUserResponse
import hu.zsof.restaurantappjetpacknew.network.response.NetworkResponse
import javax.inject.Inject

class AuthRepository @Inject constructor(private val apiService: ApiService) {

    suspend fun registerUser(loginDataRequest: LoginDataRequest, isAdmin: Boolean, isOwner: Boolean): NetworkResponse {
        return try {
            apiService.registerUser(loginDataRequest, isAdmin, isOwner)
        } catch (e: Exception) {
            e.printStackTrace()
            NetworkResponse(false, e.localizedMessage ?: "Network error")
        }
    }

    // @RequiresApi(Build.VERSION_CODES.O)
    suspend fun loginUser(loginDataRequest: LoginDataRequest): LoggedUserResponse {
        return try {
            val simpleData = "${loginDataRequest.email}:${loginDataRequest.password}"
            val encodedData = Base64.encodeToString(simpleData.toByteArray(), Base64.NO_WRAP)
            apiService.loginUser("Basic $encodedData")
        } catch (e: Exception) {
            e.printStackTrace()
            LoggedUserResponse(false, e.localizedMessage ?: "Network error")
        }
    }

    // todo authenticate user
}
