package hu.zsof.restaurantappjetpacknew.navigation

sealed class NavigationScreen(val route: String) {
    object Login : NavigationScreen(route = "login")
    object Register : NavigationScreen(route = "register")
    object Home : NavigationScreen(route = "home")
    object Profile : NavigationScreen(route = "profile")
    object NewPlace : NavigationScreen(route = "newPlace")
    object Map : NavigationScreen(route = "map")
    object FavPlace : NavigationScreen(route = "favPlace")
    object ReviewPlace : NavigationScreen(route = "reviewPlace")
    object Logout : NavigationScreen(route = "logout")
}
