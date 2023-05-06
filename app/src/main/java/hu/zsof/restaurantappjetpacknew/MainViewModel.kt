package hu.zsof.restaurantappjetpacknew

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.zsof.restaurantappjetpacknew.util.SharedPreferences
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val sharedPref: SharedPreferences) : ViewModel() {
    fun <T> setAppPreference(key: String, value: T) {
        sharedPref.setPreference(key, value)
    }

    fun <T> getAppPreference(key: String): T {
        return sharedPref.getPreference(key)
    }
}
