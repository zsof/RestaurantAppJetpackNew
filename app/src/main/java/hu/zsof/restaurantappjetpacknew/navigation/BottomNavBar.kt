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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
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
    isDrawableEnable: Boolean,
    onNavigationIconClick: () -> Unit,
    viewModel: MainViewModel,
) {
    val adminBottomNavItems = ScreenModel().adminBottomNavItems
    val userBottomNavItems = ScreenModel().userBottomNavItems
    val ownerBottomNavItems = ScreenModel().ownerBottomNavItems
    val navigationDrawer = ScreenModel.NavigationScreen.Extra

    val userType = viewModel.getAppPreference<String>(Constants.Prefs.USER_TYPE)
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val shape =
        if (navBackStackEntry?.destination?.route == ScreenModel.NavigationScreen.Map.route) {
            RoundedCornerShape(0.dp)
        } else {
            RoundedCornerShape(32.dp, 32.dp, 0.dp, 0.dp)
        }

    val context = LocalContext.current

    AnimatedVisibility(
        visible = bottomBarState,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 8.dp),
            ) {
                BottomNavigation(
                    modifier = Modifier
                        .align(alignment = Alignment.BottomCenter)
                        .clip(shape),
                    backgroundColor = MaterialTheme.colorScheme.secondaryContainer,
                ) {
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
                                                tint = if (currentRoute) Color.Black else Color.Black.copy(
                                                    0.4f
                                                )
                                            )
                                        }
                                    },
                                    label = {
                                        Text(
                                            text = stringResource(item.title),
                                            fontSize = 12.sp,
                                            color = if (currentRoute) Color.Black else Color.Black.copy(
                                                0.4f
                                            ),
                                            fontWeight = if (currentRoute) FontWeight.Bold else FontWeight.Normal
                                        )
                                    },
                                    selected = currentRoute,
                                    selectedContentColor = MaterialTheme.colorScheme.primary,
                                    onClick = {
                                        navigateToItem(context, item, navController, viewModel)
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
                                                tint = if (currentRoute) Color.Black else Color.Black.copy(
                                                    0.4f
                                                )
                                            )
                                        }
                                    },
                                    label = {
                                        Text(
                                            text = stringResource(item.title),
                                            fontSize = 12.sp,
                                            color = if (currentRoute) Color.Black else Color.Black.copy(
                                                0.4f
                                            ),
                                            fontWeight = if (currentRoute) FontWeight.Bold else FontWeight.Normal
                                        )
                                    },
                                    selected = currentRoute,
                                    selectedContentColor = MaterialTheme.colorScheme.primary,
                                    onClick = {
                                        navigateToItem(context, item, navController, viewModel)
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
                                                tint = if (currentRoute) Color.Black else Color.Black.copy(
                                                    0.4f
                                                )
                                            )
                                        }
                                    },
                                    label = {
                                        Text(
                                            text = stringResource(item.title),
                                            fontSize = 12.sp,
                                            color = if (currentRoute) Color.Black else Color.Black.copy(
                                                0.4f
                                            ),
                                            fontWeight = if (currentRoute) FontWeight.Bold else FontWeight.Normal
                                        )
                                    },
                                    selected = currentRoute,
                                    selectedContentColor = MaterialTheme.colorScheme.primary,
                                    onClick = {
                                        navigateToItem(context, item, navController, viewModel)
                                    },
                                    alwaysShowLabel = true,
                                    enabled = isItemEnable,
                                )
                            }
                        }
                    }
                    val navigationDrawerSelected =
                        navigationDrawer.route == navBackStackEntry?.destination?.route
                    BottomNavigationItem(
                        icon = {
                            navigationDrawer.icon?.let {
                                Icon(
                                    imageVector = it,
                                    contentDescription = null,
                                    tint = if (navigationDrawerSelected) Color.Black else Color.Black.copy(
                                        0.4f
                                    )
                                )
                            }
                        },
                        label = {
                            Text(
                                text = stringResource(navigationDrawer.title),
                                fontSize = 12.sp,
                                color = if (navigationDrawerSelected) Color.Black else Color.Black.copy(
                                    0.4f
                                ),
                                fontWeight = if (navigationDrawerSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        selected = navigationDrawerSelected,
                        selectedContentColor = MaterialTheme.colorScheme.primary,
                        onClick = onNavigationIconClick,
                        enabled = isDrawableEnable,
                    )
                }
            }
        },
    )
}
