package hu.zsof.restaurantappjetpacknew.navigation

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
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
import hu.zsof.restaurantappjetpacknew.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.prefs.Preferences

@Composable
fun Drawer(
    item: List<ScreenModel.NavigationScreen>,
    navController: NavController,
    scaffoldState: ScaffoldState,
    scope: CoroutineScope,
    gradientColors: List<Color> = listOf(
        MaterialTheme.colorScheme.primary,
        MaterialTheme.colorScheme.surface,
    ),
    navDrawerState: MutableState<Boolean>,
) {
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
                item.forEach() { item ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(onClick = {
                                item.route.let {
                                    navController.navigate(it) {
                                        navController.graph.startDestinationRoute?.let { route ->
                                            if (item.route == ScreenModel.NavigationScreen.Logout.route) {
                                                Preferences
                                                    .userRoot()
                                                    .put("bearer", "")
                                                popUpTo(ScreenModel.NavigationScreen.Login.route)
                                            } else {
                                                popUpTo(route) {
                                                    saveState = true
                                                }
                                            }
                                        }
                                    }
                                }
                                scope.launch {
                                    scaffoldState.drawerState.close()
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
        },
    )
}

/*fun customShape(height: Float) = object : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density,
    ): Outline {
        return Outline.Rectangle(Rect(1000f, 0f, 200f, height))
    }
}

@Composable
fun Dp.toPx(): Float {
    val density = LocalDensity.current.density
    return this.value * density
}*/
