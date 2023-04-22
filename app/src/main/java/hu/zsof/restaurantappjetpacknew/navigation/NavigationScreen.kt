package hu.zsof.restaurantappjetpacknew.navigation

sealed class NavigationScreen(val route: String) {
    object Login : NavigationScreen(route = "login")
    object Register : NavigationScreen(route = "register")
    object Home : NavigationScreen(route = "home")
    object Profile : NavigationScreen(route = "profile")
    object NewPlace : NavigationScreen(route = "newPlace")
    object FilterPlace : NavigationScreen(route = "filterPlace")
    object Map : NavigationScreen(route = "map")
    object FavPlace : NavigationScreen(route = "favPlace")
    object ReviewPlace : NavigationScreen(route = "reviewPlace")
    object Logout : NavigationScreen(route = "logout")
    object Details : NavigationScreen(route = "details/{${Args.placeId}}") {
        fun passPlaceId(placeId: Long) = "details/$placeId"
        object Args {
            const val placeId = "placeId"
        }
    }
    object ReviewDetails : NavigationScreen(route = "reviewDetails/{${Args.placeId}}") {
        fun passPlaceId(placeId: Long) = "reviewDetails/$placeId"
        object Args {
            const val placeId = "placeId"
        }
    }
}
