package hu.zsof.restaurantappjetpacknew.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import hu.zsof.restaurantappjetpacknew.R
import hu.zsof.restaurantappjetpacknew.model.User
import hu.zsof.restaurantappjetpacknew.ui.common.TextChip

@ExperimentalMaterial3Api
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    val user = viewModel.userProfile.observeAsState().value
    LaunchedEffect(key1 = "Profile") {
        viewModel.getUserProfile()
    }

    Column(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Text(
            text = stringResource(id = R.string.profile_settings_text),
            style = TextStyle(fontWeight = FontWeight.Bold),
            fontSize = 20.sp,
            modifier = Modifier
                .padding(top = 8.dp)
                .align(Alignment.CenterHorizontally),
        )
        if (user != null) {
            BaseProfile(user)
            Column(modifier = Modifier.padding(bottom = 44.dp)) {
                Column(Modifier.verticalScroll(rememberScrollState())) {
                    ChipSettings(user, viewModel)
                    Row(
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth().fillMaxHeight(),
                    ) {
                        Button(
                            onClick = {
                            },
                        ) {
                            Text(
                                text = stringResource(id = R.string.save_btn),
                                style = TextStyle(fontSize = 16.sp),
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ChipSettings(user: User, viewModel: ProfileViewModel) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(8.dp),
    ) {
        FlowRow(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth(),
        ) {
            TextChip(
                isSelected = viewModel.glutenFreeChecked.value,
                text = stringResource(id = R.string.gluten_free),
                onChecked = {
                    viewModel.glutenFreeChecked.value = it
                },
            )
            TextChip(
                isSelected = viewModel.lactoseFreeChecked.value,
                text = stringResource(id = R.string.lactose_free),
                onChecked = {
                    viewModel.lactoseFreeChecked.value = it
                },
            )
            TextChip(
                isSelected = viewModel.vegetarianChecked.value,
                text = stringResource(id = R.string.vegetarian),
                onChecked = {
                    viewModel.vegetarianChecked.value = it
                },
            )
            TextChip(
                isSelected = viewModel.veganChecked.value,
                text = stringResource(id = R.string.vegan),
                onChecked = {
                    viewModel.veganChecked.value = it
                },
            )
            TextChip(
                isSelected = viewModel.fastFoodChecked.value,
                text = stringResource(id = R.string.fast_food),
                onChecked = {
                    viewModel.fastFoodChecked.value = it
                },
            )
            TextChip(
                isSelected = viewModel.parkingChecked.value,
                text = stringResource(id = R.string.parking_available),
                onChecked = {
                    viewModel.parkingChecked.value = it
                },
            )
            TextChip(
                isSelected = viewModel.familyPlaceChecked.value,
                text = stringResource(id = R.string.family_place),
                onChecked = {
                    viewModel.familyPlaceChecked.value = it
                },
            )
            TextChip(
                isSelected = viewModel.dogFriendlyChecked.value,
                text = stringResource(id = R.string.dog_fancier),
                onChecked = {
                    viewModel.dogFriendlyChecked.value = it
                },
            )
            TextChip(
                isSelected = viewModel.deliveryChecked.value,
                text = stringResource(id = R.string.delivery),
                onChecked = {
                    viewModel.deliveryChecked.value = it
                },
            )
            TextChip(
                isSelected = viewModel.creditCardChecked.value,
                text = stringResource(id = R.string.credit_card),
                onChecked = {
                    viewModel.creditCardChecked.value = it
                },
            )
        }
    }
}

@Composable
fun BaseProfile(user: User) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(8.dp),
    ) {
        Image(
            imageVector = Icons.Outlined.Person,
            contentDescription = null,
            modifier = Modifier
                .size(120.dp)
                .align(Alignment.CenterHorizontally),

        )
        Text(
            modifier = Modifier
                .padding(start = 8.dp, top = 8.dp, end = 8.dp)
                .align(Alignment.CenterHorizontally),
            text = user.name,
            style = TextStyle(fontWeight = FontWeight.Bold),
            fontSize = 20.sp,
        )

        Row(
            modifier = Modifier
                .padding(vertical = 16.dp),
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp),
                text = user.email,
                fontSize = 20.sp,
            )
            Spacer(modifier = Modifier.width(16.dp))
            Icon(
                imageVector = Icons.Filled.Edit,
                contentDescription = null,
            )
        }
        Row(
            modifier = Modifier
                .padding(vertical = 16.dp),
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 8.dp, end = 8.dp),
                text = "*******",
                fontSize = 20.sp,
            )
            androidx.compose.material.Icon(
                imageVector = Icons.Filled.Edit,
                contentDescription = null,
            )
        }
    }
}