package hu.zsof.restaurantappjetpacknew.ui.review

import android.widget.Toast
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.QuestionMark
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.Web
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import hu.zsof.restaurantappjetpacknew.R

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
            identifier = Identifier.REPORT.name,
            color = Color.Red,

        ),
        FabItem(
            icon = ImageBitmap.imageResource(id = R.drawable.ic_accept),
            identifier = Identifier.ACCEPT.name,
            color = Color.Green,

        ),
    )

    Scaffold(
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
                        text = placeInReview.name,
                        style = MaterialTheme.typography.h5,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Italic,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .padding(16.dp),
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
            "The place has accepted!",
            Toast.LENGTH_SHORT,
        ).show()
        acceptBtnIsClicked.value = true
    }

    Column(horizontalAlignment = Alignment.End) {
        if (transition.currentState == MultiFloatingState.EXPANDED) {
            items.forEach {
                Fab(item = it, onFabItemClick = { fabItem ->
                    when (fabItem.identifier) {
                        Identifier.ACCEPT.name -> {
                            if (!acceptBtnIsClicked.value) {
                                viewModel.acceptPlace(placeId)
                            } else {
                                Toast.makeText(
                                    context,
                                    "This place has already accepted!",
                                    Toast.LENGTH_SHORT,
                                ).show()
                            }
                        }
                        Identifier.REPORT.name -> {
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

enum class MultiFloatingState {
    EXPANDED,
    COLLAPSED,
}

enum class Identifier {
    ACCEPT,
    REPORT,
}

class FabItem(
    val icon: ImageBitmap,
    val identifier: String,
    val color: Color,
)
