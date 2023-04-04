package hu.zsof.restaurantappjetpacknew.ui.favorite

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PushPin
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import hu.zsof.restaurantappjetpacknew.R
import hu.zsof.restaurantappjetpacknew.model.Place
import hu.zsof.restaurantappjetpacknew.model.enums.Price

@ExperimentalMaterial3Api
@Composable
fun FavoriteListScreen(
    viewModel: FavoriteViewModel = hiltViewModel(),
) {
    // TODO a szivecskét be kell színezni az apiból jövő adatok alapján
    // State
    val places = viewModel.favPlaces.observeAsState(listOf())
    // API call
    LaunchedEffect(key1 = Unit) {
        viewModel.showFavPlaces()
    }

    Scaffold(
        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .background(MaterialTheme.colorScheme.background),
            ) {
                LazyColumn(
                    contentPadding = PaddingValues(8.dp),
                ) {
                    items(places.value) {
                        FavListItem(place = it)
                    }
                }
            }
        },
    )
}

@ExperimentalMaterial3Api
@Composable
private fun FavListItem(
    place: Place,
    viewModel: FavoriteViewModel = hiltViewModel(),
) {
    val coroutineScope = rememberCoroutineScope()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp),
        /*.clickable(onClick = { selectRestaurant(restaurant.id) }
        ),*/
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(8.dp),
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            Column(verticalArrangement = Arrangement.Center) {
                Image(
                    painter = painterResource(id = R.drawable.ic_launcher_foreground),
                    contentDescription = null,
                    modifier = Modifier
                        .size(100.dp, 100.dp),

                )
            }
            Column() {
                Row() {
                    Text(
                        modifier = Modifier
                            .padding(start = 8.dp, top = 8.dp, end = 8.dp),
                        text = place.name,
                        style = TextStyle(fontWeight = FontWeight.Bold),
                        fontSize = 20.sp,
                    )
                }
                Row() {
                    Icon(
                        imageVector = Icons.Filled.Star,
                        contentDescription = null,
                        modifier = Modifier
                            .size(30.dp),
                        tint = Color(0xFFFFC107),

                    )
                    Text(
                        text = place.rate.toString(),
                        style = TextStyle(
                            fontStyle = FontStyle.Italic,
                            fontWeight = FontWeight.Bold,
                        ),
                        modifier = Modifier
                            .padding(8.dp),
                        fontSize = 16.sp,
                    )
                }
                Row() {
                    Text(
                        modifier = Modifier
                            .padding(8.dp),
                        text = when (place.price) {
                            Price.LOW -> {
                                "$"
                            }
                            Price.MIDDLE -> {
                                "$$"
                            }
                            else -> "$$$"
                        },
                        style = TextStyle(fontStyle = FontStyle.Italic),
                    )
                }
                Row() {
                    Icon(
                        imageVector = Icons.Filled.PushPin,
                        contentDescription = null,
                        tint = Color(0xFFF44336),
                    )
                    Text(
                        modifier = Modifier
                            .padding(8.dp),
                        text = place.address,
                        style = TextStyle(fontStyle = FontStyle.Italic),
                        fontSize = 18.sp,
                        maxLines = 3,
                    )
                }
            }
        }
    }
}

/*@ExperimentalMaterial3Api
@Preview(showBackground = true)
@Composable
fun HomeListScreen_Preview() {
    HomeListScreen(
        viewModel = HomeViewModel(),
        navController = NavHostController()
    )
}*/
