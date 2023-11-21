package hu.zsof.restaurantappjetpacknew

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import hu.zsof.restaurantappjetpacknew.module.AppState
import hu.zsof.restaurantappjetpacknew.network.repository.AuthRepository
import hu.zsof.restaurantappjetpacknew.util.Constants
import hu.zsof.restaurantappjetpacknew.util.SharedPreferences
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    @ApplicationContext private val context: Context,
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
        val sharedPreferences =
            context.getSharedPreferences(
                Constants.Prefs.AUTH_SHARED_PREFERENCES,
                Context.MODE_PRIVATE,
            )
        val token = sharedPreferences.getString("bearer", "")
        if (token.isNullOrEmpty().not()) {
            viewModelScope.launch {
                val loggedUser = authRepository.authUser()
                user.postValue(loggedUser.user)
            }
        }
    }
}
