package hu.zsof.restaurantappjetpacknew.ui.edit

import android.annotation.SuppressLint
import android.provider.Settings.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.*
import com.google.maps.android.compose.*
import hu.zsof.restaurantappjetpacknew.ui.common.screen.CommonPlaceDialogScreen
import java.io.*
import java.util.*

@SuppressLint("ResourceType")
@Composable
fun EditPlaceDialogScreen(
    viewModel: EditPlaceViewModel = hiltViewModel(),
    onDialogClose: () -> Unit,
) {
    CommonPlaceDialogScreen(
        viewModel = viewModel,
        onDialogClose = onDialogClose,
        isNewPlace = false
    )
}
