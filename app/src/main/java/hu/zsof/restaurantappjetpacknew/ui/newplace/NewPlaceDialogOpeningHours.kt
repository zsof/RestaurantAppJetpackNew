package hu.zsof.restaurantappjetpacknew.ui.newplace

import android.app.TimePickerDialog
import android.icu.util.Calendar
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.KeyboardArrowDown
import androidx.compose.material.icons.outlined.KeyboardArrowUp
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hu.zsof.restaurantappjetpacknew.R

@Composable
fun OpeningHours(viewModel: NewPlaceDialogViewModel) {
    val openingHoursArrowIcon = if (viewModel.openingHoursOpen.value) {
        Icons.Outlined.KeyboardArrowDown
    } else {
        Icons.Outlined.KeyboardArrowUp
    }
    Column {
        Row {
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
        Row {
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
    }
}

@Composable
fun OpeningHoursItem(
    viewModel: NewPlaceDialogViewModel,
    openingDay: Int,
    openingCheckbox: MutableState<Boolean>,
) {
    Row {
        Text(
            text = stringResource(id = openingDay),
            style = TextStyle(fontSize = 14.sp),
        )
        Checkbox(
            checked = openingCheckbox.value,
            onCheckedChange = { checkedNew ->
                openingCheckbox.value = checkedNew
            },
        )
        Spacer(modifier = Modifier.weight(1f))
        Column {
            Text(
                text = stringResource(id = R.string.set_time),
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(vertical = 16.dp, horizontal = 4.dp)
                    .clickable(onClick = { viewModel.openSetTime.value = true }),
            )
        }
    }
}

@Composable
@ExperimentalMaterial3Api
fun TimePicker() {
    val context = LocalContext.current
    var showTimePicker = remember {
        mutableStateOf(false)
    }

    val mCalendar = Calendar.getInstance()
    val mHour = mCalendar[Calendar.HOUR_OF_DAY]
    val mMinute = mCalendar[Calendar.MINUTE]
    val mTime = remember { mutableStateOf("") }

    if (showTimePicker.value) {
        TimePickerDialog(
            context,
            {
                    _, hour: Int, minute: Int ->
                mTime.value = "$mHour:$mMinute"
            },
            mHour,
            mMinute,
            false,
        )
          /*  onCancel = { showTimePicker.value = false },
            onConfirm = {
                val cal = Calendar.getInstance()
                cal.set(Calendar.HOUR_OF_DAY, state.hour)
                cal.set(Calendar.MINUTE, state.minute)
                cal.isLenient = false
                snackScope.launch {
                    snackState.showSnackbar("Entered time: ${formatter.format(cal.time)}")
                }
                showTimePicker.value = false
            },*/
    }
}
