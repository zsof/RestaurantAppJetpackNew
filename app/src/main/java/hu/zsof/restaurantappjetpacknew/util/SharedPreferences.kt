package hu.zsof.restaurantappjetpacknew.util

import android.content.Context
import androidx.core.content.edit
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SharedPreferences @Inject constructor(@ApplicationContext context: Context) {

    private val sharedPreferences =
        context.getSharedPreferences(Constants.Prefs.SHARED_PREFERENCES, Context.MODE_PRIVATE)

    @Suppress("UNCHECKED_CAST")
    fun <T> getPreference(key: String, defaultValue: T? = null): T {
        return when {
            booleanPreferences.contains(key) -> defaultValue ?: sharedPreferences.getBoolean(
                key,
                false,
            ) as T

            else -> defaultValue ?: sharedPreferences.getString(key, "") as T
        }
    }

    fun <T> setPreference(key: String, value: T) {
        sharedPreferences.edit {
            when {
                booleanPreferences.contains(key) -> putBoolean(key, value as Boolean)
                else -> putString(key, value as String)
            }
            commit()
        }
    }

    companion object {

        private val booleanPreferences = arrayOf(
            Constants.Prefs.DARK_MODE,
            Constants.Prefs.USER_RATED,
            Constants.Prefs.USER_LOGGED,
        )
    }
}
