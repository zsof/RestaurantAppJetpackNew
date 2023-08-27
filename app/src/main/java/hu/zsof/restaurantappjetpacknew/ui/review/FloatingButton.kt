package hu.zsof.restaurantappjetpacknew.ui.review

import android.content.Context
import android.widget.Toast
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import hu.zsof.restaurantappjetpacknew.R
import hu.zsof.restaurantappjetpacknew.model.enums.FabButton
import hu.zsof.restaurantappjetpacknew.network.repository.LocalDataStateService
import hu.zsof.restaurantappjetpacknew.ui.common.NormalTextField
import hu.zsof.restaurantappjetpacknew.ui.common.showToast

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

    if (viewModel.problemReportDialogOpen.value) {
        ProblemDialog(
            showProblemMessageDialog = viewModel.problemReportDialogOpen.value,
            onDismiss = { viewModel.problemReportDialogOpen.value = false },
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
                            viewModel.problemReportDialogOpen.value = true
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
        viewModel.problemReportDialogOpen.value = false
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
