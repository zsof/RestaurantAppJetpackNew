package hu.zsof.restaurantappjetpacknew.ui.newplace

import android.annotation.SuppressLint
import android.location.Geocoder
import android.net.Uri
import android.os.Build
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
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
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
import com.google.android.gms.maps.model.LatLng
import hu.zsof.restaurantappjetpacknew.R
import hu.zsof.restaurantappjetpacknew.model.enums.Price
import hu.zsof.restaurantappjetpacknew.model.enums.Type
import hu.zsof.restaurantappjetpacknew.network.repository.LocalDataStateService
import hu.zsof.restaurantappjetpacknew.network.request.PlaceDataRequest
import hu.zsof.restaurantappjetpacknew.ui.common.NormalTextField
import hu.zsof.restaurantappjetpacknew.ui.common.TextFieldForDialog
import java.util.*

@SuppressLint("ResourceType")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewPlaceDialogScreen(viewModel: NewPlaceDialogViewModel = hiltViewModel()) {
    val context = LocalContext.current

    val latLang: LatLng = try {
        LocalDataStateService.getLatLng()
    } catch (e: Exception) {
        e.printStackTrace()
        LatLng(0.0, 0.0)
    }

    var dialogOpen by remember {
        mutableStateOf(true)
    }

    val selectedImageUri = remember {
        mutableStateOf<Uri?>(null)
    }

    val photoDialogOpen = remember {
        mutableStateOf(false)
    }
    if (photoDialogOpen.value) {
        ChoosePhotoDialog(
            setShowPhotoPickerDialog = photoDialogOpen.value,
            onDismiss = { photoDialogOpen.value = false },
            selectedImageUri = selectedImageUri,
        )
    }

    var expanded by remember { mutableStateOf(false) }
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

    var sliderValue by remember {
        mutableStateOf(0f)
    }
    var priceValue = Price.LOW

    var placeNameValue by remember { mutableStateOf("") }
    var placeNameError by remember { mutableStateOf(false) }
    var addressValue = ""
    Geocoder(context, Locale.getDefault()).getAddress(
        LocalDataStateService.getLatLng().latitude,
        LocalDataStateService.getLatLng().longitude,
    ) { address: android.location.Address? ->
        if (address != null) {
            addressValue = address.getAddressLine(0)
        }
    }
    var addressError by remember { mutableStateOf(false) }
    var websiteValue by remember { mutableStateOf("") }
    var emailValue by remember { mutableStateOf("") }
    var phoneValue by remember { mutableStateOf("") }

    if (dialogOpen) {
        Dialog(
            onDismissRequest = { dialogOpen = false },

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
                        value = placeNameValue,
                        label = stringResource(id = R.string.place_name_text),
                        onValueChange = { newValue ->
                            placeNameValue = newValue
                            placeNameError = false
                        },
                        isError = placeNameError,
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
                        value = addressValue,
                        label = stringResource(id = R.string.address_text),
                        onValueChange = {
                            /* newValue ->
                            addressValue = newValue
                            addressError = false*/
                        },
                        isError = addressError,
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
                        value = websiteValue,
                        label = stringResource(id = R.string.website_text),
                        onValueChange = { newValue ->
                            websiteValue = newValue
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
                        value = emailValue,
                        label = stringResource(id = R.string.email_address),
                        onValueChange = { newValue ->
                            emailValue = newValue
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
                        value = phoneValue,
                        label = stringResource(id = R.string.phone_number_text),
                        onValueChange = { newValue ->
                            phoneValue = newValue
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
                            expanded = expanded,
                            onExpandedChange = {
                                expanded = !expanded
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
                                        expanded = expanded,
                                    )
                                },
                                colors = ExposedDropdownMenuDefaults.textFieldColors(),
                            )
                            ExposedDropdownMenu(
                                expanded = expanded,
                                onDismissRequest = {
                                    expanded = false
                                },
                            ) {
                                categoryOptions.forEach { selectionOption ->
                                    DropdownMenuItem(
                                        text = { Text(text = selectionOption) },
                                        onClick = {
                                            selectedOptionText = selectionOption
                                            println("selected $selectedOptionText  $selectedOption")
                                            expanded = false
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
                            value = sliderValue,
                            onValueChange = { sliderValueNew ->
                                sliderValue = sliderValueNew
                            },
                            onValueChangeFinished = {
                                // this is called when the user completed selecting the value
                                priceValue = when (sliderValue) {
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
                            photoDialogOpen.value = true
                        },
                        contentPadding = PaddingValues(
                            start = 20.dp,
                            top = 12.dp,
                            end = 20.dp,
                            bottom = 12.dp,
                        ),
                    ) {
                        Icon(
                            Icons.Filled.Camera,
                            contentDescription = "Camera",
                            modifier = Modifier.size(ButtonDefaults.IconSize),
                        )
                        Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                        Text("Image")
                    }
                    // TODO nem jelenik meg
                    AsyncImage(
                        model = selectedImageUri,
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth(),
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
                        TextButton(onClick = { dialogOpen = false }) {
                            Text(
                                text = stringResource(id = R.string.cancel_btn),
                                style = TextStyle(fontSize = 16.sp),
                            )
                        }
                        TextButton(onClick = {
                            if (placeNameValue.isEmpty()) {
                                placeNameError = true
                            } else if (addressValue.isEmpty()) {
                                addressError = true
                            } else {
                                viewModel.addNewPlace(
                                    PlaceDataRequest(
                                        name = placeNameValue,
                                        address = addressValue,
                                        web = websiteValue,
                                        email = emailValue,
                                        phoneNumber = phoneValue,
                                        price = priceValue,
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
                                        type = Type.getByName(selectedOptionText),
                                        latitude = latLang.latitude,
                                        longitude = latLang.longitude,
                                    ),
                                    image = "",
                                )
                                dialogOpen = false
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

@Composable
fun ChoosePhotoDialog(
    setShowPhotoPickerDialog: Boolean,
    onDismiss: () -> Unit,
    selectedImageUri: MutableState<Uri?>,
) {
    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri -> selectedImageUri.value = uri },
    )

    if (setShowPhotoPickerDialog) {
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
                    horizontalAlignment = Alignment.CenterHorizontally,
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
                                    singlePhotoPickerLauncher.launch(
                                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly),
                                    )
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
