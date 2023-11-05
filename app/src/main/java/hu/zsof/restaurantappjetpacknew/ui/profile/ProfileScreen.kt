package hu.zsof.restaurantappjetpacknew.ui.profile

import android.Manifest
import android.os.Build
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Person2
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import hu.zsof.restaurantappjetpacknew.R
import hu.zsof.restaurantappjetpacknew.model.User
import hu.zsof.restaurantappjetpacknew.module.AppState
import hu.zsof.restaurantappjetpacknew.ui.common.button.TextChip
import hu.zsof.restaurantappjetpacknew.ui.common.screen.CommonEditTextDialog
import hu.zsof.restaurantappjetpacknew.util.Constants
import hu.zsof.restaurantappjetpacknew.util.GalleryPermission
import hu.zsof.restaurantappjetpacknew.util.extension.getRealPathFromURI
import hu.zsof.restaurantappjetpacknew.util.extension.imageUrl

@ExperimentalMaterial3Api
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel = hiltViewModel(),
) {
    val user = viewModel.userProfile.observeAsState().value
    LaunchedEffect(key1 = "Profile") {
        viewModel.getUserProfile()
    }

    Scaffold(
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 16.dp, top = 16.dp),
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
                    viewModel.glutenFreeChecked.value = user.filterItems.glutenFree
                    viewModel.lactoseFreeChecked.value = user.filterItems.lactoseFree
                    viewModel.vegetarianChecked.value = user.filterItems.vegetarian
                    viewModel.veganChecked.value = user.filterItems.vegan
                    viewModel.fastFoodChecked.value = user.filterItems.fastFood
                    viewModel.parkingChecked.value = user.filterItems.parkingAvailable
                    viewModel.familyPlaceChecked.value = user.filterItems.familyPlace
                    viewModel.dogFriendlyChecked.value = user.filterItems.dogFriendly
                    viewModel.deliveryChecked.value = user.filterItems.delivery
                    viewModel.creditCardChecked.value = user.filterItems.creditCard

                    Column(modifier = Modifier.padding(bottom = 44.dp)) {
                        Column(Modifier.verticalScroll(rememberScrollState())) {
                            BaseProfile(user, viewModel)
                            ChipSettings(viewModel)
                            OtherSettings(viewModel = viewModel)
                        }
                    }
                }
            }
        },
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ChipSettings(viewModel: ProfileViewModel) {
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
        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
        ) {
            Button(
                onClick = {
                    viewModel.updateUserProfileFilters()
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

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun BaseProfile(user: User, viewModel: ProfileViewModel) {
    val context = LocalContext.current
    var imagePath = ""

    viewModel.selectedImageUri.value?.let {
        imagePath = getRealPathFromURI(it, context) ?: ""
    }

    val permissionStateGallery = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        rememberPermissionState(permission = Manifest.permission.READ_MEDIA_IMAGES)
    else rememberPermissionState(permission = Manifest.permission.READ_EXTERNAL_STORAGE)

    if (viewModel.photoPickerOpen.value) {
        GalleryPermission(
            permissionState = permissionStateGallery,
            selectedImageUri = viewModel.selectedImageUri,
        )
        if (viewModel.selectedImageUri.value != null) {
            viewModel.photoPickerOpen.value = false
            viewModel.updateUserProfileImage(imagePath)
        }
    }

    if (viewModel.changeNameDialogOpen.value) {
        CommonEditTextDialog(
            changingValue = viewModel.userName,
            changingTitle = stringResource(R.string.profile_change_dialog_title_name),
            keyboardType = KeyboardType.Text,
            onDismiss = { viewModel.changeNameDialogOpen.value = false },
            onDismissSave = {
                viewModel.updateUserProfileName()
                viewModel.changeNameDialogOpen.value = false
            }
        )
    }

    val drawableResource = if (viewModel.getAppPreference(Constants.Prefs.DARK_MODE))
        R.drawable.loading_blue
    else R.drawable.loading_yellow

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(8.dp),
    ) {
        if (viewModel.selectedImageUri.value != null) {
            AsyncImage(
                model = viewModel.selectedImageUri.value,
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(160.dp, 160.dp)
                    .padding(8.dp)
                    .border(2.dp, color = MaterialTheme.colorScheme.primary, CircleShape)
                    .clip(CircleShape)
                    .zIndex(1f)
            )
        } else if (user.image != null) {
            AsyncImage(
                model = user.image.imageUrl(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .size(160.dp, 160.dp)
                    .padding(8.dp)
                    .border(2.dp, color = MaterialTheme.colorScheme.primary, CircleShape)
                    .clip(CircleShape)
                    .clickable {
                        permissionStateGallery.launchPermissionRequest()
                        viewModel.photoPickerOpen.value = true
                    },
                placeholder = painterResource(id = drawableResource),
            )
        } else
            Icon(
                imageVector = Icons.Filled.Person2,
                contentDescription = null,
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.CenterHorizontally)
                    .clickable(onClick = {
                        permissionStateGallery.launchPermissionRequest()
                        viewModel.photoPickerOpen.value = true

                    }),
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )

        Row(
            modifier = Modifier
                .padding(vertical = 16.dp)
                .align(Alignment.CenterHorizontally),
        ) {
            Text(
                modifier = Modifier
                    .padding(start = 8.dp, top = 8.dp, end = 8.dp),
                text = viewModel.userName.value.toString(),
                style = TextStyle(fontWeight = FontWeight.Bold),
                fontSize = 20.sp,
            )
            IconButton(
                onClick = {
                    viewModel.changeNameDialogOpen.value = true
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Edit,
                    contentDescription = null,
                )
            }
        }

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
        }
    }
}

@Composable
fun OtherSettings(viewModel: ProfileViewModel) {
    viewModel.switchCheckedState.value = viewModel.getAppPreference(Constants.Prefs.DARK_MODE)

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
        shape = RoundedCornerShape(8.dp),
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 16.dp),
        ) {
            Text(
                modifier = Modifier
                    .padding(8.dp),
                text = stringResource(id = R.string.dark_theme_text),
                fontSize = 20.sp,
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(end = 8.dp),
                contentAlignment = Alignment.TopEnd,
            ) {
                Switch(
                    checked = viewModel.switchCheckedState.value,
                    onCheckedChange = {
                        viewModel.switchCheckedState.value = it

                        if (it) {
                            viewModel.switchCheckedState.value = true
                            viewModel.setAppPreference(Constants.Prefs.DARK_MODE, true)
                            AppState.darkTheme.value = true
                        } else {
                            viewModel.switchCheckedState.value = false
                            viewModel.setAppPreference(Constants.Prefs.DARK_MODE, false)
                            AppState.darkTheme.value = false
                        }
                    },
                )
            }
        }
    }
}
