package hu.zsof.restaurantappjetpacknew.ui.review

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import hu.zsof.restaurantappjetpacknew.R
import hu.zsof.restaurantappjetpacknew.ui.common.screen.PlaceListItem

@ExperimentalMaterial3Api
@Composable
fun ReviewPlaceListScreen(
    viewModel: ReviewPlaceViewModel = hiltViewModel(),
    onClickPlaceItem: (Long) -> Unit,
    onClickPlaceInReviewItem: (Long) -> Unit,
    ) {
    val newPlaces = viewModel.placesInReview.observeAsState(listOf())
    val modifiedPlaces = viewModel.modifiedPlaces.observeAsState(listOf())
    LaunchedEffect(key1 = 1) {
        viewModel.showPlacesInReview()
        viewModel.showModifiedPlaces()
    }

    Scaffold(
        content = { padding ->
            Column(
                modifier = Modifier
                    .padding(0.dp, 0.dp, 0.dp, 38.dp)
                    .background(MaterialTheme.colorScheme.background)
                    .fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.review_title),
                    style = MaterialTheme.typography.headlineSmall.copy(
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Bold,
                    ),
                    modifier = Modifier
                        .padding(8.dp)
                        .align(Alignment.CenterHorizontally),
                )
                Text(
                    text = stringResource(id = R.string.modified_places_for_review),
                    color = Color.Gray,
                    modifier = Modifier
                        .padding(16.dp, 8.dp, 0.dp, 0.dp),
                )
                LazyColumn(
                    contentPadding = PaddingValues(8.dp),
                    modifier = Modifier.weight(1f, fill = false)
                ) {
                    items(modifiedPlaces.value) {
                        PlaceListItem(
                            place = it,
                            onClickPlaceItem = onClickPlaceItem,
                            isModifiedPlace = true
                        )
                    }
                }
                Text(
                    text = stringResource(id = R.string.new_places_for_review),
                    color = Color.Gray,
                    modifier = Modifier
                        .padding(16.dp, 8.dp, 0.dp, 0.dp),
                )
                LazyColumn(
                    contentPadding = PaddingValues(8.dp),
                    modifier = Modifier.weight(1f, fill = false)
                ) {
                    items(newPlaces.value) {
                        PlaceListItem(
                            place = it,
                            onClickPlaceItem = onClickPlaceInReviewItem,
                            isModifiedPlace = false
                        )
                    }
                }
            }
        }
    )
}
