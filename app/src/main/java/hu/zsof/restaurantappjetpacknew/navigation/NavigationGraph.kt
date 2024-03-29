package hu.zsof.restaurantappjetpacknew.navigation

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DrawerValue
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.rememberDrawerState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import hu.zsof.restaurantappjetpacknew.MainViewModel
import hu.zsof.restaurantappjetpacknew.R
import hu.zsof.restaurantappjetpacknew.module.AppState
import hu.zsof.restaurantappjetpacknew.util.Constants
import hu.zsof.restaurantappjetpacknew.util.Constants.LOGIN_START
import hu.zsof.restaurantappjetpacknew.util.Constants.ROOT_GRAPH_ROUTE
import hu.zsof.restaurantappjetpacknew.util.extension.showToast
import hu.zsof.restaurantappjetpacknew.util.internet.ConnectionState
import hu.zsof.restaurantappjetpacknew.util.internet.connectivityState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class, ExperimentalCoroutinesApi::class)
@SuppressLint(
    "UnusedMaterial3ScaffoldPaddingParameter",
    "UnusedMaterialScaffoldPaddingParameter", "StateFlowValueCalledInComposition",
)
@Composable
fun NavGraph(
    navController: NavHostController,
    viewModel: MainViewModel = hiltViewModel(),
    navigator: Navigator,
) {
    val scaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val bottomBarState = rememberSaveable { (mutableStateOf(true)) }
    val drawerOpenState = rememberSaveable { (mutableStateOf(true)) }
    val drawerGestureEnabled = rememberSaveable { (mutableStateOf(true)) }
    val isItemEnable = rememberSaveable { mutableStateOf(true) }
    val isDrawableEnable = rememberSaveable { mutableStateOf(true) }

    // Subscribe to navBackStackEntry, required to get current route
    val navBackStackEntry by navController.currentBackStackEntryAsState()

    val connection by connectivityState()
    val isConnected = connection === ConnectionState.Available

    SetStartDestination(
        context = context,
        viewModel = viewModel,
        isConnected = isConnected,
        navigator = navigator
    )
    setBottomBar(
        navBackStackEntry = navBackStackEntry,
        bottomBarState = bottomBarState,
        drawerOpenState = drawerOpenState,
        isItemEnable = isItemEnable,
        isConnected = isConnected,
        isDrawableEnable = isDrawableEnable,
        drawerGestureEnabled = drawerGestureEnabled
    )

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Scaffold(
            topBar = {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                    CenterAlignedTopAppBar(
                        title = {
                            Text(
                                stringResource(id = R.string.app_name),
                                fontSize = 28.sp,
                                fontStyle = FontStyle.Italic,
                            )
                        },
                        colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary),
                        actions = {
                            if (navBackStackEntry?.destination?.route.equals("map")) {
                                IconButton(onClick = {
                                    AppState.mapInfoClicked.value = true
                                }) {
                                    Icon(
                                        imageVector = Icons.Outlined.Info,
                                        contentDescription = "Info button"
                                    )
                                }
                            }
                        },
                    )
                }
            },
            scaffoldState = scaffoldState,
            drawerGesturesEnabled = drawerGestureEnabled.value,
            drawerContent = {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                    Drawer(
                        navController = navController,
                        scope = scope,
                        scaffoldState = scaffoldState,
                        navDrawerState = drawerOpenState,
                        context = context,
                        viewModel = viewModel,
                        isItemEnable = isItemEnable.value,
                    )
                }
            },
            bottomBar = {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                    BottomNavBar(
                        navController = navController,
                        bottomBarState = bottomBarState.value,
                        isItemEnable = isItemEnable.value,
                        onNavigationIconClick = {
                            scope.launch {
                                scaffoldState.drawerState.open()
                            }
                        },
                        viewModel = viewModel,
                        isDrawableEnable = isDrawableEnable.value
                    )
                }
            },
        ) {
            CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.primary)
                        .clip(
                            RoundedCornerShape(32.dp, 32.dp, 0.dp, 0.dp),
                        ),
                ) {
                    NavHost(
                        navController = navController,
                        startDestination = LOGIN_START,
                        route = ROOT_GRAPH_ROUTE,
                    ) {
                        mainNavGraph(navController = navController, navigator = navigator)
                    }
                }
            }
        }
    }
}

@Composable
fun SetStartDestination(
    context: Context,
    viewModel: MainViewModel,
    isConnected: Boolean,
    navigator: Navigator
) {
    if (isConnected) {
        if (viewModel.getAppPreference(Constants.Prefs.USER_LOGGED)) {
            val userResponse = viewModel.user.observeAsState().value

            navigator.destination.value = ScreenModel.NavigationScreen.Home

            when (userResponse?.userType) {
                Constants.ROLE_ADMIN -> {
                    viewModel.setAppPreference(Constants.Prefs.USER_TYPE, Constants.ROLE_ADMIN)
                }

                Constants.ROLE_USER -> {
                    viewModel.setAppPreference(Constants.Prefs.USER_TYPE, Constants.ROLE_USER)
                }

                Constants.ROLE_OWNER -> {
                    viewModel.setAppPreference(Constants.Prefs.USER_TYPE, Constants.ROLE_OWNER)
                }
            }
        } else {
            navigator.destination.value = ScreenModel.NavigationScreen.Login
        }
    } else {
        if (viewModel.getAppPreference(Constants.Prefs.USER_LOGGED)) {
            navigator.destination.value = ScreenModel.NavigationScreen.FavPlace
            showToast(context, context.getString(R.string.no_internet_connection))
        } else {
            navigator.destination.value = ScreenModel.NavigationScreen.Login
            showToast(context, context.getString(R.string.no_internet_connection))
        }
    }
}

fun setBottomBar(
    navBackStackEntry: NavBackStackEntry?,
    bottomBarState: MutableState<Boolean>,
    drawerOpenState: MutableState<Boolean>,
    isItemEnable: MutableState<Boolean>,
    isDrawableEnable: MutableState<Boolean>,
    isConnected: Boolean,
    drawerGestureEnabled: MutableState<Boolean>,
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

        "map" -> {
            drawerGestureEnabled.value = false
        }

        else -> {
            // Show BottomBar
            bottomBarState.value = true
            drawerOpenState.value = true
            drawerGestureEnabled.value = true
            isDrawableEnable.value = true
        }
    }

    if (isConnected.not()) {
        when (navBackStackEntry?.destination?.route) {
            "fav" -> isItemEnable.value = true

            else -> isItemEnable.value = false
        }
    } else {
        navBackStackEntry?.destination?.route?.forEach { _ ->
            isItemEnable.value = true
        }
    }
}
