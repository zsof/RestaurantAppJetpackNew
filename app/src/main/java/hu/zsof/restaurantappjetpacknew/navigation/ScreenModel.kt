package hu.zsof.restaurantappjetpacknew.navigation

import androidx.annotation.StringRes
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.icons.outlined.ExitToApp
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Place
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

        object OwnerPlace : NavigationScreen(
            R.string.own_places,
            Icons.Outlined.Edit,
            route = "ownerPlace",
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

        object OwnerDetails :
            NavigationScreen(route = "ownerDetails/{${Args.placeId}}") {
            fun passPlaceId(placeId: Long) = "ownerDetails/$placeId"

            object Args {
                const val placeId = "placeId"
            }
        }

        object EditPlace :
            NavigationScreen(route = "editPlace/{${Args.placeId}}") {
            fun passPlaceId(placeId: Long) = "editPlace/$placeId"

            object Args {
                const val placeId = "placeId"
            }
        }
    }

    val userScreensNavigationDrawer = listOf(
        NavigationScreen.Profile,
        NavigationScreen.Logout,
    )
    val screensNavigationDrawer = listOf(
        NavigationScreen.FavPlace,
        NavigationScreen.Profile,
        NavigationScreen.Logout,
    )

    val adminBottomNavItems = listOf(
        NavigationScreen.Home,
        NavigationScreen.Map,
        NavigationScreen.ReviewPlace,
    )

    val ownerBottomNavItems = listOf(
        NavigationScreen.Home,
        NavigationScreen.Map,
        NavigationScreen.OwnerPlace,
    )

    val userBottomNavItems = listOf(
        NavigationScreen.Home,
        NavigationScreen.Map,
        NavigationScreen.FavPlace,
    )
}
