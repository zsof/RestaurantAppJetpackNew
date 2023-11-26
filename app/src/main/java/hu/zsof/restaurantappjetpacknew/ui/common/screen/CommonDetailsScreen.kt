package hu.zsof.restaurantappjetpacknew.ui.common.screen

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.ReportProblem
import androidx.compose.material.icons.outlined.Attachment
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.PushPin
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import hu.zsof.restaurantappjetpacknew.R
import hu.zsof.restaurantappjetpacknew.model.BasePlace
import hu.zsof.restaurantappjetpacknew.model.Place
import hu.zsof.restaurantappjetpacknew.model.PlaceInReview
import hu.zsof.restaurantappjetpacknew.model.enums.PlaceType
import hu.zsof.restaurantappjetpacknew.model.enums.Type
import hu.zsof.restaurantappjetpacknew.module.AppState
import hu.zsof.restaurantappjetpacknew.ui.common.button.TextChip
import hu.zsof.restaurantappjetpacknew.ui.review.FabItem
import hu.zsof.restaurantappjetpacknew.ui.review.MultiFloatingButton
import hu.zsof.restaurantappjetpacknew.ui.review.MultiFloatingState
import hu.zsof.restaurantappjetpacknew.ui.review.ReviewPlaceViewModel
import hu.zsof.restaurantappjetpacknew.util.Constants
import hu.zsof.restaurantappjetpacknew.util.extension.imageUrl
import hu.zsof.restaurantappjetpacknew.util.extension.showToast

@OptIn(ExperimentalLayoutApi::class, ExperimentalPermissionsApi::class)
@Composable
fun CommonDetailsScreen(
    placeId: Long,
    onEditPlaceClick: ((Long) -> Unit)? = null,
    onEditImageClick: (() -> Unit)? = null,
    place: BasePlace?,
    showProblemDialog: MutableState<Boolean>? = null,
    placeType: PlaceType = PlaceType.PLACE,
    isPlaceByOwner: Boolean,
    multiFloatingState: MutableState<MultiFloatingState>? = null,
    fabItems: List<FabItem> = emptyList(),
    reviewPlaceViewModel: ReviewPlaceViewModel = hiltViewModel(),
    openingHoursOpen: MutableState<Boolean>,
    selectedImage: Uri? = null,
    onPlaceAccepted: (() -> Unit)? = null,
    permissionStateGallery: PermissionState? = null
) {
    val openingHoursArrowIcon = if (openingHoursOpen.value) {
        Icons.Outlined.KeyboardArrowUp
    } else {
        Icons.Outlined.KeyboardArrowDown
    }

    val context = LocalContext.current

    val sharedPref =
        context.getSharedPreferences(Constants.Prefs.SHARED_PREFERENCES, Context.MODE_PRIVATE)

    val drawableResource = if (sharedPref.getBoolean(Constants.Prefs.DARK_MODE, false))
        R.drawable.loading_blue
    else R.drawable.loading_yellow

    if (showProblemDialog != null && showProblemDialog.value) {
        AlertDialog(
            onDismissRequest = { showProblemDialog.value = false },
            confirmButton = {
                Button(onClick = { showProblemDialog.value = false }) {
                    Text(stringResource(R.string.ok_btn))
                }
            },
            title = {
                Text(
                    text = stringResource(R.string.problem_with_place),
                    textAlign = TextAlign.Start,
                    fontWeight = FontWeight.Bold
                )
            },
            text = {
                place?.problem?.substringAfter('"')
                    ?.let {
                        Text(
                            text = it.substringBeforeLast('"'),
                        )
                    }
            },
        )
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
                    viewModel = reviewPlaceViewModel,
                    placeId = placeId,
                    onPlaceAccepted = {
                        if (onPlaceAccepted != null) {
                            onPlaceAccepted()
                        }
                    }
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
                    when {
                        selectedImage != null ->
                            AsyncImage(
                                model = selectedImage,
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                modifier = Modifier
                                    .wrapContentSize()
                                    .wrapContentHeight()
                                    .wrapContentWidth()
                                    .align(CenterHorizontally)
                                    .padding(16.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .aspectRatio(1280f / 847f)
                                    .clickable {
                                        if (isPlaceByOwner) {
                                            if (place is Place) {
                                                handlePhotoClick(
                                                    onEditImageClick,
                                                    permissionStateGallery
                                                )
                                            } else showToast(
                                                context,
                                                context.getString(R.string.cannot_upload_photo_to_place_in_review)
                                            )
                                        } else showToast(
                                            context,
                                            context.getString(R.string.no_permission_to_upload_image)
                                        )
                                    },
                            )

                        place.image != null ->
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
                                    .clip(RoundedCornerShape(8.dp))
                                    .aspectRatio(1280f / 847f)
                                    .clickable {
                                        if (isPlaceByOwner) {
                                            if (place is Place) {
                                                handlePhotoClick(
                                                    onEditImageClick,
                                                    permissionStateGallery
                                                )
                                            } else showToast(
                                                context,
                                                context.getString(R.string.cannot_upload_photo_to_place_in_review)
                                            )
                                        } else showToast(
                                            context,
                                            context.getString(R.string.no_permission_to_upload_image)
                                        )
                                    },
                                placeholder = painterResource(id = drawableResource),
                            )

                        else ->
                            Icon(
                                imageVector = Icons.Filled.CameraAlt,
                                contentDescription = null,
                                modifier = Modifier
                                    .size(120.dp)
                                    .align(CenterHorizontally)
                                    .clickable {
                                        if (isPlaceByOwner) {
                                            if (place is Place) {
                                                handlePhotoClick(
                                                    onEditImageClick,
                                                    permissionStateGallery
                                                )
                                            } else showToast(
                                                context,
                                                context.getString(R.string.cannot_upload_photo_to_place_in_review)
                                            )
                                        } else showToast(
                                            context,
                                            context.getString(R.string.no_permission_to_upload_image)
                                        )
                                    },
                                tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            )
                    }

                    Column(
                        horizontalAlignment = CenterHorizontally,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = place.name,
                                style = MaterialTheme.typography.headlineLarge,
                                fontWeight = FontWeight.Bold,
                                fontStyle = FontStyle.Italic,
                                modifier = Modifier
                                    .padding(16.dp, 8.dp, 4.dp, 8.dp)
                                    .weight(1f, fill = false),
                            )

                            if (!place.problem.isNullOrEmpty() && (place is Place || place is PlaceInReview)) {
                                IconButton(onClick = {
                                    showProblemDialog?.value = true
                                }, modifier = Modifier.padding(4.dp, 8.dp, 4.dp, 8.dp)) {
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

                                }, modifier = Modifier.padding(4.dp, 8.dp, 4.dp, 8.dp)) {
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
                        modifier = Modifier
                            .align(CenterHorizontally)
                            .width(128.dp)
                            .height(56.dp)
                            .fillMaxWidth(),
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
                    Row {
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
                    Row {
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

@OptIn(ExperimentalPermissionsApi::class)
fun handlePhotoClick(
    onEditImageClick: (() -> Unit)?,
    permissionStateGallery: PermissionState? = null
) {
    if (onEditImageClick != null) {
        permissionStateGallery?.launchPermissionRequest()
        onEditImageClick()
    }
}
