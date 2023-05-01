package hu.zsof.restaurantappjetpacknew.navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.ScaffoldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import java.util.prefs.Preferences

@Composable
fun Drawer(
    item: List<ScreenModel.NavigationScreen>,
    navController: NavController,
    scaffoldState: ScaffoldState,
    scope: CoroutineScope,
) {
    Column() {
        /* Surface(
             modifier = Modifier
                 .fillMaxWidth(1f),
         ) {
             Row(
                 modifier = Modifier.fillMaxWidth(),
                 horizontalArrangement = Arrangement.Center,
                 verticalAlignment = Alignment.CenterVertically,
             ) {
                 Text(text = "I", fontSize = 28.sp, fontWeight = FontWeight.Bold)
                 Spacer(modifier = Modifier.width(7.dp))
                 Icon(
                     imageVector = Icons.Filled.Favorite,
                     contentDescription = "",
                     tint = Color.Red,
                     modifier = Modifier.height(40.dp),
                 )
                 Spacer(modifier = Modifier.width(7.dp))
                 Text("Jetpack Compose", fontSize = 28.sp, fontWeight = FontWeight.Bold)
             }
         }
         Spacer(modifier = Modifier.height(10.dp))*/

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
                                        Preferences.userRoot().put("bearer", "")
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
                    .height(45.dp)
                    .padding(start = 10.dp),
            ) {
                item.icon?.let {
                    Icon(imageVector = it, contentDescription = "")
                }
                Spacer(modifier = Modifier.width(7.dp))
                Text(
                    text = stringResource(id = item.title),
                    fontSize = 18.sp,
                    color = Color.Black,
                )
            }
        }
    }
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
