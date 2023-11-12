package hu.zsof.restaurantappjetpacknew.ui.auth.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.zsof.restaurantappjetpacknew.network.repository.AuthRepository
import hu.zsof.restaurantappjetpacknew.network.request.LoginDataRequest
import hu.zsof.restaurantappjetpacknew.network.response.LoggedUserResponse
import hu.zsof.restaurantappjetpacknew.util.Constants
import hu.zsof.restaurantappjetpacknew.util.SharedPreferences
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val sharedPref: SharedPreferences,
) : ViewModel() {

    val email = mutableStateOf("")
    val isEmailError = mutableStateOf(false)

    val password = mutableStateOf("")
    val isPasswordVisible = mutableStateOf(false)
    val isPasswordError = mutableStateOf(false)

    suspend fun login(): LoggedUserResponse {
        validateEmail()
        validatePassword()

        return if (isEmailError.value.not() && isPasswordError.value.not())
            authRepository.loginUser(
                LoginDataRequest(
                    email = email.value,
                    password = password.value,
                ),
            )
        else LoggedUserResponse(
            isSuccess = false,
            error = "Belépés sikertelen! Ellenőrizze adatait!"
        )
    }

    fun validateEmail() {
        val pattern = Pattern.compile(Constants.EMAIL_PATTERN, Pattern.CASE_INSENSITIVE)

        if (email.value.isEmpty()) {
            isEmailError.value = true
        } else isEmailError.value = pattern.matcher(email.value).matches().not()
    }

    fun validatePassword() {
        val pattern = Pattern.compile(Constants.PASSWORD_PATTERN)

        if (password.value.isEmpty()) {
            isPasswordError.value = true
        } else isPasswordError.value = pattern.matcher(password.value).matches().not()
    }

    fun <T> setAppPreference(key: String, value: T) {
        sharedPref.setPreference(key, value)
    }

    fun <T> getAppPreference(key: String): T {
        return sharedPref.getPreference(key)
    }
}
