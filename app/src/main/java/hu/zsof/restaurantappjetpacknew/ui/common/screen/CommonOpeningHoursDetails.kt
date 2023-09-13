package hu.zsof.restaurantappjetpacknew.ui.common.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hu.zsof.restaurantappjetpacknew.R
import hu.zsof.restaurantappjetpacknew.model.BasePlace

@Composable
fun OpeningHoursDetails(
    place: BasePlace,
    openingHoursOpen: Boolean
) {
    if (openingHoursOpen) {
        Row(horizontalArrangement = Arrangement.Start, modifier = Modifier.padding(top = 8.dp)) {
            Text(
                modifier = Modifier
                    .padding(8.dp, 0.dp),
                text = stringResource(id = R.string.monday),
                fontSize = 16.sp,
                maxLines = 3,
            )
            Spacer(modifier = Modifier.weight(1f))
            Column {
                Row {
                    Text(
                        modifier = Modifier
                            .padding(8.dp, 0.dp),
                        text = place.openDetails.mondayOpen.ifEmpty {
                            stringResource(
                                id = R.string.not_provided_info
                            )
                        },
                        fontSize = 16.sp,
                        maxLines = 3,
                    )
                }
                Text(
                    modifier = Modifier
                        .padding(8.dp, 0.dp),
                    text = place.openDetails.mondayClose.ifEmpty {
                        stringResource(
                            id = R.string.not_provided_info
                        )
                    },
                    fontSize = 16.sp,
                    maxLines = 3,
                )
            }
        }
        Row(horizontalArrangement = Arrangement.Start, modifier = Modifier.padding(top = 8.dp)) {
            Text(
                modifier = Modifier
                    .padding(8.dp, 0.dp),
                text = stringResource(id = R.string.tuesday),
                fontSize = 16.sp,
                maxLines = 3,
            )
            Spacer(modifier = Modifier.weight(1f))
            Column {
                Row {
                    Text(
                        modifier = Modifier
                            .padding(8.dp, 0.dp),
                        text = place.openDetails.tuesdayOpen.ifEmpty {
                            stringResource(
                                id = R.string.not_provided_info
                            )
                        },
                        fontSize = 16.sp,
                        maxLines = 3,
                    )
                }
                Text(
                    modifier = Modifier
                        .padding(8.dp, 0.dp),
                    text = place.openDetails.tuesdayClose.ifEmpty {
                        stringResource(
                            id = R.string.not_provided_info
                        )
                    },
                    fontSize = 16.sp,
                    maxLines = 3,
                )
            }
        }
        Row(horizontalArrangement = Arrangement.Start, modifier = Modifier.padding(top = 8.dp)) {
            Text(
                modifier = Modifier
                    .padding(8.dp, 0.dp),
                text = stringResource(id = R.string.wednesday),
                fontSize = 16.sp,
                maxLines = 3,
            )
            Spacer(modifier = Modifier.weight(1f))
            Column {
                Row {
                    Text(
                        modifier = Modifier
                            .padding(8.dp, 0.dp),
                        text = place.openDetails.wednesdayOpen.ifEmpty {
                            stringResource(
                                id = R.string.not_provided_info
                            )
                        },
                        fontSize = 16.sp,
                        maxLines = 3,
                    )
                }
                Text(
                    modifier = Modifier
                        .padding(8.dp, 0.dp),
                    text = place.openDetails.wednesdayClose.ifEmpty {
                        stringResource(
                            id = R.string.not_provided_info
                        )
                    },
                    fontSize = 16.sp,
                    maxLines = 3,
                )
            }
        }
        Row(horizontalArrangement = Arrangement.Start, modifier = Modifier.padding(top = 8.dp)) {
            Text(
                modifier = Modifier
                    .padding(8.dp, 0.dp),
                text = stringResource(id = R.string.thursday),
                fontSize = 16.sp,
                maxLines = 3,
            )
            Spacer(modifier = Modifier.weight(1f))
            Column {
                Row {
                    Text(
                        modifier = Modifier
                            .padding(8.dp, 0.dp),
                        text = place.openDetails.thursdayOpen.ifEmpty {
                            stringResource(
                                id = R.string.not_provided_info
                            )
                        },
                        fontSize = 16.sp,
                        maxLines = 3,
                    )
                }
                Text(
                    modifier = Modifier
                        .padding(8.dp, 0.dp),
                    text = place.openDetails.thursdayClose.ifEmpty {
                        stringResource(
                            id = R.string.not_provided_info
                        )
                    },
                    fontSize = 16.sp,
                    maxLines = 3,
                )
            }
        }
        Row(horizontalArrangement = Arrangement.Start, modifier = Modifier.padding(top = 8.dp)) {
            Text(
                modifier = Modifier
                    .padding(8.dp, 0.dp),
                text = stringResource(id = R.string.friday),
                fontSize = 16.sp,
                maxLines = 3,
            )
            Spacer(modifier = Modifier.weight(1f))
            Column {
                Row {
                    Text(
                        modifier = Modifier
                            .padding(8.dp, 0.dp),
                        text = place.openDetails.fridayOpen.ifEmpty {
                            stringResource(
                                id = R.string.not_provided_info
                            )
                        },
                        fontSize = 16.sp,
                        maxLines = 3,
                    )
                }
                Text(
                    modifier = Modifier
                        .padding(8.dp, 0.dp),
                    text = place.openDetails.fridayClose.ifEmpty {
                        stringResource(
                            id = R.string.not_provided_info
                        )
                    },
                    fontSize = 16.sp,
                    maxLines = 3,
                )
            }
        }
        Row(horizontalArrangement = Arrangement.Start, modifier = Modifier.padding(top = 8.dp)) {
            Text(
                modifier = Modifier
                    .padding(8.dp, 0.dp),
                text = stringResource(id = R.string.saturday),
                fontSize = 16.sp,
                maxLines = 3,
            )
            Spacer(modifier = Modifier.weight(1f))
            Column {
                Row {
                    Text(
                        modifier = Modifier
                            .padding(8.dp, 0.dp),
                        text = place.openDetails.saturdayOpen.ifEmpty {
                            stringResource(
                                id = R.string.not_provided_info
                            )
                        },
                        fontSize = 16.sp,
                        maxLines = 3,
                    )
                }
                Text(
                    modifier = Modifier
                        .padding(8.dp, 0.dp),
                    text = place.openDetails.saturdayClose.ifEmpty {
                        stringResource(
                            id = R.string.not_provided_info
                        )
                    },
                    fontSize = 16.sp,
                    maxLines = 3,
                )
            }
        }
        Row(horizontalArrangement = Arrangement.Start, modifier = Modifier.padding(top = 8.dp)) {
            Text(
                modifier = Modifier
                    .padding(8.dp, 0.dp),
                text = stringResource(id = R.string.sunday),
                fontSize = 16.sp,
                maxLines = 3,
            )
            Spacer(modifier = Modifier.weight(1f))
            Column {
                Row {
                    Text(
                        modifier = Modifier
                            .padding(8.dp, 0.dp),
                        text = place.openDetails.sundayOpen.ifEmpty {
                            stringResource(
                                id = R.string.not_provided_info
                            )
                        },
                        fontSize = 16.sp,
                        maxLines = 3,
                    )
                }
                Text(
                    modifier = Modifier
                        .padding(8.dp, 0.dp),
                    text = place.openDetails.sundayClose.ifEmpty {
                        stringResource(
                            id = R.string.not_provided_info
                        )
                    },
                    fontSize = 16.sp,
                    maxLines = 3,
                )
            }
        }
    }
}
