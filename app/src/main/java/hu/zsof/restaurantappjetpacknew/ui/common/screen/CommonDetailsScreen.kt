package hu.zsof.restaurantappjetpacknew.ui.common.screen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ReportProblem
import androidx.compose.material.icons.outlined.Attachment
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.PushPin
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.ImageLoader
import coil.compose.AsyncImage
import hu.zsof.restaurantappjetpacknew.R
import hu.zsof.restaurantappjetpacknew.model.BasePlace
import hu.zsof.restaurantappjetpacknew.model.Place
import hu.zsof.restaurantappjetpacknew.model.PlaceInReview
import hu.zsof.restaurantappjetpacknew.model.enums.PlaceType
import hu.zsof.restaurantappjetpacknew.model.enums.Type
import hu.zsof.restaurantappjetpacknew.module.AppState
import hu.zsof.restaurantappjetpacknew.module.NetworkModule
import hu.zsof.restaurantappjetpacknew.ui.common.button.TextChip
import hu.zsof.restaurantappjetpacknew.ui.review.FabItem
import hu.zsof.restaurantappjetpacknew.ui.review.MultiFloatingButton
import hu.zsof.restaurantappjetpacknew.ui.review.MultiFloatingState
import hu.zsof.restaurantappjetpacknew.ui.review.ReviewPlaceViewModel
import hu.zsof.restaurantappjetpacknew.util.extension.imageUrl
import okhttp3.OkHttpClient

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CommonDetailsScreen(
    placeId: Long,
    onEditPlaceClick: ((Long) -> Unit)? = null,
    place: BasePlace?,
    showProblemDialog: MutableState<Boolean>? = null,
    placeType: PlaceType = PlaceType.PLACE,
    isPlaceByOwner: Boolean,
    multiFloatingState: MutableState<MultiFloatingState>? = null,
    fabItems: List<FabItem> = emptyList(),
    viewModel: ReviewPlaceViewModel = hiltViewModel(),
    openingHoursOpen: MutableState<Boolean>
) {
    val openingHoursArrowIcon = if (openingHoursOpen.value) {
        Icons.Outlined.KeyboardArrowUp
    } else {
        Icons.Outlined.KeyboardArrowDown
    }

    //Add request header to get image - to jump the ngrok website and load the image
    val context = LocalContext.current
    val imageLoader = ImageLoader.Builder(context)
        .okHttpClient {
            OkHttpClient.Builder()
                .addInterceptor(NetworkModule.AuthInterceptor(context))
                .build()
        }
        .build()

    if (showProblemDialog != null) {
        if (showProblemDialog.value) {
            AlertDialog(
                onDismissRequest = { showProblemDialog.value = false },
                confirmButton = {
                    Button(onClick = { showProblemDialog.value = false }) {
                        Text(stringResource(R.string.ok_btn))
                    }
                },
                // TODO valamiért a "" is visszajön backendtpl "szöveg"  <-- így, ezt meg kellene szüntetni
                text = {
                    if (place is PlaceInReview) {
                        place.problem?.substringAfter('"')
                            ?.let { Text(text = it.substringBeforeLast('"')) }
                    }
                },
            )
        }
    }

    Scaffold(
        modifier = Modifier.padding(bottom = 36.dp),
        floatingActionButton = {
            if (placeType == PlaceType.PLACE_IN_REVIEW && multiFloatingState != null) {
                MultiFloatingButton(
                    multiFloatingState = multiFloatingState.value,
                    onMultiFabStateChange = {
                        multiFloatingState.value = it
                    },
                    items = fabItems,
                    viewModel = viewModel,
                    placeId = placeId,
                )
            }
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(bottom = 32.dp),
            ) {
                if (place != null) {
                    if (place.image.imageUrl().isEmpty().not()) {
                        AsyncImage(
                            model = place.image.imageUrl(),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .wrapContentSize()
                                .wrapContentHeight()
                                .wrapContentWidth()
                                .align(CenterHorizontally)
                                .padding(16.dp)
                                .clip(RoundedCornerShape(8.dp)),
                            imageLoader = imageLoader
                        )

                    }
                    Column(
                        horizontalAlignment = CenterHorizontally,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Row(horizontalArrangement = Arrangement.Center) {
                            Text(
                                text = place.name,
                                style = MaterialTheme.typography.headlineLarge,
                                fontWeight = FontWeight.Bold,
                                fontStyle = FontStyle.Italic,
                                modifier = Modifier
                                    .padding(16.dp, 16.dp, 16.dp, 8.dp),
                            )

                            if (place is PlaceInReview && !place.problem.isNullOrEmpty()) {
                                IconButton(onClick = {
                                    showProblemDialog?.value = true
                                }, modifier = Modifier.padding(top = 16.dp)) {
                                    Icon(
                                        imageVector = Icons.Filled.ReportProblem,
                                        contentDescription = null,
                                    )
                                }
                            }
                            if (isPlaceByOwner && place is Place) {
                                IconButton(onClick = {
                                    AppState.place.postValue(place)
                                    if (onEditPlaceClick != null) {
                                        onEditPlaceClick(placeId)
                                    }

                                }, modifier = Modifier.padding(top = 16.dp)) {
                                    Icon(
                                        imageVector = Icons.Filled.Edit,
                                        contentDescription = null,
                                    )
                                }
                            }
                        }
                    }

                    TextChip(
                        isSelected = true,
                        text = Type.getByType(place.type),
                        modifier = Modifier.align(CenterHorizontally),
                        shouldShowIcon = false
                    )

                    Column(
                        horizontalAlignment = CenterHorizontally,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 8.dp),
                    ) {
                        FlowRow(horizontalArrangement = Arrangement.Center) {
                            place.filter.convertToMap().forEach {
                                if (it.value) {
                                    TextChip(
                                        isSelected = true,
                                        text = stringResource(id = it.key),
                                    )
                                }
                            }
                        }
                    }

                    Row(horizontalArrangement = Arrangement.Start) {
                        Icon(
                            imageVector = Icons.Outlined.PushPin,
                            contentDescription = null,
                            modifier = Modifier.padding(8.dp, 4.dp, 0.dp, 8.dp),
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
                            imageVector = Icons.Outlined.Attachment,
                            contentDescription = null,
                            modifier = Modifier.padding(8.dp, 4.dp, 0.dp, 8.dp),
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
                            modifier = Modifier.padding(8.dp, 4.dp, 0.dp, 8.dp),
                        )
                        Text(
                            modifier = Modifier
                                .padding(8.dp, 0.dp),
                            text = place.email
                                ?: stringResource(R.string.not_provided_info),
                            fontSize = 18.sp,
                        )
                    }
                    Row() {
                        Icon(
                            imageVector = Icons.Outlined.Phone,
                            contentDescription = null,
                            modifier = Modifier.padding(8.dp, 4.dp, 0.dp, 8.dp),
                        )
                        Text(
                            modifier = Modifier
                                .padding(8.dp, 0.dp),
                            text = place.phoneNumber
                                ?: stringResource(R.string.not_provided_info),
                            fontSize = 18.sp,
                        )
                    }
                    Row(
                        modifier = Modifier
                            .clickable {
                                openingHoursOpen.value = !openingHoursOpen.value
                            }
                            .padding(8.dp, 8.dp, 8.dp, 0.dp),
                    ) {
                        Text(
                            text = stringResource(id = R.string.opening_hours),
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                        )
                        Spacer(modifier = Modifier.weight(1f))
                        Icon(
                            imageVector = openingHoursArrowIcon,
                            contentDescription = null,
                            modifier = Modifier.size(32.dp)
                        )
                    }
                    OpeningHoursDetails(place = place, openingHoursOpen = openingHoursOpen.value)
                }
            }
        },
    )
}
