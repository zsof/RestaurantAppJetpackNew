package hu.zsof.restaurantappjetpacknew

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import hu.zsof.restaurantappjetpacknew.navigation.NavGraph
import hu.zsof.restaurantappjetpacknew.ui.theme.RestaurantAppJetpackNewTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RestaurantAppJetpackNewTheme {
                val navController = rememberNavController()
                NavGraph(navController = navController)
            }
        }
    }
}
