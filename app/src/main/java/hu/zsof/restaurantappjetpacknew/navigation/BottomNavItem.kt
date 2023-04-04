package hu.zsof.restaurantappjetpacknew.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector
import hu.zsof.restaurantappjetpacknew.R

enum class BottomNavItem(
    @StringRes val title: Int,
    val icon: ImageVector?,
    var route: String?,
    val forAdminIsVisible: Boolean = true,
    val forUserIsVisible: Boolean = true,
) {
    HOME(title = R.string.home, icon = Icons.Outlined.Home, route = NavigationScreen.Home.route),
    MAP(R.string.map, Icons.Outlined.Place, NavigationScreen.Map.route),
    FAVOURITES(
        R.string.favs,
        Icons.Outlined.Favorite,
        NavigationScreen.FavPlace.route,
        forAdminIsVisible = false,
    ),
    PROFILE(
        R.string.profile,
        Icons.Outlined.Person,
        NavigationScreen.Profile.route,
        forAdminIsVisible = false,
    ),
    REVIEW(
        R.string.review,
        Icons.Outlined.Edit,
        NavigationScreen.ReviewPlace.route,
        forUserIsVisible = false,
    ),
    LOGOUT(R.string.logout, Icons.Outlined.ExitToApp, NavigationScreen.Login.route),
}
