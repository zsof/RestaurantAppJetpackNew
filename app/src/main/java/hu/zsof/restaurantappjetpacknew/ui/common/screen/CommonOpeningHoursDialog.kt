package hu.zsof.restaurantappjetpacknew.ui.common.screen

import android.app.TimePickerDialog
import android.icu.util.Calendar
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import hu.zsof.restaurantappjetpacknew.R
import hu.zsof.restaurantappjetpacknew.ui.newplace.NewPlaceDialogViewModel

@Composable
fun OpeningHours(viewModel: NewPlaceDialogViewModel = hiltViewModel()) {
    val openingHoursArrowIcon = if (viewModel.openingHoursOpen.value) {
        Icons.Outlined.KeyboardArrowUp
    } else {
        Icons.Outlined.KeyboardArrowDown
    }

    Column(modifier = Modifier.padding(start = 10.dp)) {
        Row(
            modifier = Modifier.clickable {
                viewModel.openingHoursOpen.value = !viewModel.openingHoursOpen.value
            },
        ) {
            Text(
                text = stringResource(id = R.string.opening_hours),
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
            )
            Spacer(modifier = Modifier.weight(1f))
            Icon(
                imageVector = openingHoursArrowIcon,
                contentDescription = null,
            )
        }
        if (viewModel.openingHoursOpen.value) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = stringResource(id = R.string.same_opening_hours_everyday),
                    style = TextStyle(fontSize = 14.sp, fontStyle = FontStyle.Italic),
                )
                Spacer(modifier = Modifier.weight(1f))
                Checkbox(
                    checked = viewModel.sameOpeningHoursEveryday.value,
                    onCheckedChange = { checkedNew ->
                        viewModel.sameOpeningHoursEveryday.value = checkedNew
                    },
                )
            }
            if (viewModel.sameOpeningHoursEveryday.value) {
                OpeningHoursItem(
                    basicOpen = R.string.basic_open_hour,
                    basicClose = R.string.basic_close_hour,
                    selectedOpenText = viewModel.basicOpen,
                    selectedCloseText = viewModel.basicClose,
                )
            } else {
                OpeningHoursItem(
                    openingDay = R.string.monday,
                    openingCheckbox = viewModel.mondayCheckbox,
                    selectedOpenText = viewModel.mondayOpen,
                    selectedCloseText = viewModel.mondayClose,
                )
                OpeningHoursItem(
                    openingDay = R.string.tuesday,
                    openingCheckbox = viewModel.tuesdayCheckbox,
                    selectedOpenText = viewModel.tuesdayOpen,
                    selectedCloseText = viewModel.tuesdayClose,
                )
                OpeningHoursItem(
                    openingDay = R.string.wednesday,
                    openingCheckbox = viewModel.wednesdayCheckbox,
                    selectedOpenText = viewModel.wednesdayOpen,
                    selectedCloseText = viewModel.wednesdayClose,
                )
                OpeningHoursItem(
                    openingDay = R.string.thursday,
                    openingCheckbox = viewModel.thursdayCheckbox,
                    selectedOpenText = viewModel.thursdayOpen,
                    selectedCloseText = viewModel.thursdayClose,
                )
                OpeningHoursItem(
                    openingDay = R.string.friday,
                    openingCheckbox = viewModel.fridayCheckbox,
                    selectedOpenText = viewModel.fridayOpen,
                    selectedCloseText = viewModel.fridayClose,
                )
                OpeningHoursItem(
                    openingDay = R.string.saturday,
                    openingCheckbox = viewModel.saturdayCheckbox,
                    selectedOpenText = viewModel.saturdayOpen,
                    selectedCloseText = viewModel.saturdayClose,
                )
                OpeningHoursItem(
                    openingDay = R.string.sunday,
                    openingCheckbox = viewModel.sundayCheckbox,
                    selectedOpenText = viewModel.sundayOpen,
                    selectedCloseText = viewModel.sundayClose,
                )
            }
        }
    }
}

@Composable
fun OpeningHoursItem(
    viewModel: NewPlaceDialogViewModel = hiltViewModel(),
    openingDay: Int = 0,
    basicOpen: Int = 0,
    basicClose: Int = 0,
    openingCheckbox: MutableState<Boolean> = mutableStateOf(false),
    selectedOpenText: MutableState<String>,
    selectedCloseText: MutableState<String>,
) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    val hour = calendar[Calendar.HOUR_OF_DAY]
    val minute = calendar[Calendar.MINUTE]

    if (openingCheckbox.value.not() && openingDay != 0) {
        selectedOpenText.value = stringResource(id = R.string.closed)
        selectedCloseText.value = stringResource(id = R.string.closed)
    }

    val timepicker = TimePickerDialog(
        context,
        { _, selectedHour: Int, selectedMinute: Int ->
            if (viewModel.isOpenHourSet.value) {
                selectedOpenText.value = "$selectedHour:$selectedMinute"
            } else {
                selectedCloseText.value = "$selectedHour:$selectedMinute"
            }
        },
        hour,
        minute,
        false,
    )


    Row(verticalAlignment = Alignment.Top, modifier = Modifier.padding(start = 10.dp)) {
        if (openingDay == 0) {
            Column {
                Text(
                    text = stringResource(id = basicOpen),
                    style = TextStyle(fontSize = 14.sp),
                    modifier = Modifier.padding(top = 16.dp, end = 12.dp),
                )
                Text(
                    text = stringResource(id = basicClose),
                    style = TextStyle(fontSize = 14.sp),
                    modifier = Modifier.padding(top = 16.dp, end = 12.dp),
                )
            }
        } else {
            Text(
                text = stringResource(id = openingDay),
                style = TextStyle(fontSize = 14.sp),
                modifier = Modifier.padding(top = 16.dp, end = 12.dp),
            )
            Checkbox(
                checked = openingCheckbox.value,
                onCheckedChange = { checkedNew ->
                    openingCheckbox.value = checkedNew
                },
            )
        }

        Spacer(modifier = Modifier.weight(1f))
        Column() {
            val openTimeText = selectedOpenText.value.ifEmpty {
                stringResource(id = R.string.set_time)
            }
            val closeTimeText = selectedCloseText.value.ifEmpty {
                stringResource(id = R.string.set_time)
            }
            Text(
                text = openTimeText,
                fontSize = 14.sp,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 16.dp, bottom = 2.dp, end = 8.dp)
                    .clickable(
                        enabled = openingCheckbox.value || openingDay == 0,
                        onClick = {
                            viewModel.isOpenHourSet.value = true
                            timepicker.show()

                        }),
            )
            if (openingDay == 0) {
                Spacer(modifier = Modifier.height(12.dp))
            }
            Text(
                text = closeTimeText,
                fontSize = 14.sp,
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 2.dp, end = 8.dp)
                    .clickable(
                        enabled = openingCheckbox.value || openingDay == 0,
                        onClick = {
                            viewModel.isOpenHourSet.value = false
                            timepicker.show()
                        }),
            )
        }
    }
}
