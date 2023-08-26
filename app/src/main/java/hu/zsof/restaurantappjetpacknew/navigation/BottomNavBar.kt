package hu.zsof.restaurantappjetpacknew.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import hu.zsof.restaurantappjetpacknew.MainViewModel
import hu.zsof.restaurantappjetpacknew.util.Constants
import hu.zsof.restaurantappjetpacknew.util.Constants.ROLE_ADMIN
import hu.zsof.restaurantappjetpacknew.util.Constants.ROLE_OWNER
import hu.zsof.restaurantappjetpacknew.util.Constants.ROLE_USER

@Composable
fun BottomNavBar(
    navController: NavController,
    bottomBarState: Boolean,
    isItemEnable: Boolean,
    onNavigationIconClick: () -> Unit,
    viewModel: MainViewModel,
) {
    val adminBottomNavItems = ScreenModel().adminBottomNavItems
    val userBottomNavItems = ScreenModel().userBottomNavItems
    val ownerBottomNavItems = ScreenModel().ownerBottomNavItems
    val navigationDrawer = ScreenModel.NavigationScreen.Extra

    val userType = viewModel.getAppPreference<String>(Constants.Prefs.USER_TYPE)
    AnimatedVisibility(
        visible = bottomBarState,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
        content = {
            Box(modifier = Modifier.fillMaxSize().padding(top = 8.dp)) {
                BottomNavigation(
                    modifier = Modifier.align(alignment = Alignment.BottomCenter)
                        .clip(
                            RoundedCornerShape(32.dp, 32.dp, 0.dp, 0.dp),
                        ),
                    backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
                ) {
                    val navBackStackEntry by navController.currentBackStackEntryAsState()
                    when (userType) {
                        ROLE_ADMIN -> {
                            adminBottomNavItems.forEach { item ->
                                val currentRoute =
                                    item.route == navBackStackEntry?.destination?.route
                                BottomNavigationItem(
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
                                            fontSize = 12.sp,
                                        )
                                    },
                                    selected = currentRoute,
                                    onClick = {
                                        item.route.let {
                                            navController.navigate(it) {
                                                navController.graph.startDestinationRoute?.let { route ->
                                                    popUpTo(route) {
                                                        saveState = true
                                                    }
                                                }

                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        }
                                    },
                                    alwaysShowLabel = true,
                                    enabled = isItemEnable,
                                )
                            }
                        }

                        ROLE_USER -> {
                            userBottomNavItems.forEach { item ->
                                val currentRoute =
                                    item.route == navBackStackEntry?.destination?.route
                                BottomNavigationItem(
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
                                            fontSize = 12.sp,
                                        )
                                    },
                                    selected = currentRoute,
                                    onClick = {
                                        item.route.let {
                                            navController.navigate(it) {
                                                navController.graph.startDestinationRoute?.let { route ->
                                                    popUpTo(route) {
                                                        saveState = true
                                                    }
                                                }

                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        }
                                    },
                                    alwaysShowLabel = true,
                                    enabled = isItemEnable,
                                )
                            }
                        }

                        ROLE_OWNER -> {
                            ownerBottomNavItems.forEach { item ->
                                val currentRoute =
                                    item.route == navBackStackEntry?.destination?.route
                                BottomNavigationItem(
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
                                            fontSize = 12.sp,
                                        )
                                    },
                                    selected = currentRoute,
                                    onClick = {
                                        item.route.let {
                                            navController.navigate(it) {
                                                navController.graph.startDestinationRoute?.let { route ->
                                                    popUpTo(route) {
                                                        saveState = true
                                                    }
                                                }

                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        }
                                    },
                                    alwaysShowLabel = true,
                                    enabled = isItemEnable,
                                )
                            }
                        }
                    }
                    BottomNavigationItem(
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
                                fontSize = 12.sp,
                            )
                        },
                        selected = navigationDrawer.route == navBackStackEntry?.destination?.route,
                        onClick = onNavigationIconClick,
                        enabled = true,
                    )
                }
            }
        },
    )
}
