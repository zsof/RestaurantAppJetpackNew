package hu.zsof.restaurantappjetpacknew.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import hu.zsof.restaurantappjetpacknew.ui.auth.login.LoginScreen
import hu.zsof.restaurantappjetpacknew.ui.auth.register.RegisterScreen
import hu.zsof.restaurantappjetpacknew.ui.details.TabLayoutDetails
import hu.zsof.restaurantappjetpacknew.ui.edit.EditPlaceDialogScreen
import hu.zsof.restaurantappjetpacknew.ui.favorite.FavoriteListScreen
import hu.zsof.restaurantappjetpacknew.ui.filter.FilterPlaceDialogScreen
import hu.zsof.restaurantappjetpacknew.ui.homelist.HomeListScreen
import hu.zsof.restaurantappjetpacknew.ui.map.MapScreen
import hu.zsof.restaurantappjetpacknew.ui.newplace.NewPlaceDialogScreen
import hu.zsof.restaurantappjetpacknew.ui.owner.OwnerDetailsScreen
import hu.zsof.restaurantappjetpacknew.ui.owner.TabLayoutOwnerList
import hu.zsof.restaurantappjetpacknew.ui.profile.ProfileScreen
import hu.zsof.restaurantappjetpacknew.ui.review.ReviewDetailsScreen
import hu.zsof.restaurantappjetpacknew.ui.review.TabLayoutReviewList
import hu.zsof.restaurantappjetpacknew.util.Constants

@ExperimentalMaterial3Api
fun NavGraphBuilder.authNavGraph(
    navController: NavHostController,
    navigator: Navigator
) {
    navigator.destination.value?.route?.let {
        navigation(
        startDestination = it,
        route = Constants.LOGIN_START,
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
            NewPlaceDialogScreen(
                onDialogClose = { navController.popBackStack() },
            )
        }
        composable(route = ScreenModel.NavigationScreen.Map.route) {
            MapScreen(
                onLongClick = { navController.navigate(ScreenModel.NavigationScreen.NewPlace.route) },
            )
        }

        composable(route = ScreenModel.NavigationScreen.FilterPlace.route) {
            FilterPlaceDialogScreen(
                navController = navController,
                onDialogClose = { navController.popBackStack() },
            )
        }

        composable(route = ScreenModel.NavigationScreen.ReviewPlace.route) {
            TabLayoutReviewList(
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
            TabLayoutDetails(
                placeId = navController.currentBackStackEntry?.arguments?.getLong(ScreenModel.NavigationScreen.Details.Args.placeId)
                    ?: 0,
                onEditPlaceClick = {
                    navController.navigate(
                        ScreenModel.NavigationScreen.EditPlace.passPlaceId(
                            it,
                        ),
                    )
                },
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
                onPlaceAccepted = { navController.popBackStack() }
            )
        }
        composable(route = ScreenModel.NavigationScreen.Profile.route) {
            ProfileScreen()
        }

        composable(route = ScreenModel.NavigationScreen.OwnerPlace.route) {
            TabLayoutOwnerList(
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
                    ?: 0
            )
        }

        composable(
            route = ScreenModel.NavigationScreen.EditPlace.route,
            arguments = listOf(
                navArgument(ScreenModel.NavigationScreen.EditPlace.Args.placeId) {
                    type = NavType.LongType
                },
            ),
        ) {
            EditPlaceDialogScreen(
                onDialogClose = {
                    navController.popBackStack()
                },
            )
        }

    }
    }
}
