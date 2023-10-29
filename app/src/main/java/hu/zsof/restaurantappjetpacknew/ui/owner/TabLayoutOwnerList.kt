package hu.zsof.restaurantappjetpacknew.ui.owner

import androidx.compose.foundation.layout.*
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.*
import com.google.accompanist.pager.*
import hu.zsof.restaurantappjetpacknew.R
import hu.zsof.restaurantappjetpacknew.model.enums.PlaceType
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabLayoutOwnerList(
    onClickPlaceItem: (Long) -> Unit,
    onClickPlaceInReviewItem: (Long) -> Unit
) {
    val pagerState = rememberPagerState(pageCount = 2, infiniteLoop = true)

    Column() {
        Tabs(pagerState = pagerState)
        TabsContent(
            pagerState = pagerState,
            onClickPlaceItem = onClickPlaceItem,
            onClickPlaceInReviewItem = onClickPlaceInReviewItem
        )
    }
}

@ExperimentalPagerApi
@Composable
fun Tabs(pagerState: PagerState) {
    val list = listOf(
        stringResource(R.string.tab_accepted_places) to Icons.Default.Details,
        stringResource(R.string.tab_wait_for_accept_places) to Icons.Default.Image,
    )

    val scope = rememberCoroutineScope()
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = MaterialTheme.colorScheme.secondary,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                height = 2.dp,
                color = Color.Black,
            )
        },
    ) {
        list.forEachIndexed { index, _ ->
            Tab(
                text = {
                    Text(
                        list[index].first,
                        color = if (pagerState.currentPage == index)
                            MaterialTheme.colorScheme.onSurfaceVariant
                        else MaterialTheme.colorScheme.surfaceVariant,
                    )
                },
                selected = pagerState.currentPage == index,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@ExperimentalPagerApi
@Composable
fun TabsContent(
    pagerState: PagerState,
    onClickPlaceItem: (Long) -> Unit,
    onClickPlaceInReviewItem: (Long) -> Unit
) {
    HorizontalPager(state = pagerState) { page ->
        when (page) {
            0 -> OwnerPlaceListScreen(
                onClickPlaceItem = onClickPlaceItem,
                onClickPlaceInReviewItem = onClickPlaceInReviewItem,
                placeType = PlaceType.PLACE
            )

            1 -> OwnerPlaceListScreen(
                onClickPlaceItem = onClickPlaceItem,
                onClickPlaceInReviewItem = onClickPlaceInReviewItem,
                placeType = PlaceType.PLACE_IN_REVIEW
            )
        }
    }
}
