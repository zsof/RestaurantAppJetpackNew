package hu.zsof.restaurantappjetpacknew.navigation

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.material.DrawerValue
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberDrawerState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import hu.zsof.restaurantappjetpacknew.MainViewModel
import hu.zsof.restaurantappjetpacknew.network.repository.LocalDataStateService
import hu.zsof.restaurantappjetpacknew.util.Constants.AUTH_GRAPH_ROUTE
import hu.zsof.restaurantappjetpacknew.util.Constants.MAIN_GRAPH_ROUTE
import hu.zsof.restaurantappjetpacknew.util.Constants.ROOT_GRAPH_ROUTE
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint(
    "UnusedMaterial3ScaffoldPaddingParameter",
    "UnusedMaterialScaffoldPaddingParameter",
)
@Composable
fun NavGraph(
    navController: NavHostController,
    viewModel: MainViewModel = hiltViewModel(),
) {
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val bottomBarState = rememberSaveable { (mutableStateOf(true)) }
    val drawerOpenState = rememberSaveable { (mutableStateOf(true)) }

    // Subscribe to navBackStackEntry, required to get current route
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val sharedPreferences = context.getSharedPreferences("AuthSharedPref", Context.MODE_PRIVATE)
    val token = sharedPreferences.getString("bearer", "")
    if (token.isNullOrEmpty().not()) {
        LaunchedEffect(key1 = "MainActivity") {
            viewModel.authenticateLoggedUser()
        }
        LocalDataStateService.startDestination = MAIN_GRAPH_ROUTE
    } else {
        LocalDataStateService.startDestination = AUTH_GRAPH_ROUTE
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
                    }
                }
            }
        }
    }
}
