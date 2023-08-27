package hu.zsof.restaurantappjetpacknew.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ReportProblem
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.Web
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import hu.zsof.restaurantappjetpacknew.model.BasePlace
import hu.zsof.restaurantappjetpacknew.model.Place
import hu.zsof.restaurantappjetpacknew.model.PlaceInReview
import hu.zsof.restaurantappjetpacknew.model.enums.PlaceType
import hu.zsof.restaurantappjetpacknew.network.repository.LocalDataStateService
import hu.zsof.restaurantappjetpacknew.ui.review.FabItem
import hu.zsof.restaurantappjetpacknew.ui.review.MultiFloatingButton
import hu.zsof.restaurantappjetpacknew.ui.review.MultiFloatingState
import hu.zsof.restaurantappjetpacknew.ui.review.ReviewPlaceViewModel
import hu.zsof.restaurantappjetpacknew.util.extension.imageUrl

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CommonDetailsScreen(
    placeId: Long,
    onEditPlaceInReviewClick: ((Long) -> Unit)? = null,
    place: BasePlace?,
    showProblemDialog: MutableState<Boolean>? = null,
    placeType: PlaceType = PlaceType.PLACE,
    isPlaceByOwner: Boolean,
    isUserRated: Boolean = false,
    ratingDialogOpen: MutableState<Boolean>? = null,
    multiFloatingState: MutableState<MultiFloatingState>? = null,
    fabItems: List<FabItem> = emptyList(),
    viewModel: ReviewPlaceViewModel = hiltViewModel(),
) {
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
                            .align(Alignment.CenterHorizontally)
                            .padding(16.dp)
                            .clip(RoundedCornerShape(8.dp)),
                    )

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Row(horizontalArrangement = Arrangement.Center) {
                            Text(
                                text = place.name,
                                style = MaterialTheme.typography.headlineLarge,
                                fontWeight = FontWeight.Bold,
                                fontStyle = FontStyle.Italic,
                                modifier = Modifier
                                    .padding(16.dp),
                            )

                            if (place is PlaceInReview && !place.problem.isNullOrEmpty()) {
                                IconButton(onClick = {
                                    showProblemDialog?.value = true
                                }, modifier = Modifier.padding(top = 8.dp)) {
                                    Icon(
                                        imageVector = Icons.Filled.ReportProblem,
                                        contentDescription = null,
                                    )
                                }
                            }
                            if (isPlaceByOwner) {
                                IconButton(onClick = {
                                    if (onEditPlaceInReviewClick != null) {
                                        onEditPlaceInReviewClick(placeId)
                                    }

                                    if (placeType == PlaceType.PLACE) {
                                        LocalDataStateService.place = place as Place
                                    } else if (placeType == PlaceType.PLACE_BY_OWNER) {
                                        LocalDataStateService.placeInReview = place as PlaceInReview
                                    }
                                }, modifier = Modifier.padding(top = 8.dp)) {
                                    Icon(
                                        imageVector = Icons.Filled.Edit,
                                        contentDescription = null,
                                    )
                                }
                            }
                        }
                    }

                    if (place is Place) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center,
                        ) {
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
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurfaceVariant,
                                modifier = Modifier
                                    .padding(8.dp, 8.dp, 8.dp, 8.dp)
                                    .clickable(onClick = {
                                        if (isUserRated.not()) {
                                            ratingDialogOpen?.value = true
                                        }
                                    }),
                                fontSize = 18.sp,
                            )
                        }
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
                            text = place.email
                                ?: stringResource(R.string.not_provided_info),
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
                            text = place.phoneNumber
                                ?: stringResource(R.string.not_provided_info),
                            fontSize = 18.sp,
                        )
                    }
                    Text(
                        text = stringResource(id = R.string.opening_hours),
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(8.dp, 4.dp, 0.dp, 0.dp),
                    )
                }
            }
        },
    )
}
