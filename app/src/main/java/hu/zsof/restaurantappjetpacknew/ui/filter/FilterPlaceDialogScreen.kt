package hu.zsof.restaurantappjetpacknew.ui.filter

import android.annotation.SuppressLint
import android.provider.Settings.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.google.accompanist.permissions.*
import com.google.maps.android.compose.*
import hu.zsof.restaurantappjetpacknew.R
import hu.zsof.restaurantappjetpacknew.model.enums.Price
import hu.zsof.restaurantappjetpacknew.model.enums.Type
import hu.zsof.restaurantappjetpacknew.navigation.ScreenModel
import hu.zsof.restaurantappjetpacknew.network.repository.LocalDataStateService
import java.util.*

@SuppressLint("ResourceType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterPlaceDialogScreen(
    viewModel: FilterPlaceDialogViewModel = hiltViewModel(),
    navController: NavHostController,
) {
    val categoryOptions = stringArrayResource(id = R.array.filter_category_items)
    var selectedOptionText by remember { mutableStateOf(categoryOptions[0]) }

    if (viewModel.dialogOpen.value) {
        Dialog(
            onDismissRequest = { viewModel.dialogOpen.value = false },
            properties = DialogProperties(
                dismissOnClickOutside = true,
                dismissOnBackPress = true,
                usePlatformDefaultWidth = false,
            ),
        ) {
            Surface(
                modifier = Modifier
                    .padding(top = 32.dp, bottom = 32.dp, start = 8.dp, end = 8.dp)
                    .fillMaxWidth()
                    .wrapContentHeight(),
                shape = RoundedCornerShape(8.dp),
            ) {
                Column(
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .padding(all = 16.dp)
                        .verticalScroll(rememberScrollState()),
                ) {
                    Text(
                        text = stringResource(id = R.string.filter_places_name),
                        style = MaterialTheme.typography.headlineSmall.copy(
                            fontStyle = FontStyle.Italic,
                            fontWeight = FontWeight.Bold,
                        ),
                        modifier = Modifier
                            .padding(8.dp)
                            .align(Alignment.Start),
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    Row() {
                        Text(
                            text = stringResource(id = R.string.type_text),
                            modifier = Modifier.padding(top = 16.dp, end = 16.dp, start = 8.dp),
                            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
                        )
                        Spacer(Modifier.weight(1f))

                        ExposedDropdownMenuBox(
                            expanded = viewModel.expandedCategoryMenu.value,
                            onExpandedChange = {
                                viewModel.expandedCategoryMenu.value =
                                    !viewModel.expandedCategoryMenu.value
                            },
                            modifier = Modifier.padding(start = 16.dp, end = 8.dp),
                        ) {
                            TextField(
                                readOnly = true,
                                value = selectedOptionText,
                                modifier = Modifier.menuAnchor(),
                                onValueChange = { },
                                trailingIcon = {
                                    ExposedDropdownMenuDefaults.TrailingIcon(
                                        expanded = viewModel.expandedCategoryMenu.value,
                                    )
                                },
                                colors = ExposedDropdownMenuDefaults.textFieldColors(),
                            )
                            ExposedDropdownMenu(
                                expanded = viewModel.expandedCategoryMenu.value,
                                onDismissRequest = {
                                    viewModel.expandedCategoryMenu.value = false
                                },
                            ) {
                                categoryOptions.forEach { selectionOption ->
                                    DropdownMenuItem(
                                        text = { Text(text = selectionOption) },
                                        onClick = {
                                            selectedOptionText = selectionOption
                                            viewModel.expandedCategoryMenu.value = false
                                        },
                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))

                    Row() {
                        Text(
                            text = stringResource(id = R.string.price_text),
                            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
                            modifier = Modifier.padding(top = 12.dp, end = 16.dp, start = 10.dp),
                        )
                        Spacer(Modifier.weight(1f))

                        Slider(
                            value = viewModel.sliderValue.value,
                            onValueChange = { sliderValueNew ->
                                viewModel.sliderValue.value = sliderValueNew
                            },
                            onValueChangeFinished = {
                                // this is called when the user completed selecting the value
                                viewModel.priceValue = when (viewModel.sliderValue.value) {
                                    0f -> {
                                        Price.LOW
                                    }
                                    5.0f -> {
                                        Price.MIDDLE
                                    }
                                    else -> {
                                        Price.HIGH
                                    }
                                }
                            },
                            valueRange = 0f..10f,
                            steps = 1,
                            modifier = Modifier.padding(start = 32.dp),
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = stringResource(id = R.string.filters),
                        style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(start = 10.dp),
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    PlaceFilter()
                    Spacer(modifier = Modifier.height(10.dp))

                    Row() {
                        Spacer(Modifier.weight(1f))
                        TextButton(onClick = {
                            navController.navigate(ScreenModel.NavigationScreen.Home.route)
                            viewModel.dialogOpen.value = false
                        }) {
                            Text(
                                text = stringResource(id = R.string.cancel_btn),
                                style = TextStyle(fontSize = 16.sp),
                            )
                        }
                        TextButton(
                            onClick = {
                                val filterResult = viewModel.filterPlaces(
                                    typeValue = Type.getByName(selectedOptionText),
                                )
                                LocalDataStateService.filteredPlaces.value = filterResult
                                navController.navigate(ScreenModel.NavigationScreen.Home.route)
                                viewModel.dialogOpen.value = false
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
