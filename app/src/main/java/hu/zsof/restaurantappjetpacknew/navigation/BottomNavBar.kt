package hu.zsof.restaurantappjetpacknew.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

@Composable
fun BottomNavBar(
    navController: NavController,
) {
    val bottomNavItems = NavItem.values()
    var isAdminValue = false

    /* AnimatedVisibility(
         visible = rememberSaveable {
             (mutableStateOf(true))
         }.value,
         enter = slideInVertically(initialOffsetY = { it }),
     ) {*/

    val isAdmin = LocalDataStateService.userType.observeAsState().value
    if (isAdmin != null) {
        println(isAdmin)
        if (isAdmin == ROLE_ADMIN) {
            isAdminValue = true
        }
    }
    NavigationBar(
        containerColor = PurpleGrey40,
        // modifier = Modifier.height(56.dp),
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        if (isAdminValue) {
            bottomNavItems.filter { it.isAdminVisible }.forEach { item ->
                val currentRoute = item.route == navBackStackEntry?.destination?.route
                NavigationBarItem(
                    icon = { item.icon?.let { Icon(imageVector = it, contentDescription = null) } },
                    label = {
                        Text(
                            text = stringResource(item.title),
                            color = Color.Black,
                            fontSize = 16.sp,
                        )
                    },
                    selected = currentRoute,
                    onClick = {
                        item.route?.let {
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
                    /*selectedContentColor = LocalContentColor.current,
                unselectedContentColor = LocalContentColor.current,*/
                    alwaysShowLabel = true,
                )
            }
        } else {
            bottomNavItems.forEach { item ->
                val currentRoute = item.route == navBackStackEntry?.destination?.route
                NavigationBarItem(
                    icon = { item.icon?.let { Icon(imageVector = it, contentDescription = null) } },
                    label = {
                        Text(
                            text = stringResource(item.title),
                            color = Color.Black,
                            fontSize = 16.sp,
                        )
                    },
                    selected = currentRoute,
                    onClick = {
                        item.route?.let {
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
                    /*selectedContentColor = LocalContentColor.current,
                unselectedContentColor = LocalContentColor.current,*/
                    alwaysShowLabel = true,
                )
            }
        }
    }
}
