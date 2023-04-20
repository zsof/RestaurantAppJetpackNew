package hu.zsof.restaurantappjetpacknew.ui.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.*
import com.google.accompanist.pager.*
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun TabLayout(placeId: Long) {
    val pagerState = rememberPagerState(pageCount = 3, infiniteLoop = true)

    Column(
        modifier = Modifier.background(Color.White),
    ) {
        Tabs(pagerState = pagerState)
        TabsContent(pagerState = pagerState, placeId = placeId)
    }
}

@ExperimentalPagerApi
@Composable
fun Tabs(pagerState: PagerState) {
    val list = listOf(
        "Details" to Icons.Default.Details,
        "Images" to Icons.Default.Image,
        "Comments" to Icons.Default.Comment,
    )

    val scope = rememberCoroutineScope()
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        contentColor = Color.White,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.pagerTabIndicatorOffset(pagerState, tabPositions),
                height = 2.dp,
                color = Color.White,
            )
        },
    ) {
        list.forEachIndexed { index, _ ->
            Tab(
                text = {
                    Text(
                        list[index].first,
                        color = if (pagerState.currentPage == index) Color.White else Color.LightGray,
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
fun TabsContent(pagerState: PagerState, placeId: Long) {
    HorizontalPager(state = pagerState) { page ->
        println("horizontal $placeId")
        when (page) {
            0 -> DetailsScreen(placeId = placeId)
            1 -> TabContentScreen(data = "Welcome to Image Screen")
            2 -> TabContentScreen(data = "Welcome to Comments Screen")
        }
    }
}

@Composable
fun TabContentScreen(data: String) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = data,
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )
    }
}