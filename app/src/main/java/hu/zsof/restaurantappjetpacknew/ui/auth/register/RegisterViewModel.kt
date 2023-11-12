package hu.zsof.restaurantappjetpacknew.ui.auth.register

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.zsof.restaurantappjetpacknew.network.repository.AuthRepository
import hu.zsof.restaurantappjetpacknew.network.request.LoginDataRequest
import hu.zsof.restaurantappjetpacknew.network.response.NetworkResponse
import hu.zsof.restaurantappjetpacknew.util.Constants
import hu.zsof.restaurantappjetpacknew.util.Constants.PASSWORD_PATTERN
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val authRepository: AuthRepository,

    ) : ViewModel() {

    private val _uiState = MutableStateFlow(RegisterUiState())
    val uiState: StateFlow<RegisterUiState> = _uiState.asStateFlow()
    fun setEmail(changedValue: String) {
        _uiState.update {
            it.copy(email = changedValue)
        }
    }

    fun setName(changedValue: String) {
        _uiState.update {
            it.copy(userName = changedValue)
        }
    }

    fun setPassword(changedValue: String) {
        _uiState.update {
            it.copy(password = changedValue)
        }
    }

    fun setIsOwner(changedValue: Boolean) {
        _uiState.update {
            it.copy(isOwner = changedValue)
        }
    }

    fun setPasswordVisibility(changedValue: Boolean) {
        _uiState.update {
            it.copy(isPasswordVisible = changedValue.not())
        }
    }

    suspend fun register(): NetworkResponse {
        validateUserName()
        validateEmail()
        validatePassword()

        return if (_uiState.value.isUserNameError.not()
            && _uiState.value.isEmailError.not()
            && _uiState.value.isPasswordError.not()
        )
            authRepository.registerUser(
                LoginDataRequest(
                    _uiState.value.email,
                    _uiState.value.password,
                    _uiState.value.userName,
                ),
                isOwner = _uiState.value.isOwner,
            )
        else NetworkResponse(
            isSuccess = false,
            error = "Regisztráció sikertelen! Ellenőrizze adatait!"
        )
    }

    fun validateEmail() {
        val pattern = Pattern.compile(Constants.EMAIL_PATTERN, Pattern.CASE_INSENSITIVE)

        if (_uiState.value.email.isEmpty()) {
            _uiState.update {
                it.copy(isEmailError = true)
            }
        } else {
            _uiState.update {
                it.copy(isEmailError = pattern.matcher(_uiState.value.email).matches().not())
            }
        }
    }

    fun validatePassword() {
        val pattern = Pattern.compile(PASSWORD_PATTERN)

        if (_uiState.value.password.isEmpty()) {
            _uiState.update {
                it.copy(isPasswordError = true)
            }
        } else {
            _uiState.update {
                it.copy(isPasswordError = pattern.matcher(_uiState.value.password).matches().not())
            }
        }
    }

    fun validateUserName() {
        _uiState.update {
            it.copy(isUserNameError = it.userName.isEmpty())
        }
    }
}
