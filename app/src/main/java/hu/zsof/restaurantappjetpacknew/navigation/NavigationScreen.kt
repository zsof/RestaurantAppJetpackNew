package hu.zsof.restaurantappjetpacknew.navigation

sealed class NavigationScreen(val route: String) {
    object Login : NavigationScreen(route = "login")
    object Register : NavigationScreen(route = "register")
    object Home : NavigationScreen(route = "home/{${Args.username}}") {
        fun passUsername(username: String) = "home/$username"

        object Args {
            const val username = "username"
        }
    }

    object Profile : NavigationScreen(route = "profile")

    object NewPlace : NavigationScreen(route = "newPlace")
}
