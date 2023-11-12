package hu.zsof.restaurantappjetpacknew.network.repository

import android.content.Context
import android.util.Base64
import dagger.hilt.android.qualifiers.ApplicationContext
import hu.zsof.restaurantappjetpacknew.R
import hu.zsof.restaurantappjetpacknew.network.ApiService
import hu.zsof.restaurantappjetpacknew.network.request.LoginDataRequest
import hu.zsof.restaurantappjetpacknew.network.response.LoggedUserResponse
import hu.zsof.restaurantappjetpacknew.network.response.NetworkResponse
import hu.zsof.restaurantappjetpacknew.util.recordErrorToFirebase
import javax.inject.Inject

class AuthRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val apiService: ApiService
) {

    suspend fun registerUser(
        loginDataRequest: LoginDataRequest,
        isOwner: Boolean,
    ): NetworkResponse {
        return try {
            apiService.registerUser(loginDataRequest, isOwner)
        } catch (e: Exception) {
            recordErrorToFirebase(e)
            NetworkResponse(false, e.localizedMessage ?: context.getString(R.string.network_error))
        }
    }

    suspend fun loginUser(loginDataRequest: LoginDataRequest): LoggedUserResponse {
        return try {
            val simpleData = "${loginDataRequest.email}:${loginDataRequest.password}"
            val encodedData = Base64.encodeToString(simpleData.toByteArray(), Base64.NO_WRAP)
            apiService.loginUser("Basic $encodedData")
        } catch (e: Exception) {
            recordErrorToFirebase(e)
            LoggedUserResponse(
                false,
                e.localizedMessage ?: context.getString(R.string.network_error)
            )
        }
    }

    suspend fun authUser(): LoggedUserResponse {
        return try {
            apiService.authUser()
        } catch (e: Exception) {
            recordErrorToFirebase(e)
            LoggedUserResponse(
                false,
                e.localizedMessage ?: context.getString(R.string.network_error)
            )
        }
    }
}
