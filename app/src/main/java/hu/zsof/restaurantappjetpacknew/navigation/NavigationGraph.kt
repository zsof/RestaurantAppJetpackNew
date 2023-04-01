package hu.zsof.restaurantappjetpacknew.navigation

import android.annotation.SuppressLint
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import hu.zsof.restaurantappjetpacknew.ui.homelist.HomeListScreen
import hu.zsof.restaurantappjetpacknew.ui.login.LoginScreen
import hu.zsof.restaurantappjetpacknew.ui.map.MapScreen
import hu.zsof.restaurantappjetpacknew.ui.newplace.NewPlaceDialogScreen
import hu.zsof.restaurantappjetpacknew.ui.register.RegisterScreen
import hu.zsof.restaurantappjetpacknew.util.Constants.AUTH_GRAPH_ROUTE
import hu.zsof.restaurantappjetpacknew.util.Constants.MAIN_GRAPH_ROUTE
import hu.zsof.restaurantappjetpacknew.util.Constants.ROOT_GRAPH_ROUTE

@ExperimentalMaterial3Api
fun NavGraphBuilder.authNavGraph(
    navController: NavHostController,
) {
    navigation(
        startDestination = NavigationScreen.Login.route,
        route = AUTH_GRAPH_ROUTE,
    ) {
        composable(
            route = NavigationScreen.Login.route,
        ) {
            LoginScreen(
                onLoginClick = {
                    navController.navigate(NavigationScreen.Home.passUsername(it))
                    // navController.navigate(NavigationScreen.Register.route)
                    println("login")
                },
                onRegisterClick = {
                    navController.navigate(NavigationScreen.Register.route)
                    println("register")
                },

            )
        }
        composable(route = NavigationScreen.Register.route) {
            RegisterScreen(
                onRegisterClick = { navController.navigate(NavigationScreen.Home.passUsername(it)) },
                onLoginClick = { navController.navigate(NavigationScreen.Login.route) },
            )
        }
    }
}

@ExperimentalMaterial3Api
fun NavGraphBuilder.mainNavGraph(
    navController: NavHostController,
) {
    navigation(
        startDestination = NavigationScreen.Home.route,
        route = MAIN_GRAPH_ROUTE,
    ) {
        composable(
            route = NavigationScreen.Home.route,
            arguments = listOf(
                navArgument(NavigationScreen.Home.Args.username) {
                    type = NavType.StringType
                },
            ),
        ) {
            HomeListScreen(
                onFabClick = { navController.navigate(NavigationScreen.Map.route) },
                /*  argument = navController.currentBackStackEntry?.arguments
                      ?.getString(NavigationScreen.Home.Args.username) ?: "",
                 */
            )
        }
        composable(route = NavigationScreen.NewPlace.route) {
            NewPlaceDialogScreen()
        }
        composable(route = NavigationScreen.Map.route) {
            MapScreen(onLongClick = { navController.navigate(NavigationScreen.NewPlace.route) })
        }
        /*composable(route = NavigationScreen.Profile.route) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text(text = "Profile")
            }
        }
 composable(route = NavigationScreen.Settings.route) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text(text = "Settings")
            }
        }*/
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@Composable
fun NavGraph(
    navController: NavHostController,

) {
    Scaffold(
        bottomBar = { BottomNavBar(navController = navController) },
    ) {
        NavHost(
            navController = navController,
            startDestination = AUTH_GRAPH_ROUTE,
            route = ROOT_GRAPH_ROUTE,
        ) {
            authNavGraph(navController = navController)
            mainNavGraph(navController = navController)
        }
    }
}
