package hu.zsof.restaurantappjetpacknew.ui.common.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import hu.zsof.restaurantappjetpacknew.R
import hu.zsof.restaurantappjetpacknew.ui.newplace.NewPlaceDialogViewModel

@ExperimentalMaterial3Api
@Composable
fun CommonPlaceFilter(
    viewModel: NewPlaceDialogViewModel = hiltViewModel(),
) {
    Row() {
        Column(horizontalAlignment = Alignment.Start) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = viewModel.glutenFreeChecked.value,
                    onCheckedChange = { checkedNew ->
                        viewModel.glutenFreeChecked.value = checkedNew
                    },
                )
                Text(
                    modifier = Modifier.padding(start = 2.dp),
                    text = stringResource(id = R.string.gluten_free),
                    style = TextStyle(fontSize = 14.sp),
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = viewModel.lactoseFreeChecked.value,
                    onCheckedChange = { checkedNew ->
                        viewModel.lactoseFreeChecked.value = checkedNew
                    },
                )
                Text(
                    modifier = Modifier.padding(start = 2.dp),
                    text = stringResource(id = R.string.lactose_free),
                    style = TextStyle(fontSize = 14.sp),
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = viewModel.vegetarianChecked.value,
                    onCheckedChange = { checkedNew ->
                        viewModel.vegetarianChecked.value = checkedNew
                    },
                )
                Text(
                    modifier = Modifier.padding(start = 2.dp),
                    text = stringResource(id = R.string.vegetarian),
                    style = TextStyle(fontSize = 14.sp),
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = viewModel.veganChecked.value,
                    onCheckedChange = { checkedNew ->
                        viewModel.veganChecked.value = checkedNew
                    },
                )
                Text(
                    modifier = Modifier.padding(start = 2.dp),
                    text = stringResource(id = R.string.vegan),
                    style = TextStyle(fontSize = 14.sp),
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = viewModel.fastFoodChecked.value,
                    onCheckedChange = { checkedNew ->
                        viewModel.fastFoodChecked.value = checkedNew
                    },
                )
                Text(
                    modifier = Modifier.padding(start = 2.dp),
                    text = stringResource(id = R.string.fast_food),
                    style = TextStyle(fontSize = 14.sp),
                )
            }
        }

        Spacer(Modifier.weight(1f))

        Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = viewModel.parkingChecked.value,
                    onCheckedChange = { checkedNew ->
                        viewModel.parkingChecked.value = checkedNew
                    },
                )
                Text(
                    modifier = Modifier.padding(start = 2.dp),
                    text = stringResource(id = R.string.parking_available),
                    style = TextStyle(fontSize = 14.sp),
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = viewModel.familyPlaceChecked.value,
                    onCheckedChange = { checkedNew ->
                        viewModel.familyPlaceChecked.value = checkedNew
                    },
                )
                Text(
                    modifier = Modifier.padding(start = 2.dp),
                    text = stringResource(id = R.string.family_place),
                    style = TextStyle(fontSize = 14.sp),
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = viewModel.dogFriendlyChecked.value,
                    onCheckedChange = { checkedNew ->
                        viewModel.dogFriendlyChecked.value = checkedNew
                    },
                )
                Text(
                    modifier = Modifier.padding(start = 2.dp),
                    text = stringResource(id = R.string.dog_fancier),
                    style = TextStyle(fontSize = 14.sp),
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = viewModel.deliveryChecked.value,
                    onCheckedChange = { checkedNew ->
                        viewModel.deliveryChecked.value = checkedNew
                    },
                )
                Text(
                    modifier = Modifier.padding(start = 2.dp),
                    text = stringResource(id = R.string.delivery),
                    style = TextStyle(fontSize = 14.sp),
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(
                    checked = viewModel.creditCardChecked.value,
                    onCheckedChange = { checkedNew ->
                        viewModel.creditCardChecked.value = checkedNew
                    },
                )
                Text(
                    modifier = Modifier.padding(start = 2.dp),
                    text = stringResource(id = R.string.credit_card),
                    style = TextStyle(fontSize = 14.sp),
                )
            }
        }
    }
}
