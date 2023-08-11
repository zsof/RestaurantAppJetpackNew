package hu.zsof.restaurantappjetpacknew.ui.auth.login

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.zsof.restaurantappjetpacknew.network.repository.AuthRepository
import hu.zsof.restaurantappjetpacknew.network.request.LoginDataRequest
import hu.zsof.restaurantappjetpacknew.util.Constants
import kotlinx.coroutines.runBlocking
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val authRepository: AuthRepository,
) : ViewModel() {

    var email = mutableStateOf("test@test.hu")
    var isEmailError = mutableStateOf(false)

    var password = mutableStateOf("Alma1234")
    var isPasswordVisible = mutableStateOf(false)
    var isPasswordError = mutableStateOf(false)

    suspend fun login() = runBlocking {
        return@runBlocking authRepository.loginUser(
            LoginDataRequest(
                email.value,
                password.value,
            ),
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
        val pattern = Pattern.compile(Constants.PASSWORD_PATTERN)

        if (pattern.matcher(password.value).matches()) {
            isPasswordError.value = password.value.isEmpty()
        } else {
            isPasswordError.value = false
        }
    }

/* fun <T> setAppPreference(key: String, value: T) {
     sharedPref.setPreference(key, value)
 }

 fun <T> getAppPreference(key: String): T {
     return sharedPref.getPreference(key)
 }*/
}
