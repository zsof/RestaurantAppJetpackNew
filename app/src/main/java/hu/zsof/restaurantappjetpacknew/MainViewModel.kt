package hu.zsof.restaurantappjetpacknew

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.zsof.restaurantappjetpacknew.network.repository.AuthRepository
import hu.zsof.restaurantappjetpacknew.module.AppState
import hu.zsof.restaurantappjetpacknew.util.SharedPreferences
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val sharedPref: SharedPreferences,
    private val authRepository: AuthRepository,
) : ViewModel() {
    fun <T> setAppPreference(key: String, value: T) {
        sharedPref.setPreference(key, value)
    }

    fun <T> getAppPreference(key: String): T {
        return sharedPref.getPreference(key)
    }

    val user = AppState.loggedUser

    fun authenticateLoggedUser() {
        viewModelScope.launch {
            val loggedUser = authRepository.authUser()
            AppState.loggedUser.postValue(loggedUser.user)
        }
    }
}
