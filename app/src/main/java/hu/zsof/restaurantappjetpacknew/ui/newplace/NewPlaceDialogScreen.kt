package hu.zsof.restaurantappjetpacknew.ui.newplace

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.location.Geocoder
import android.net.Uri
import android.os.Build
import android.provider.Settings.*
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.PhotoCamera
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
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
import hu.zsof.restaurantappjetpacknew.network.repository.LocalDataStateService
import hu.zsof.restaurantappjetpacknew.ui.common.NormalTextField
import hu.zsof.restaurantappjetpacknew.ui.common.TextFieldForDialog
import java.io.File
import java.util.*

@RequiresApi(Build.VERSION_CODES.R)
@SuppressLint("ResourceType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewPlaceDialogScreen(viewModel: NewPlaceDialogViewModel = hiltViewModel()) {
    val context = LocalContext.current

    if (viewModel.photoDialogOpen.value) {
        ChoosePhotoDialog(
            showPhotoPickerDialog = viewModel.photoDialogOpen.value,
            onDismiss = { viewModel.photoDialogOpen.value = false },
            selectedImageUri = viewModel.selectedImageUri,
            viewModel = viewModel,
        )
        if (viewModel.selectedImageUri.value != null) {
            viewModel.photoDialogOpen.value = false
        }
    }

    val categoryOptions = stringArrayResource(id = R.array.category_items)
    var selectedOptionText by remember { mutableStateOf(categoryOptions[0]) }
    val filterOptions = stringArrayResource(id = R.array.filter_options)
    val filterOptionsRight = stringArrayResource(id = R.array.filter_option_right)
    val (selectedOption: String, onOptionSelected: (String) -> Unit) = remember {
        mutableStateOf(
            filterOptions[0],
            // filterOptionsRight[0]
        )
    }

    // TODO error
    val imageFilePath: String = if (viewModel.selectedImageUri.value != null) {
        val path = viewModel.selectedImageUri.value.toString().split(':')[1]
        File(path).path
    } else {
        ""
    }

    Geocoder(context, Locale.getDefault()).getAddress(
        LocalDataStateService.getLatLng().latitude,
        LocalDataStateService.getLatLng().longitude,
    ) { address: android.location.Address? ->
        if (address != null) {
            viewModel.addressValue = address.getAddressLine(0)
        }
    }

    if (viewModel.dialogOpen.value) {
        Dialog(
            onDismissRequest = { viewModel.dialogOpen.value = false },
            properties = DialogProperties(
                dismissOnClickOutside = false,
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
                        text = stringResource(id = R.string.add_new_place_title),
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
                        value = viewModel.addressValue,
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
                            expanded = viewModel.expanded.value,
                            onExpandedChange = {
                                viewModel.expanded.value = !viewModel.expanded.value
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
                                        expanded = viewModel.expanded.value,
                                    )
                                },
                                colors = ExposedDropdownMenuDefaults.textFieldColors(),
                            )
                            ExposedDropdownMenu(
                                expanded = viewModel.expanded.value,
                                onDismissRequest = {
                                    viewModel.expanded.value = false
                                },
                            ) {
                                categoryOptions.forEach { selectionOption ->
                                    DropdownMenuItem(
                                        text = { Text(text = selectionOption) },
                                        onClick = {
                                            selectedOptionText = selectionOption
                                            println("selected $selectedOptionText  $selectedOption")
                                            viewModel.expanded.value = false
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
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp, 12.dp),
                        contentScale = ContentScale.Crop,
                    )
                    Text(
                        text = stringResource(id = R.string.filters),
                        style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(start = 10.dp),
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Row() {
                        Column(horizontalAlignment = Alignment.Start) {
                            filterOptions.forEach { filter ->
                                var checked by remember {
                                    mutableStateOf(false)
                                }
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Checkbox(
                                        checked = checked,
                                        onCheckedChange = { checkedNew ->
                                            checked = checkedNew
                                        },
                                    )
                                    Text(
                                        modifier = Modifier.padding(start = 2.dp),
                                        text = filter,
                                        style = TextStyle(fontSize = 14.sp),
                                    )
                                }
                            }
                        }

                        Spacer(Modifier.weight(1f))

                        Column {
                            filterOptionsRight.forEach { filter ->
                                var checked by remember {
                                    mutableStateOf(false)
                                }
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Checkbox(
                                        checked = checked,
                                        onCheckedChange = { checkedNew ->
                                            checked = checkedNew
                                            println("chechckob $checked  $checkedNew  $filter ")
                                        },
                                    )
                                    Text(
                                        modifier = Modifier.padding(start = 2.dp),
                                        text = filter,
                                        style = TextStyle(fontSize = 14.sp),
                                    )
                                }
                            }
                        }
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = stringResource(id = R.string.opening_hours),
                        style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(start = 10.dp),
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Row() {
                        Spacer(Modifier.weight(1f))
                        TextButton(onClick = { viewModel.dialogOpen.value = false }) {
                            Text(
                                text = stringResource(id = R.string.cancel_btn),
                                style = TextStyle(fontSize = 16.sp),
                            )
                        }
                        TextButton(onClick = {
                            if (viewModel.placeNameValue.value.isEmpty()) {
                                viewModel.placeNameError.value = true
                            } else if (viewModel.addressValue.isEmpty()) {
                                viewModel.addressError.value = true
                            } else {
                                viewModel.addNewPlace(
                                    typeValue = Type.getByName(selectedOptionText),
                                    priceValue = viewModel.priceValue,
                                    /* filter = CustomFilter(
                                         glutenFree = glutenFreeAdd.isChecked,
                                         lactoseFree = lactoseFreeAdd.isChecked,
                                         vegetarian = vegetarianAdd.isChecked,
                                         vegan = veganAdd.isChecked,
                                         fastFood = fastFoodAdd.isChecked,
                                         parkingAvailable = parkingAdd.isChecked,
                                         dogFriendly = dogAdd.isChecked,
                                         familyPlace = familyPlaceAdd.isChecked,
                                         delivery = deliveryAdd.isChecked,
                                         creditCard = creditCardAdd.isChecked,
                                     ),*/
                                    image = imageFilePath.toString(),
                                )
                                viewModel.dialogOpen.value = false
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

@RequiresApi(Build.VERSION_CODES.R)
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ChoosePhotoDialog(
    showPhotoPickerDialog: Boolean,
    onDismiss: () -> Unit,
    selectedImageUri: MutableState<Uri?>,
    viewModel: NewPlaceDialogViewModel,
) {
    val permissionState =
        rememberPermissionState(permission = Manifest.permission.READ_EXTERNAL_STORAGE)

    GalleryPermission(
        permissionState = permissionState,
        selectedImageUri = selectedImageUri,
        viewModel,
    )

    LaunchedEffect(Unit) {
        permissionState.launchPermissionRequest()
    }

    if (showPhotoPickerDialog) {
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
                    horizontalAlignment = CenterHorizontally,
                    modifier = Modifier
                        .padding(all = 16.dp),
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Text(
                            text = stringResource(R.string.photo_picker_title),
                            style = TextStyle(
                                fontSize = 24.sp,
                                fontWeight = FontWeight.Bold,
                            ),
                        )
                        Icon(
                            imageVector = Icons.Filled.Close,
                            contentDescription = null,
                            tint = colorResource(android.R.color.darker_gray),
                            modifier = Modifier
                                .width(30.dp)
                                .height(30.dp)
                                .clickable(onClick = onDismiss),
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable(onClick = {}),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.camera),
                            contentDescription = null,
                            modifier = Modifier
                                .width(42.dp)
                                .height(42.dp)
                                .padding(0.dp, 0.dp, 8.dp, 0.dp),
                        )
                        Text(
                            text = stringResource(R.string.take_photo),
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontStyle = FontStyle.Italic,
                            ),
                        )
                    }
                    Spacer(modifier = Modifier.height(10.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable(
                                onClick = {
                                    viewModel.galleryPermissionOpen.value = true
                                },
                            ),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start,

                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.gallery),
                            contentDescription = null,
                            modifier = Modifier
                                .width(42.dp)
                                .height(42.dp)
                                .padding(0.dp, 0.dp, 8.dp, 0.dp),
                        )
                        Text(
                            text = stringResource(R.string.choose_gallery),
                            style = TextStyle(
                                fontSize = 18.sp,
                                fontStyle = FontStyle.Italic,
                            ),
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun GalleryPermission(
    permissionState: PermissionState,
    selectedImageUri: MutableState<Uri?>,
    viewModel: NewPlaceDialogViewModel,
) {
    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> selectedImageUri.value = uri },
    )

    val context = LocalContext.current
    if (viewModel.galleryPermissionOpen.value) {
        PermissionRequired(
            permissionState = permissionState,
            permissionNotGrantedContent = {
                val textToShow = if (permissionState.shouldShowRationale) {
                    // If the user has denied the permission but the rationale can be shown,
                    // then gently explain why the app requires this permission
                    "Using the gallery is important for this app. Please grant the permission."
                } else {
                    // If it's the first time the user lands on this feature, or the user
                    // doesn't want to be asked again for this permission, explain that the
                    // permission is required
                    "Using the gallery required for this feature to be available. " +
                        "Please grant the permission"
                }
                AlertDialog(
                    onDismissRequest = {
                        // Dismiss the dialog when the user clicks outside the dialog or on the back
                        // button. If you want to disable that functionality, simply use an empty
                        // onCloseRequest.
                        // openDialog.value = false
                    },
                    confirmButton = {
                        Button(onClick = { permissionState.launchPermissionRequest() }) {
                            Text("Request permission")
                        }
                    },
                    text = { Text(textToShow) },
                )
            },
            permissionNotAvailableContent = {
                AlertDialog(
                    onDismissRequest = {
                        viewModel.galleryPermissionOpen.value = false
                    },
                    text = { Text("You could not use this function because the permission was denied. Please go to Settings and add permission to use the Gallery") },
                    confirmButton = { // todo check telón amin api 30 alatt van - ne jelenjen meg gomb
                        if (Build.VERSION.SDK_INT >= 30) {
                            Button(onClick = {
                                val i = Intent(ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                                context.startActivity(i)
                            }) {
                                Text("Go to Settings")
                            }
                        }
                    },
                )
            },
        ) {
            singlePhotoPickerLauncher.launch(
                PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly),
            )
        }
    }
}

@Suppress("DEPRECATION")
fun Geocoder.getAddress(
    latitude: Double,
    longitude: Double,
    address: (android.location.Address?) -> Unit,
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        getFromLocation(latitude, longitude, 1) { address(it.firstOrNull()) }
        return
    }

    try {
        address(getFromLocation(latitude, longitude, 1)?.firstOrNull())
    } catch (e: Exception) {
        // will catch if there is an internet problem
        address(null)
    }
}
