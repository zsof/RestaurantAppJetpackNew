package hu.zsof.restaurantappjetpacknew.ui.newplace

import android.Manifest
import android.annotation.SuppressLint
import android.location.Geocoder
import android.net.Uri
import android.os.Build
import android.provider.Settings.*
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
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

                    PlaceFilter()

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
                                    image = imageFilePath,
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

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ChoosePhotoDialog(
    showPhotoPickerDialog: Boolean,
    onDismiss: () -> Unit,
    selectedImageUri: MutableState<Uri?>,
    viewModel: NewPlaceDialogViewModel,
) {
    val permissionStateGallery =
        rememberPermissionState(permission = Manifest.permission.READ_EXTERNAL_STORAGE)
    GalleryPermission(
        permissionState = permissionStateGallery,
        selectedImageUri = selectedImageUri,
        viewModel,
    )

    val permissionStateCamera =
        rememberPermissionState(permission = Manifest.permission.CAMERA)
    CameraPermission(
        permissionState = permissionStateCamera,
        selectedImageUri = selectedImageUri,
        viewModel,
    )

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
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                            .clickable(onClick = {
                                permissionStateCamera.launchPermissionRequest()
                                viewModel.cameraPermissionOpen.value =
                                    true // Necessary to be able to close the dialog
                            }),
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
                                    permissionStateGallery.launchPermissionRequest()
                                    viewModel.galleryPermissionOpen.value =
                                        true // Necessary to be able to close the dialog
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
                // if there was already a Manifest.permission request, but the user rejected it
                if (permissionState.permissionRequested) {
                    AlertDialog(
                        onDismissRequest = {},
                        confirmButton = {
                            Button(onClick = { permissionState.launchPermissionRequest() }) {
                                Text("Request permission")
                            }
                        },
                        text = { Text("Using the gallery is important for this feature to be available. Please grant the permission.") },
                    )
                }
            },
            // if the user has already denied permission twice
            permissionNotAvailableContent = {
                AlertDialog(
                    onDismissRequest = {
                        viewModel.galleryPermissionOpen.value = false
                    },
                    text = { Text("You could not use this function because the permission was denied. Please go to Settings and add permission to use the Gallery") },
                    confirmButton = {
                        // The Google Play store has a policy that limits usage of MANAGE_EXTERNAL_STORAGE
                        /*  if (Build.VERSION.SDK_INT >= 30) {
                              Button(onClick = {
                                  val i = Intent(ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
                                  context.startActivity(i)
                              }) {
                                  Text("Go to Settings")
                              }
                          }*/
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

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CameraPermission(
    permissionState: PermissionState,
    selectedImageUri: MutableState<Uri?>,
    viewModel: NewPlaceDialogViewModel,
) {
    val context = LocalContext.current
    if (viewModel.cameraPermissionOpen.value) {
        PermissionRequired(
            permissionState = permissionState,
            permissionNotGrantedContent = {
                // if there was already a Manifest.permission request, but the user rejected it
                if (permissionState.permissionRequested) {
                    AlertDialog(
                        onDismissRequest = {
                        },
                        confirmButton = {
                            Button(onClick = { permissionState.launchPermissionRequest() }) {
                                Text("Request permission")
                            }
                        },
                        text = { Text("Using the camera is important for this feature to be available. Please grant the permission.") },
                    )
                }
            },
            // if the user has already denied permission twice
            permissionNotAvailableContent = {
                AlertDialog(
                    onDismissRequest = {
                        viewModel.cameraPermissionOpen.value = false
                    },
                    text = { Text("You can't use this feature because the permission was denied. Please go to Settings and add permission to use the Camera") },
                    confirmButton = {},
                )
            },
        ) {
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
