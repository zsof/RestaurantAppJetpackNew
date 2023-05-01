package hu.zsof.restaurantappjetpacknew.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import hu.zsof.restaurantappjetpacknew.network.repository.LocalDataStateService
import hu.zsof.restaurantappjetpacknew.ui.theme.PurpleGrey40
import hu.zsof.restaurantappjetpacknew.util.Constants.ROLE_ADMIN
import hu.zsof.restaurantappjetpacknew.util.Constants.ROLE_USER
import java.util.prefs.Preferences

@Composable
fun BottomNavBar(
    navController: NavController,
    bottomBarState: MutableState<Boolean>,
    onNavigationIconClick: () -> Unit,
) {
    val adminBottomNavItems = ScreenModel().adminBottomNavItems
    val userBottomNavItems = ScreenModel().userBottomNavItems
    val navigationDrawerItems = ScreenModel().screensNavigationDrawer
    val navigationDrawer = ScreenModel.NavigationScreen.Extra

    val userType = LocalDataStateService.userType.observeAsState().value
    AnimatedVisibility(
        visible = bottomBarState.value,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
        content = {
            NavigationBar(
                containerColor = PurpleGrey40,
            ) {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                if (userType == ROLE_ADMIN) {
                    adminBottomNavItems.forEach { item ->
                        val currentRoute = item.route == navBackStackEntry?.destination?.route
                        NavigationBarItem(
                            icon = {
                                item.icon?.let {
                                    Icon(
                                        imageVector = it,
                                        contentDescription = null,
                                    )
                                }
                            },
                            label = {
                                Text(
                                    text = stringResource(item.title),
                                    color = Color.Black,
                                    fontSize = 16.sp,
                                )
                            },
                            selected = currentRoute,
                            onClick = {
                                item.route.let {
                                    navController.navigate(it) {
                                        navController.graph.startDestinationRoute?.let { route ->
                                            if (it == ScreenModel.NavigationScreen.Logout.route) {
                                                Preferences.userRoot().put("bearer", "")
                                                popUpTo(ScreenModel.NavigationScreen.Login.route)
                                            } else {
                                                popUpTo(route) {
                                                    saveState = true
                                                }
                                            }
                                        }

                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            },
                            alwaysShowLabel = true,
                        )
                    }
                } else if (userType == ROLE_USER) {
                    userBottomNavItems.forEach { item ->
                        val currentRoute = item.route == navBackStackEntry?.destination?.route
                        NavigationBarItem(
                            icon = {
                                item.icon?.let {
                                    Icon(
                                        imageVector = it,
                                        contentDescription = null,
                                    )
                                }
                            },
                            label = {
                                Text(
                                    text = stringResource(item.title),
                                    color = Color.Black,
                                    fontSize = 16.sp,
                                )
                            },
                            selected = currentRoute,
                            onClick = {
                                item.route.let {
                                    navController.navigate(it) {
                                        navController.graph.startDestinationRoute?.let { route ->
                                            if (it == ScreenModel.NavigationScreen.Logout.route) {
                                                Preferences.userRoot().put("bearer", "")
                                                popUpTo(ScreenModel.NavigationScreen.Login.route)
                                            } else {
                                                popUpTo(route) {
                                                    saveState = true
                                                }
                                            }
                                        }

                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                }
                            },
                            alwaysShowLabel = true,
                        )
                    }
                }
                NavigationBarItem(
                    icon = {
                        navigationDrawer.icon?.let {
                            Icon(
                                imageVector = it,
                                contentDescription = null,
                            )
                        }
                    },
                    label = {
                        Text(
                            text = stringResource(navigationDrawer.title),
                            color = Color.Black,
                            fontSize = 16.sp,
                        )
                    },
                    selected = navigationDrawer.route == navBackStackEntry?.destination?.route,
                    onClick = onNavigationIconClick,
                )
            }
        },
    )
}
