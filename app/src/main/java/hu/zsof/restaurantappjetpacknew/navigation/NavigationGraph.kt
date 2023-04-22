package hu.zsof.restaurantappjetpacknew.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import hu.zsof.restaurantappjetpacknew.ui.details.TabLayout
import hu.zsof.restaurantappjetpacknew.ui.favorite.FavoriteListScreen
import hu.zsof.restaurantappjetpacknew.ui.filter.FilterPlaceDialogScreen
import hu.zsof.restaurantappjetpacknew.ui.homelist.HomeListScreen
import hu.zsof.restaurantappjetpacknew.ui.login.LoginScreen
import hu.zsof.restaurantappjetpacknew.ui.map.MapScreen
import hu.zsof.restaurantappjetpacknew.ui.newplace.NewPlaceDialogScreen
import hu.zsof.restaurantappjetpacknew.ui.register.RegisterScreen
import hu.zsof.restaurantappjetpacknew.ui.review.ReviewDetailsScreen
import hu.zsof.restaurantappjetpacknew.ui.review.ReviewPlaceListScreen
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
                    navController.navigate(NavigationScreen.Home.route)
                },
                onRegisterClick = {
                    navController.navigate(NavigationScreen.Register.route)
                },

            )
        }
        composable(route = NavigationScreen.Register.route) {
            RegisterScreen(
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
        ) {
            HomeListScreen(
                onFabClick = { navController.navigate(NavigationScreen.Map.route) },
                onClickPlaceItem = { navController.navigate(NavigationScreen.Details.passPlaceId(it)) },
                onFilterClick = { navController.navigate(NavigationScreen.FilterPlace.route) },
                navController = navController,
            )
        }
        composable(route = NavigationScreen.NewPlace.route) {
            NewPlaceDialogScreen()
        }
        composable(route = NavigationScreen.Map.route) {
            MapScreen(onLongClick = { navController.navigate(NavigationScreen.NewPlace.route) })
        }

        composable(route = NavigationScreen.FilterPlace.route) {
            FilterPlaceDialogScreen(
                navController = navController,
            )
        }

        composable(route = NavigationScreen.ReviewPlace.route) {
            ReviewPlaceListScreen(
                onClickPlaceItem = {
                    navController.navigate(
                        NavigationScreen.ReviewDetails.passPlaceId(
                            it,
                        ),
                    )
                },
            )
        }

        composable(NavigationScreen.FavPlace.route) {
            FavoriteListScreen(
                onClickPlaceItem = {
                    navController.navigate(
                        NavigationScreen.Details.passPlaceId(
                            it,
                        ),
                    )
                },
            )
        }
        composable(
            route = NavigationScreen.Logout.route,
        ) {
            LoginScreen(
                onLoginClick = {
                    navController.navigate(NavigationScreen.Home.route)
                },
                onRegisterClick = {
                    navController.navigate(NavigationScreen.Register.route)
                },

            )
        }
        composable(
            route = NavigationScreen.Details.route,
            arguments = listOf(
                navArgument(NavigationScreen.Details.Args.placeId) {
                    type = NavType.LongType
                },
            ),
        ) {
            TabLayout(
                placeId = navController.currentBackStackEntry?.arguments?.getLong(NavigationScreen.Details.Args.placeId)
                    ?: 0,
            )
        }
        composable(
            route = NavigationScreen.ReviewDetails.route,
            arguments = listOf(
                navArgument(NavigationScreen.ReviewDetails.Args.placeId) {
                    type = NavType.LongType
                },
            ),
        ) {
            ReviewDetailsScreen(
                placeId = navController.currentBackStackEntry?.arguments?.getLong(NavigationScreen.Details.Args.placeId)
                    ?: 0,
            )
        }
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@ExperimentalMaterial3Api
@Composable
fun NavGraph(
    navController: NavHostController,

) {
    val bottomBarState = rememberSaveable { (mutableStateOf(true)) }
    // Subscribe to navBackStackEntry, required to get current route
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    // Control BottomBar
    when (navBackStackEntry?.destination?.route) {
        "login" -> {
            // Hide BottomBar
            bottomBarState.value = false
        }
        "register" -> {
            bottomBarState.value = false
        }
        "logout" -> {
            bottomBarState.value = false
        }
        else -> {
            // Show BottomBar
            bottomBarState.value = true
        }
    }
    Scaffold(
        bottomBar = {
            BottomNavBar(
                navController = navController,
                bottomBarState = bottomBarState,
            )
        },
    ) {
        // Apply the padding globally to the whole BottomNavScreensController
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(0.dp, 0.dp, 0.dp, 36.dp),
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
}
