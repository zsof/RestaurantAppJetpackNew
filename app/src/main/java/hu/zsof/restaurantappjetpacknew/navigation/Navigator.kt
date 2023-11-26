package hu.zsof.restaurantappjetpacknew.navigation

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import hu.zsof.restaurantappjetpacknew.MainViewModel
import hu.zsof.restaurantappjetpacknew.module.AppState
import hu.zsof.restaurantappjetpacknew.util.Constants
import javax.inject.Inject

class Navigator @Inject constructor() {

    val destination: MutableLiveData<ScreenModel.NavigationScreen> =
        MutableLiveData(ScreenModel.NavigationScreen.Login)

}

fun navigateToItem(
    context: Context,
    item: ScreenModel.NavigationScreen,
    navController: NavController,
    viewModel: MainViewModel,
) {
    val sharedPreferences = context.getSharedPreferences(
        Constants.Prefs.AUTH_SHARED_PREFERENCES,
        Context.MODE_PRIVATE,
    )
    val token = sharedPreferences.getString("bearer", "")

    if (!token.isNullOrEmpty()) {
        item.route.let {
            navController.navigate(it)
        }
        if (item.route == ScreenModel.NavigationScreen.Logout.route) {
            sharedPreferences
                .edit()
                .putString("bearer", "")
                .apply()

            AppState.loggedUser.value = null
            viewModel.setAppPreference(
                Constants.Prefs.USER_LOGGED,
                false,
            )
        }
    } else {
        navController.navigate(ScreenModel.NavigationScreen.Login.route)
        AppState.loggedUser.value = null
        viewModel.setAppPreference(
            Constants.Prefs.USER_LOGGED,
            false,
        )
    }
}