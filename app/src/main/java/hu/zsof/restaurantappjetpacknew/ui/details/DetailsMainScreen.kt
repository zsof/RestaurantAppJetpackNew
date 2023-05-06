package hu.zsof.restaurantappjetpacknew.ui.details

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import hu.zsof.restaurantappjetpacknew.R
import hu.zsof.restaurantappjetpacknew.ui.common.TextChip
import hu.zsof.restaurantappjetpacknew.util.Constants
import hu.zsof.restaurantappjetpacknew.util.extension.imageUrl

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DetailsMainScreen(
    viewModel: DetailsViewModel = hiltViewModel(),
    placeId: Long,
) {
    val place = viewModel.placeById.observeAsState().value
    LaunchedEffect(key1 = "Details") {
        viewModel.getPlaceById(placeId)
    }

 /*   if (viewModel.ratingDialogOpen.value) {
        RateDialog(viewModel = viewModel, onDismiss = { viewModel.ratingDialogOpen.value = false })
    }
*/
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
    ) {
        if (place != null) {
            AsyncImage(
                model = place.image.imageUrl(),
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
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(16.dp),
            )

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Icon(
                    imageVector = Icons.Filled.Star,
                    contentDescription = null,
                    modifier = Modifier
                        .size(34.dp)
                        .padding(2.dp, 4.dp, 0.dp, 0.dp),
                    tint = Color(0xFFFFC107),

                )
                Text(
                    text = place.rate.toString(),
                    style = TextStyle(
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Bold,
                    ),
                    modifier = Modifier
                        .padding(8.dp, 8.dp, 8.dp, 8.dp)
                        .clickable(onClick = {
                            if (!viewModel.getAppPreference<Boolean>(Constants.Prefs.USER_RATED)) {
                                viewModel.ratingDialogOpen.value = true
                            }
                        }),
                    fontSize = 18.sp,
                )
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth(),
            ) {
                FlowRow(horizontalArrangement = Arrangement.Center) {
                    place.filter.convertToMap().forEach {
                        if (it.value) {
                            TextChip(
                                isSelected = true,
                                text = it.key,
                            )
                        }
                    }
                }
            }

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

/*@Composable
fun RateDialog(viewModel: DetailsViewModel, onDismiss: () -> Unit) {
    if (viewModel.ratingDialogOpen.value) {
        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(
                dismissOnClickOutside = true,
                dismissOnBackPress = true,
                usePlatformDefaultWidth = false,
            ),
        ) {
            Surface(
                modifier = Modifier
                    .padding(32.dp)
                    .wrapContentHeight(),
                shape = RoundedCornerShape(8.dp),
            ) {
                Column {
                }
            }
        }
    }
}*/
