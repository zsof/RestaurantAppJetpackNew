package hu.zsof.restaurantappjetpacknew.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import hu.zsof.restaurantappjetpacknew.ui.theme.PurpleGrey40

@Composable
fun BottomNavBar(
    navController: NavController,
) {
    val bottomNavItems = NavItem.values()

    /* AnimatedVisibility(
         visible = rememberSaveable {
             (mutableStateOf(true))
         }.value,
         enter = slideInVertically(initialOffsetY = { it }),
     ) {*/
    NavigationBar(
        containerColor = PurpleGrey40,
        // modifier = Modifier.height(56.dp),
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
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
    // }
}
