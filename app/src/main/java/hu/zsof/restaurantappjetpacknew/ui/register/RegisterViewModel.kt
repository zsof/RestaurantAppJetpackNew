package hu.zsof.restaurantappjetpacknew.ui.register

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.zsof.restaurantappjetpacknew.network.repository.AuthRepository
import hu.zsof.restaurantappjetpacknew.network.request.LoginDataRequest
import hu.zsof.restaurantappjetpacknew.network.response.NetworkResponse
import hu.zsof.restaurantappjetpacknew.util.Constants
import hu.zsof.restaurantappjetpacknew.util.Constants.PASSWORD_PATTERN
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository,

) : ViewModel() {

    var email = mutableStateOf("")
    var isEmailError = mutableStateOf(false)

    var userName = mutableStateOf("")
    var isUserNameError = mutableStateOf(false)

    var nickName = mutableStateOf("")

    var password = mutableStateOf("")
    var isPasswordVisible = mutableStateOf(false)
    var isPasswordError = mutableStateOf(false)
    suspend fun register(): NetworkResponse {
        return authRepository.registerUser(
            LoginDataRequest(
                email.value,
                password.value,
                userName.value,
                nickName.value,
            ),
            isAdmin = false,
        )
    }

    fun validateEmail() {
        val pattern = Pattern.compile(Constants.EMAIL_PATTERN, Pattern.CASE_INSENSITIVE)

        if (pattern.matcher(email.value).matches()) {
            isEmailError.value = email.value.isEmpty()
        } else {
            isEmailError.value = false
        }
    }

    fun validatePassword() {
        val pattern = Pattern.compile(PASSWORD_PATTERN)

        if (pattern.matcher(password.value).matches()) {
            isPasswordError.value = password.value.isEmpty()
        } else {
            isPasswordError.value = false
        }
    }

    fun validateUserName() {
        isUserNameError.value = userName.value.isEmpty()
    }
}
