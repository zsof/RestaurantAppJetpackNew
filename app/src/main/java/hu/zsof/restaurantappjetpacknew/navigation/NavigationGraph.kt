package hu.zsof.restaurantappjetpacknew.navigation

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.DrawerValue
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberDrawerState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.*
import androidx.navigation.navArgument
import hu.zsof.restaurantappjetpacknew.MainViewModel
import hu.zsof.restaurantappjetpacknew.ui.auth.login.LoginScreen
import hu.zsof.restaurantappjetpacknew.ui.auth.register.RegisterScreen
import hu.zsof.restaurantappjetpacknew.ui.details.TabLayout
import hu.zsof.restaurantappjetpacknew.ui.favorite.FavoriteListScreen
import hu.zsof.restaurantappjetpacknew.ui.filter.FilterPlaceDialogScreen
import hu.zsof.restaurantappjetpacknew.ui.homelist.HomeListScreen
import hu.zsof.restaurantappjetpacknew.ui.map.MapScreen
import hu.zsof.restaurantappjetpacknew.ui.newplace.NewPlaceDialogScreen
import hu.zsof.restaurantappjetpacknew.ui.owner.OwnerDetailsScreen
import hu.zsof.restaurantappjetpacknew.ui.owner.OwnerPlaceListScreen
import hu.zsof.restaurantappjetpacknew.ui.profile.ProfileScreen
import hu.zsof.restaurantappjetpacknew.ui.review.ReviewDetailsScreen
import hu.zsof.restaurantappjetpacknew.ui.review.ReviewPlaceListScreen
import hu.zsof.restaurantappjetpacknew.util.Constants.AUTH_GRAPH_ROUTE
import hu.zsof.restaurantappjetpacknew.util.Constants.MAIN_GRAPH_ROUTE
import hu.zsof.restaurantappjetpacknew.util.Constants.ROOT_GRAPH_ROUTE
import kotlinx.coroutines.launch
import java.util.prefs.Preferences

@ExperimentalMaterial3Api
fun NavGraphBuilder.authNavGraph(
    navController: NavHostController,
) {
    navigation(
        startDestination = ScreenModel.NavigationScreen.Login.route,
        route = AUTH_GRAPH_ROUTE,
    ) {
        composable(
            route = ScreenModel.NavigationScreen.Login.route,
        ) {
            LoginScreen(
                onLoginClick = {
                    navController.navigate(ScreenModel.NavigationScreen.Home.route)
                },
                onRegisterClick = {
                    navController.navigate(ScreenModel.NavigationScreen.Register.route)
                },

            )
        }
        composable(route = ScreenModel.NavigationScreen.Register.route) {
            RegisterScreen(
                onLoginClick = { navController.navigate(ScreenModel.NavigationScreen.Login.route) },
            )
        }
    }
}

@ExperimentalMaterial3Api
fun NavGraphBuilder.mainNavGraph(
    navController: NavHostController,
) {
    navigation(
        startDestination = ScreenModel.NavigationScreen.Home.route,
        route = MAIN_GRAPH_ROUTE,
    ) {
        composable(
            route = ScreenModel.NavigationScreen.Home.route,
        ) {
            HomeListScreen(
                onFabClick = { navController.navigate(ScreenModel.NavigationScreen.Map.route) },
                onClickPlaceItem = {
                    navController.navigate(
                        ScreenModel.NavigationScreen.Details.passPlaceId(
                            it,
                        ),
                    )
                },
                onFilterClick = { navController.navigate(ScreenModel.NavigationScreen.FilterPlace.route) },
            )
        }
        composable(route = ScreenModel.NavigationScreen.NewPlace.route) {
            NewPlaceDialogScreen()
        }
        composable(route = ScreenModel.NavigationScreen.Map.route) {
            MapScreen(
                onLongClick = { navController.navigate(ScreenModel.NavigationScreen.NewPlace.route) },
            )
        }

        composable(route = ScreenModel.NavigationScreen.FilterPlace.route) {
            FilterPlaceDialogScreen(
                navController = navController,
            )
        }

        composable(route = ScreenModel.NavigationScreen.ReviewPlace.route) {
            ReviewPlaceListScreen(
                onClickPlaceItem = {
                    navController.navigate(
                        ScreenModel.NavigationScreen.ReviewDetails.passPlaceId(
                            it,
                        ),
                    )
                },
            )
        }

        composable(ScreenModel.NavigationScreen.FavPlace.route) {
            FavoriteListScreen(
                onClickPlaceItem = {
                    navController.navigate(
                        ScreenModel.NavigationScreen.Details.passPlaceId(
                            it,
                        ),
                    )
                },
            )
        }
        composable(
            route = ScreenModel.NavigationScreen.Logout.route,
        ) {
            LoginScreen(
                onLoginClick = {
                    navController.navigate(ScreenModel.NavigationScreen.Home.route)
                },
                onRegisterClick = {
                    navController.navigate(ScreenModel.NavigationScreen.Register.route)
                },

            )
        }
        composable(
            route = ScreenModel.NavigationScreen.Details.route,
            arguments = listOf(
                navArgument(ScreenModel.NavigationScreen.Details.Args.placeId) {
                    type = NavType.LongType
                },
            ),
        ) {
            TabLayout(
                placeId = navController.currentBackStackEntry?.arguments?.getLong(ScreenModel.NavigationScreen.Details.Args.placeId)
                    ?: 0,
            )
        }
        composable(
            route = ScreenModel.NavigationScreen.ReviewDetails.route,
            arguments = listOf(
                navArgument(ScreenModel.NavigationScreen.ReviewDetails.Args.placeId) {
                    type = NavType.LongType
                },
            ),
        ) {
            ReviewDetailsScreen(
                placeId = navController.currentBackStackEntry?.arguments?.getLong(ScreenModel.NavigationScreen.Details.Args.placeId)
                    ?: 0,
            )
        }
        composable(route = ScreenModel.NavigationScreen.Profile.route) {
            ProfileScreen()
        }

        composable(route = ScreenModel.NavigationScreen.OwnerPlace.route) {
            OwnerPlaceListScreen(
                onClickPlaceItem = {
                    navController.navigate(
                        ScreenModel.NavigationScreen.Details.passPlaceId(
                            it,
                        ),
                    )
                },
                onClickPlaceInReviewItem = {
                    navController.navigate(
                        ScreenModel.NavigationScreen.OwnerDetails.passPlaceId(
                            it,
                        ),
                    )
                },
            )
        }
        composable(
            route = ScreenModel.NavigationScreen.OwnerDetails.route,
            arguments = listOf(
                navArgument(ScreenModel.NavigationScreen.OwnerDetails.Args.placeId) {
                    type = NavType.LongType
                },
            ),
        ) {
            OwnerDetailsScreen(
                placeId = navController.currentBackStackEntry?.arguments?.getLong(ScreenModel.NavigationScreen.OwnerDetails.Args.placeId)
                    ?: 0,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "UnusedMaterialScaffoldPaddingParameter")
@Composable
fun NavGraph(
    navController: NavHostController,
    viewModel: MainViewModel = hiltViewModel(),
) {
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val scope = rememberCoroutineScope()

    val bottomBarState = rememberSaveable { (mutableStateOf(true)) }
    val drawerOpenState = rememberSaveable { (mutableStateOf(true)) }

    // Subscribe to navBackStackEntry, required to get current route
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val token = Preferences.userRoot().get("bearer", "")
    if (token.isNullOrEmpty().not()) {
        LaunchedEffect(key1 = "MainActivity") {
            viewModel.authenticateLoggedUser(token)
        }
        // navigate to home
    } else { // navigate to login
    }

    // Control BottomBar
    when (navBackStackEntry?.destination?.route) {
        "login" -> {
            // Hide BottomBar
            bottomBarState.value = false
            drawerOpenState.value = false
        }

        "register" -> {
            bottomBarState.value = false
            drawerOpenState.value = false
        }

        "logout" -> {
            bottomBarState.value = false
            drawerOpenState.value = false
        }

        else -> {
            // Show BottomBar
            bottomBarState.value = true
            drawerOpenState.value = true
        }
    }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Scaffold(
            scaffoldState = scaffoldState,
            drawerGesturesEnabled = drawerOpenState.value,
            drawerContent = {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                    Drawer(
                        navController = navController,
                        scope = scope,
                        scaffoldState = scaffoldState,
                        navDrawerState = drawerOpenState,
                    )
                }
            },
            bottomBar = {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                    BottomNavBar(
                        navController = navController,
                        bottomBarState = bottomBarState,
                        onNavigationIconClick = {
                            scope.launch {
                                scaffoldState.drawerState.open()
                            }
                        },
                    )
                }
            },
        ) {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                Box(
                    modifier = Modifier
                        .fillMaxSize(),
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
    }
}
