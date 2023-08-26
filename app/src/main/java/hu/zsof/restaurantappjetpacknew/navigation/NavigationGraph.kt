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
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import hu.zsof.restaurantappjetpacknew.MainViewModel
import hu.zsof.restaurantappjetpacknew.network.repository.LocalDataStateService
import hu.zsof.restaurantappjetpacknew.ui.common.showToast
import hu.zsof.restaurantappjetpacknew.util.Constants
import hu.zsof.restaurantappjetpacknew.util.Constants.FAV_START
import hu.zsof.restaurantappjetpacknew.util.Constants.HOME_START
import hu.zsof.restaurantappjetpacknew.util.Constants.LOGIN_START
import hu.zsof.restaurantappjetpacknew.util.Constants.ROOT_GRAPH_ROUTE
import hu.zsof.restaurantappjetpacknew.util.internet.ConnectionState
import hu.zsof.restaurantappjetpacknew.util.internet.connectivityState
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
    val isItemEnable = rememberSaveable { mutableStateOf(true) }

    // Subscribe to navBackStackEntry, required to get current route
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val connection by connectivityState()
    val isConnected = connection === ConnectionState.Available

    setStartDestination(context = context, viewModel = viewModel, isConnected = isConnected)
    setBottomBar(navBackStackEntry, bottomBarState, drawerOpenState, isItemEnable, isConnected)

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
                        context = context,
                        viewModel = viewModel,
                    )
                }
            },
            bottomBar = {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                    BottomNavBar(
                        navController = navController,
                        bottomBarState = bottomBarState,
                        isItemEnable = isItemEnable,
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
                        startDestination = LOGIN_START,
                        route = ROOT_GRAPH_ROUTE,
                    ) {
                        authNavGraph(navController = navController)
                    }
                }
            }
        }
    }
}

fun setStartDestination(
    context: Context,
    viewModel: MainViewModel,
    isConnected: Boolean,
) {
    val sharedPreferences =
        context.getSharedPreferences(Constants.Prefs.AUTH_SHARED_PREFERENCES, Context.MODE_PRIVATE)
    val token = sharedPreferences.getString("bearer", "")

    if (token.isNullOrEmpty().not()) {
        if (isConnected) {
            val response = viewModel.authenticateLoggedUser()

            LocalDataStateService.loggedUser = response.user
            LocalDataStateService.startDestination = HOME_START
            viewModel.setAppPreference(Constants.Prefs.USER_LOGGED, true)

            when (response.user.userType) {
                Constants.ROLE_ADMIN -> {
                    LocalDataStateService.userType.postValue(Constants.ROLE_ADMIN)
                }

                Constants.ROLE_USER -> {
                    LocalDataStateService.userType.postValue(Constants.ROLE_USER)
                }

                Constants.ROLE_OWNER -> {
                    LocalDataStateService.userType.postValue(Constants.ROLE_OWNER)
                }
            }
        } else if (viewModel.getAppPreference(Constants.Prefs.USER_LOGGED)) {
            LocalDataStateService.startDestination = FAV_START
        } else {
            LocalDataStateService.startDestination = LOGIN_START
            showToast(context, "Az internet nem elérhető!")
        }
    } else {
        LocalDataStateService.startDestination = LOGIN_START
    }
}

fun setBottomBar(
    navBackStackEntry: NavBackStackEntry?,
    bottomBarState: MutableState<Boolean>,
    drawerOpenState: MutableState<Boolean>,
    isItemEnable: MutableState<Boolean>,
    isConnected: Boolean,
) {
    // Control BottomBar
    when (navBackStackEntry?.destination?.route) {
        // Hide BottomBar
        "login" -> {
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

    if (isConnected.not()) {
        when (navBackStackEntry?.destination?.route) {
            "fav" -> {
                isItemEnable.value = true
            }

            else -> isItemEnable.value = false
        }
    }
}
