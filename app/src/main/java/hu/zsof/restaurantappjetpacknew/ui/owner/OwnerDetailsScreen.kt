package hu.zsof.restaurantappjetpacknew.ui.owner

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ReportProblem
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.material.icons.outlined.Web
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import hu.zsof.restaurantappjetpacknew.ui.common.TextChip

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun OwnerDetailsScreen(
    viewModel: OwnerPlaceViewModel = hiltViewModel(),
    placeId: Long,

) {
    val placeInReview = viewModel.reviewPlaceById.observeAsState().value
    LaunchedEffect(key1 = "ReviewDetails") {
        viewModel.getReviewPlaceById(placeId)
    }

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
