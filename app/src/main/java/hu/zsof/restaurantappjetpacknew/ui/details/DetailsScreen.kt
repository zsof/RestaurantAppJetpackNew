package hu.zsof.restaurantappjetpacknew.ui.details

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.Web
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import hu.zsof.restaurantappjetpacknew.R

@Composable
fun DetailsScreen(
    viewModel: DetailsViewModel = hiltViewModel(),
    placeId: Long,
) {
    val place = viewModel.placeById.observeAsState().value
    LaunchedEffect(key1 = "Details") {
        viewModel.getPlaceById(placeId)
    }

    Column(
        modifier = Modifier.fillMaxSize().verticalScroll(rememberScrollState()),
    ) {
        if (place != null) {
            AsyncImage(
                model = "https://media.geeksforgeeks.org/wp-content/uploads/20210101144014/gfglogo.png",
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .wrapContentSize()
                    .wrapContentHeight()
                    .wrapContentWidth()
                    .align(Alignment.CenterHorizontally),
            )

            Text(
                text = place.name,
                style = MaterialTheme.typography.h5,
                fontWeight = FontWeight.Bold,
                fontStyle = FontStyle.Italic,
                modifier = Modifier.align(Alignment.CenterHorizontally).padding(16.dp),
            )

            Row(horizontalArrangement = Arrangement.Start) {
                Icon(
                    imageVector = Icons.Outlined.Map,
                    contentDescription = null,
                    modifier = Modifier.padding(8.dp, 4.dp, 0.dp, 0.dp),
                )
                Text(
                    modifier = Modifier
                        .padding(8.dp, 0.dp),
                    text = place.address,
                    fontSize = 18.sp,
                    maxLines = 3,
                )
            }
            Row(horizontalArrangement = Arrangement.Start) {
                Icon(
                    imageVector = Icons.Outlined.Web,
                    contentDescription = null,
                    modifier = Modifier.padding(8.dp, 4.dp, 0.dp, 0.dp),
                )
                Text(
                    modifier = Modifier
                        .padding(8.dp, 0.dp),
                    text = place.web ?: stringResource(R.string.not_provided_info),
                    fontSize = 18.sp,
                )
            }
            Row() {
                Icon(
                    imageVector = Icons.Outlined.Email,
                    contentDescription = null,
                    modifier = Modifier.padding(8.dp, 4.dp, 0.dp, 0.dp),
                )
                Text(
                    modifier = Modifier
                        .padding(8.dp, 0.dp),
                    text = place.email ?: stringResource(R.string.not_provided_info),
                    fontSize = 18.sp,
                )
            }
            Row() {
                Icon(
                    imageVector = Icons.Outlined.Phone,
                    contentDescription = null,
                    modifier = Modifier.padding(8.dp, 4.dp, 0.dp, 0.dp),
                )
                Text(
                    modifier = Modifier
                        .padding(8.dp, 0.dp),
                    text = place.phoneNumber ?: stringResource(R.string.not_provided_info),
                    fontSize = 18.sp,
                )
            }
            Text(
                text = stringResource(id = R.string.opening_hours),
                style = MaterialTheme.typography.h6,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(8.dp, 4.dp, 0.dp, 0.dp),
            )
        }
    }
}
