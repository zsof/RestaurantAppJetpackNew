package hu.zsof.restaurantappjetpacknew.ui.common.screen

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.provider.MediaStore
import android.provider.Settings.*
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PhotoCamera
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.google.accompanist.permissions.*
import com.google.maps.android.compose.*
import hu.zsof.restaurantappjetpacknew.R
import hu.zsof.restaurantappjetpacknew.model.enums.Price
import hu.zsof.restaurantappjetpacknew.model.enums.Type
import hu.zsof.restaurantappjetpacknew.module.AppState
import hu.zsof.restaurantappjetpacknew.ui.common.field.NormalTextField
import hu.zsof.restaurantappjetpacknew.ui.common.field.TextFieldForDialog
import java.io.*
import java.util.*

@SuppressLint("ResourceType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommonPlaceDialogScreen(
    viewModel: CommonPlaceDialogViewModel = hiltViewModel(),
    onDialogClose: () -> Unit,
    isNewPlace: Boolean
) {
    val context = LocalContext.current

    if (viewModel.photoDialogOpen.value) {
        PhotoChooserDialog(
            showPhotoPickerDialog = viewModel.photoDialogOpen.value,
            onDismiss = { viewModel.photoDialogOpen.value = false },
            selectedImageUri = viewModel.selectedImageUri,
            galleryOpenPermission = viewModel.galleryPermissionOpen,
            cameraOpenPermission = viewModel.cameraPermissionOpen,
        )
        if (viewModel.selectedImageUri.value != null) {
            viewModel.photoDialogOpen.value = false
        }
    }

    val categoryOptions = stringArrayResource(id = R.array.filter_category_items)
    var selectedOptionText by remember { mutableStateOf(categoryOptions[viewModel.selectedOptionIndex.value]) }

    val projection = arrayOf(MediaStore.MediaColumns.DATA)
    val contentResolver: ContentResolver = context.contentResolver
    var imagePath = ""
    viewModel.selectedImageUri.value?.let {
        contentResolver.query(
            it,
            projection,
            null,
            null,
            null,
        )
    }
        ?.use { metaCursor ->
            if (metaCursor.moveToFirst()) {
                imagePath = metaCursor.getString(0)
            }
        }

    if (viewModel.dialogOpen.value) {
        Dialog(
            onDismissRequest = {
                viewModel.dialogOpen.value = false
                AppState.place.value = null
            },
            properties = DialogProperties(
                dismissOnClickOutside = false,
                dismissOnBackPress = true,
                usePlatformDefaultWidth = false,
            ),
        ) {
            BackHandler {
                AppState.place.value = null
                onDialogClose()
            }
            Surface(
                modifier = Modifier
                    .padding(top = 32.dp, bottom = 32.dp, start = 8.dp, end = 8.dp)
                    .fillMaxWidth()
                    .wrapContentHeight(),
                shape = RoundedCornerShape(8.dp),
            ) {
                Column {
                    Column(
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier
                            .padding(all = 16.dp)
                            .verticalScroll(rememberScrollState())
                            .weight(1f, fill = false),
                    ) {
                        Text(
                            text = if (isNewPlace)
                                stringResource(id = R.string.add_new_place_title)
                            else stringResource(
                                id = R.string.edit_place_title
                            ),
                            style = MaterialTheme.typography.headlineSmall.copy(
                                fontStyle = FontStyle.Italic,
                                fontWeight = FontWeight.Bold,
                            ),
                            modifier = Modifier
                                .padding(8.dp)
                                .align(Alignment.Start),
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        NormalTextField(
                            value = viewModel.placeNameValue.value,
                            label = stringResource(id = R.string.place_name_text),
                            onValueChange = { newValue ->
                                viewModel.placeNameValue.value = newValue
                                viewModel.placeNameError.value = false
                            },
                            isError = viewModel.placeNameError.value,
                            leadingIcon = null,
                            trailingIcon = {},
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next,
                                capitalization = KeyboardCapitalization.Words,
                            ),
                            onDone = { },
                            placeholder = "",
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        NormalTextField(
                            value = viewModel.addressValue.value,
                            label = stringResource(id = R.string.address_text),
                            onValueChange = {
                                /* newValue ->
                            addressValue = newValue
                            addressError = false*/
                            },
                            isError = viewModel.addressError.value,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Text,
                                imeAction = ImeAction.Next,
                                capitalization = KeyboardCapitalization.Sentences,
                            ),
                            leadingIcon = null,
                            trailingIcon = null,
                            onDone = { },
                            placeholder = "",
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        TextFieldForDialog(
                            value = viewModel.websiteValue.value,
                            label = stringResource(id = R.string.website_text),
                            onValueChange = { newValue ->
                                viewModel.websiteValue.value = newValue
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Uri,
                                imeAction = ImeAction.Next,
                            ),
                            onDone = { },
                            placeholder = stringResource(id = R.string.optional_text),
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        TextFieldForDialog(
                            value = viewModel.emailValue.value,
                            label = stringResource(id = R.string.email_address),
                            onValueChange = { newValue ->
                                viewModel.emailValue.value = newValue
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Email,
                                imeAction = ImeAction.Next,
                            ),
                            onDone = { },
                            placeholder = stringResource(id = R.string.optional_text),
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        TextFieldForDialog(
                            value = viewModel.phoneValue.value,
                            label = stringResource(id = R.string.phone_number_text),
                            onValueChange = { newValue ->
                                viewModel.phoneValue.value = newValue
                            },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Phone,
                                imeAction = ImeAction.Done,
                            ),
                            onDone = { },
                            placeholder = stringResource(id = R.string.optional_text),
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
                                modifier = Modifier.padding(
                                    top = 12.dp,
                                    end = 16.dp,
                                    start = 10.dp
                                ),
                            )
                            Spacer(Modifier.weight(1f))

                            Slider(
                                value = viewModel.sliderValue.value,
                                onValueChange = { sliderValueNew ->
                                    viewModel.sliderValue.value = sliderValueNew
                                },
                                onValueChangeFinished = {
                                    // this is called when the user completed selecting the value
                                    viewModel.priceValue.value =
                                        when (viewModel.sliderValue.value) {
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
                                colors = SliderDefaults.colors(thumbColor = MaterialTheme.colorScheme.primaryContainer),
                            )
                        }

                        Spacer(modifier = Modifier.height(10.dp))

                        Button(
                            onClick = {
                                viewModel.photoDialogOpen.value = true
                            },
                            contentPadding = PaddingValues(
                                start = 20.dp,
                                top = 12.dp,
                                end = 20.dp,
                                bottom = 12.dp,
                            ),
                            modifier = Modifier.align(CenterHorizontally),
                        ) {
                            Icon(
                                Icons.Outlined.PhotoCamera,
                                contentDescription = "Camera",
                                modifier = Modifier.size(ButtonDefaults.IconSize),
                            )
                            Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                            Text("Image")
                        }
                        AsyncImage(
                            model = viewModel.selectedImageUri.value,
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier
                                .wrapContentSize()
                                .wrapContentHeight()
                                .wrapContentWidth()
                                .align(CenterHorizontally)
                                .padding(16.dp)
                                .clip(RoundedCornerShape(8.dp)),
                        )
                        Text(
                            text = stringResource(id = R.string.filters),
                            style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
                            modifier = Modifier.padding(start = 10.dp),
                        )
                        Spacer(modifier = Modifier.height(10.dp))

                        CommonPlaceFilter(viewModel = viewModel)

                        Spacer(modifier = Modifier.height(10.dp))

                        OpeningHours(viewModel = viewModel)

                        Spacer(modifier = Modifier.height(10.dp))
                    }
                    Row(modifier = Modifier.padding(bottom = 8.dp)) {
                        Spacer(Modifier.weight(1f))
                        TextButton(onClick = {
                            viewModel.dialogOpen.value = false
                            AppState.place.value = null
                            onDialogClose()
                        }) {
                            Text(
                                text = stringResource(id = R.string.cancel_btn),
                                style = TextStyle(fontSize = 16.sp),
                            )
                        }
                        TextButton(onClick = {
                            if (viewModel.placeNameValue.value.isEmpty()) {
                                viewModel.placeNameError.value = true
                            } else if (viewModel.addressValue.value.isEmpty()) {
                                viewModel.addressError.value = true
                            } else {
                                viewModel.addOrEditPlace(
                                    typeValue = Type.getByName(selectedOptionText),
                                    priceValue = viewModel.priceValue.value,
                                    image = imagePath,
                                )
                                viewModel.dialogOpen.value = false

                                AppState.place.value = null
                                onDialogClose()
                            }
                        }) {
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
