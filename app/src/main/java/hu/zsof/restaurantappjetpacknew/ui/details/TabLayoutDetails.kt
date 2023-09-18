package hu.zsof.restaurantappjetpacknew.ui.details

import androidx.compose.foundation.layout.*
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.*
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabLayoutDetails(placeId: Long, onEditPlaceClick: (Long) -> Unit) {
    val pagerState = rememberPagerState(pageCount = 2, infiniteLoop = true)

    Column() {
        Tabs(pagerState = pagerState)
        TabsContent(
            pagerState = pagerState,
            placeId = placeId,
            onEditPlaceClick = onEditPlaceClick,
        )
    }
}

@ExperimentalPagerApi
@Composable
fun Tabs(pagerState: PagerState) {
    val list = listOf(
        "Részletek" to Icons.Default.Details,
        "Kommentek" to Icons.Default.Image,
    )

    val scope = rememberCoroutineScope()
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = MaterialTheme.colorScheme.secondary,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier
                    .pagerTabIndicatorOffset(pagerState, tabPositions),
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
                        color = if (pagerState.currentPage == index) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.surfaceVariant,
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

@ExperimentalPagerApi
@Composable
fun TabsContent(pagerState: PagerState, placeId: Long, onEditPlaceClick: (Long) -> Unit) {
    HorizontalPager(state = pagerState) { page ->
        when (page) {
            0 -> DetailsMainScreen(
                placeId = placeId,
                onEditPlaceClick = onEditPlaceClick,
            )

            1 -> DetailsCommentScreen(placeId = placeId)
        }
    }
}
