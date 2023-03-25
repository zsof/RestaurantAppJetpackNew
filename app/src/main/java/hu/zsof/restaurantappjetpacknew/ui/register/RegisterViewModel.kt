package hu.zsof.restaurantappjetpacknew.ui.register

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.zsof.restaurantappjetpacknew.network.repository.AuthRepository
import hu.zsof.restaurantappjetpacknew.network.request.LoginDataRequest
import hu.zsof.restaurantappjetpacknew.network.response.LoggedUserResponse
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    // private val sharedPref: SharedPreference,
) : ViewModel() {

    suspend fun login(loginDataRequest: LoginDataRequest): LoggedUserResponse {
        return authRepository.loginUser(loginDataRequest)
    }

  /*  fun <T> setAppPreference(key: String, value: T) {
        sharedPref.setPreference(key, value)
    }

    fun <T> getAppPreference(key: String): T {
        return sharedPref.getPreference(key)
    }*/
}
