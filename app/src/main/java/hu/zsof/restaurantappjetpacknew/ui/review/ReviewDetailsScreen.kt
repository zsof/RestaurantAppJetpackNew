package hu.zsof.restaurantappjetpacknew.ui.review

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.filled.ReportProblem
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.Web
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import hu.zsof.restaurantappjetpacknew.R
import hu.zsof.restaurantappjetpacknew.model.enums.FabButton
import hu.zsof.restaurantappjetpacknew.network.repository.LocalDataStateService
import hu.zsof.restaurantappjetpacknew.ui.common.NormalTextField
import hu.zsof.restaurantappjetpacknew.ui.common.TextChip
import hu.zsof.restaurantappjetpacknew.ui.common.showToast
import hu.zsof.restaurantappjetpacknew.util.extension.imageUrl

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ReviewDetailsScreen(
    viewModel: ReviewPlaceViewModel = hiltViewModel(),
    placeId: Long,

) {
    val placeInReview = viewModel.reviewPlaceById.observeAsState().value
    LaunchedEffect(key1 = "ReviewDetails") {
        viewModel.getReviewPlaceById(placeId)
    }

    var multiFloatingState by remember {
        mutableStateOf(MultiFloatingState.COLLAPSED)
    }
    val items = listOf(
        FabItem(
            icon = ImageBitmap.imageResource(id = R.drawable.ic_report_problem),
            identifier = FabButton.REPORT.name,
            color = Color.Red,

        ),
        FabItem(
            icon = ImageBitmap.imageResource(id = R.drawable.ic_accept),
            identifier = FabButton.ACCEPT.name,
            color = Color.Green,

        ),
    )

    val showProblemDialog = remember {
        mutableStateOf(false)
    }

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
                placeInReview?.problem?.substringAfter('"')
                    ?.let { Text(text = it.substringBeforeLast('"')) }
            },
        )
    }

    Scaffold(
        modifier = Modifier.padding(bottom = 36.dp),
        floatingActionButton = {
            MultiFloatingButton(
                multiFloatingState = multiFloatingState,
                onMultiFabStateChange = {
                    multiFloatingState = it
                },
                items = items,
                viewModel = viewModel,
                placeId = placeId,
            )
        },

        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
            ) {
                if (placeInReview != null) {
                    AsyncImage(
                        model = placeInReview.image.imageUrl(),
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
                                text = placeInReview.name,
                                style = MaterialTheme.typography.h5,
                                fontWeight = FontWeight.Bold,
                                fontStyle = FontStyle.Italic,
                                modifier = Modifier
                                    .padding(16.dp),
                            )

                            if (!placeInReview.problem.isNullOrEmpty()) {
                                IconButton(onClick = {
                                    showProblemDialog.value = true
                                }, modifier = Modifier.padding(top = 8.dp)) {
                                    Icon(
                                        imageVector = Icons.Filled.ReportProblem,
                                        contentDescription = null,
                                    )
                                }
                            }
                        }
                    }

                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth(),
                    ) {
                        FlowRow(horizontalArrangement = Arrangement.Center) {
                            placeInReview.filter.convertToMap().forEach {
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
                            text = placeInReview.address,
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
                            text = placeInReview.web ?: stringResource(R.string.not_provided_info),
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
                            text = placeInReview.email
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
                            text = placeInReview.phoneNumber
                                ?: stringResource(R.string.not_provided_info),
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
        },
    )
}

@Composable
fun MultiFloatingButton(
    multiFloatingState: MultiFloatingState,
    onMultiFabStateChange: (MultiFloatingState) -> Unit,
    items: List<FabItem>,
    viewModel: ReviewPlaceViewModel,
    placeId: Long,
) {
    val context = LocalContext.current
    val transition = updateTransition(targetState = multiFloatingState, label = "transition")
    val rotate by transition.animateFloat(label = "rotate") {
        if (it == MultiFloatingState.EXPANDED) 180f else 0f
    }

    val acceptBtnIsClicked = remember {
        mutableStateOf(false)
    }
    val isPlaceAccept = viewModel.isPlaceAccepted.observeAsState()
    if (isPlaceAccept.value == true && !acceptBtnIsClicked.value) {
        Toast.makeText(
            context,
            "The place has accepted successfully!",
            Toast.LENGTH_SHORT,
        ).show()
        acceptBtnIsClicked.value = true
    }

    if (viewModel.problemDialogOpen.value) {
        ProblemDialog(
            showProblemMessageDialog = viewModel.problemDialogOpen.value,
            onDismiss = { viewModel.problemDialogOpen.value = false },
            viewModel = viewModel,
            placeId = placeId,
            context = context,
        )
    }

    Column(horizontalAlignment = Alignment.End) {
        if (transition.currentState == MultiFloatingState.EXPANDED) {
            items.forEach {
                Fab(item = it, onFabItemClick = { fabItem ->
                    when (fabItem.identifier) {
                        FabButton.ACCEPT.name -> {
                            if (!acceptBtnIsClicked.value) {
                                if (LocalDataStateService.isModifiedPlace) {
                                    viewModel.acceptPlace(placeId, true)
                                } else {
                                    viewModel.acceptPlace(placeId, false)
                                }
                            } else {
                                showToast(context, "This place has already accepted!")
                            }
                        }

                        FabButton.REPORT.name -> {
                            viewModel.problemDialogOpen.value = true
                        }
                    }
                }, color = it.color)
                Spacer(modifier = Modifier.size(20.dp))
            }
        }
        FloatingActionButton(
            onClick = {
                onMultiFabStateChange(
                    if (transition.currentState == MultiFloatingState.EXPANDED) {
                        MultiFloatingState.COLLAPSED
                    } else {
                        MultiFloatingState.EXPANDED
                    },
                )
            },
            modifier = Modifier.padding(bottom = 44.dp),
        ) {
            Icon(
                imageVector = Icons.Filled.QuestionMark,
                contentDescription = null,
                modifier = Modifier.rotate(rotate),
            )
        }
    }
}

@Composable
fun Fab(item: FabItem, onFabItemClick: (FabItem) -> Unit, color: Color) {
    Canvas(
        modifier = Modifier
            .size(32.dp)
            .padding(end = 20.dp)
            .clickable(
                interactionSource = MutableInteractionSource(),
                onClick = {
                    onFabItemClick.invoke(item)
                },
                indication = rememberRipple(
                    bounded = false,
                    radius = 20.dp,
                ),
            ),
    ) {
        drawCircle(
            color = color,
            radius = 64f,
        )

        drawImage(
            image = item.icon,
            topLeft = Offset(
                center.x - (item.icon.width / 2),
                center.y - (item.icon.width / 2),
            ),
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProblemDialog(
    onDismiss: () -> Unit,
    viewModel: ReviewPlaceViewModel,
    showProblemMessageDialog: Boolean,
    placeId: Long,
    context: Context,
) {
    val isPlaceReported = viewModel.isPlaceReported.observeAsState()
    if (isPlaceReported.value == true) {
        showToast(context, "This place has reported successfully!")
        viewModel.problemDialogOpen.value = false
    }

    if (showProblemMessageDialog) {
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
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(all = 16.dp),
                ) {
                    NormalTextField(
                        value = viewModel.problemMessage.value,
                        label = stringResource(id = R.string.problem_message),
                        onValueChange = { newValue ->
                            viewModel.problemMessage.value = newValue
                        },
                        isError = viewModel.problemMessageError.value,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Text,
                            imeAction = ImeAction.Done,
                        ),
                        leadingIcon = {},
                        trailingIcon = { },
                        onDone = { },
                        placeholder = stringResource(id = R.string.problem_hint),
                    )

                    Row(modifier = Modifier.padding(top = 16.dp)) {
                        Button(onClick = onDismiss, modifier = Modifier.padding(16.dp, 0.dp)) {
                            Text(text = stringResource(R.string.cancel_btn))
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Button(onClick = {
                            if (LocalDataStateService.isModifiedPlace) {
                                viewModel.reportProblem(placeId, true)
                            } else {
                                viewModel.reportProblem(placeId, false)
                            }
                        }, modifier = Modifier.padding(16.dp, 0.dp)) {
                            Text(text = stringResource(R.string.send))
                        }
                    }
                }
            }
        }
    }
}

enum class MultiFloatingState {
    EXPANDED,
    COLLAPSED,
}

class FabItem(
    val icon: ImageBitmap,
    val identifier: String,
    val color: Color,
)
