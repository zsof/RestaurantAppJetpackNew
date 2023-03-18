package hu.zsof.restaurantappjetpacknew.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector
import hu.zsof.restaurantappjetpacknew.R

enum class NavItem(
    @StringRes val title: Int,
    val icon: ImageVector?,
    var route: String?,
) {
    HOME(title = R.string.home, icon = Icons.Outlined.Home, route = NavigationScreen.Home.route),
    MAP(R.string.map, Icons.Outlined.Place, null),
    FAVOURITES(R.string.favs, Icons.Outlined.Favorite, null),
    PROFILE(R.string.profile, Icons.Outlined.Person, NavigationScreen.Profile.route),
    LOGOUT(R.string.logout, Icons.Outlined.ExitToApp, NavigationScreen.Login.route),
   /* LOGIN(R.string.log_in, null, null),
    REGISTER(R.string.logout, null, null),*/
}
