package hu.zsof.restaurantappjetpacknew.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.ui.graphics.vector.ImageVector
import hu.zsof.restaurantappjetpacknew.R

class ScreenModel {
    sealed class NavigationScreen(
        @StringRes val title: Int = 0,
        val icon: ImageVector? = null,
        var route: String,
    ) {

        /**
         * BottomNavBar items
         */
        object Home :
            NavigationScreen(title = R.string.home, icon = Icons.Outlined.Home, route = "home")

        object Profile : NavigationScreen(
            R.string.profile,
            Icons.Outlined.Person,
            route = "profile",
        )

        object Map : NavigationScreen(R.string.map, Icons.Outlined.Place, route = "map")
        object FavPlace : NavigationScreen(
            R.string.favs,
            Icons.Outlined.Favorite,
            route = "favPlace",
        )

        object ReviewPlace : NavigationScreen(
            R.string.review,
            Icons.Outlined.Edit,
            route = "reviewPlace",
        )

        object Logout : NavigationScreen(
            R.string.logout,
            Icons.Outlined.ExitToApp,
            route = "logout",
        )

        object Extra : NavigationScreen(
            R.string.extra,
            Icons.Outlined.Menu,
            route = "extra",
        )

        /**
         * Other navigation items
         */

        object Login : NavigationScreen(route = "login")
        object Register : NavigationScreen(route = "register")
        object NewPlace : NavigationScreen(route = "newPlace")
        object FilterPlace : NavigationScreen(route = "filterPlace")

        object Details : NavigationScreen(route = "details/{${Args.placeId}}") {
            fun passPlaceId(placeId: Long) = "details/$placeId"

            object Args {
                const val placeId = "placeId"
            }
        }

        object ReviewDetails :
            NavigationScreen(route = "reviewDetails/{${Args.placeId}}") {
            fun passPlaceId(placeId: Long) = "reviewDetails/$placeId"

            object Args {
                const val placeId = "placeId"
            }
        }
    }

    val screensNavigationDrawer = listOf(
        NavigationScreen.Logout,
        NavigationScreen.Profile,
    )

    val adminBottomNavItems = listOf(
        NavigationScreen.Home,
        NavigationScreen.Map,
        NavigationScreen.ReviewPlace,
    )

    val userBottomNavItems = listOf(
        NavigationScreen.Home,
        NavigationScreen.Map,
        NavigationScreen.FavPlace,
    )
}
