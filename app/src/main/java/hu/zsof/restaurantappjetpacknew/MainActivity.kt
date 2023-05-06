package hu.zsof.restaurantappjetpacknew

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import hu.zsof.restaurantappjetpacknew.navigation.NavGraph
import hu.zsof.restaurantappjetpacknew.network.repository.LocalDataStateService
import hu.zsof.restaurantappjetpacknew.ui.theme.RestaurantAppJetpackNewTheme
import hu.zsof.restaurantappjetpacknew.util.Constants

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RestaurantAppJetpackNewTheme(darkTheme = isNightMode()) {
                val navController = rememberNavController()
                NavGraph(navController = navController)
            }
        }
    }

    @Composable
    private fun isNightMode(): Boolean {
        val darkTheme = LocalDataStateService.darkTheme.observeAsState()

        // Changes the theme when user click
        if (darkTheme.value != null) {
            return if (darkTheme.value == true) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                false
            }
            // Changes the theme at first start
        } else {
            return if (viewModel.getAppPreference(Constants.Prefs.DARK_MODE)) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                false
            }
        }
    }
}
