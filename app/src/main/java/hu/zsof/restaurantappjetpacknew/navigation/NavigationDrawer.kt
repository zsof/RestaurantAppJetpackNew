package hu.zsof.restaurantappjetpacknew.navigation

import android.content.Context
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import hu.zsof.restaurantappjetpacknew.MainViewModel
import hu.zsof.restaurantappjetpacknew.R
import hu.zsof.restaurantappjetpacknew.util.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun Drawer(
    navController: NavController,
    scaffoldState: ScaffoldState,
    scope: CoroutineScope,
    gradientColors: List<Color> = listOf(
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.surface,
    ),
    navDrawerState: MutableState<Boolean>,
    context: Context,
    viewModel: MainViewModel,
    isItemEnable: Boolean,
) {
    val userScreensNavigation = ScreenModel().userScreensNavigationDrawer
    val screensNavigation = ScreenModel().screensNavigationDrawer
    val userType = viewModel.getAppPreference<String>(Constants.Prefs.USER_TYPE)

    AnimatedVisibility(
        visible = navDrawerState.value,
        enter = slideInVertically(initialOffsetY = { it }),
        exit = slideOutVertically(targetOffsetY = { it }),
        content = {
            Column(
                Modifier
                    .fillMaxSize()
                    .background(brush = Brush.verticalGradient(colors = gradientColors)),
            ) {
                Icon(
                    imageVector = Icons.Filled.Close,
                    contentDescription = null,
                    modifier = Modifier
                        .clickable(enabled = true, onClick = {
                            scope.launch {
                                scaffoldState.drawerState.close()
                            }
                        })
                        .padding(start = 16.dp, top = 16.dp, bottom = 8.dp)
                )
                Text(
                    text = stringResource(id = R.string.welcome_text),
                    style = MaterialTheme.typography.headlineLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontStyle = FontStyle.Italic,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(16.dp),
                )
                Spacer(modifier = Modifier.height(20.dp))
                when (userType) {
                    Constants.ROLE_USER -> {
                        ScreenItems(
                            role = userScreensNavigation,
                            navController = navController,
                            scaffoldState = scaffoldState,
                            scope = scope,
                            context = context,
                            viewModel = viewModel,
                            isItemEnable = isItemEnable,
                        )
                    }

                    else -> {
                        ScreenItems(
                            role = screensNavigation,
                            navController = navController,
                            scaffoldState = scaffoldState,
                            scope = scope,
                            context = context,
                            viewModel = viewModel,
                            isItemEnable = isItemEnable,
                        )
                    }
                }
            }
        },
    )
}

@Composable
fun ScreenItems(
    role: List<ScreenModel.NavigationScreen>,
    navController: NavController,
    scaffoldState: ScaffoldState,
    scope: CoroutineScope,
    context: Context,
    viewModel: MainViewModel,
    isItemEnable: Boolean,
) {
    role.forEach() { item ->
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = {
                    if (item.route == ScreenModel.NavigationScreen.FavPlace.route) {
                        navigateToItem(context, item, navController, viewModel)
                        scope.launch {
                            scaffoldState.drawerState.close()
                        }
                    } else if (isItemEnable) {
                        navigateToItem(context, item, navController, viewModel)
                        scope.launch {
                            scaffoldState.drawerState.close()
                        }
                    }
                })
                .height(42.dp)
                .padding(start = 10.dp),
        ) {
            item.icon?.let {
                Icon(imageVector = it, contentDescription = "")
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = stringResource(id = item.title),
                fontSize = 20.sp,
            )
        }
    }
}
