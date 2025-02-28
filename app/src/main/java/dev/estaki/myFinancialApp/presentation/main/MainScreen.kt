package dev.estaki.myFinancialApp.presentation.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import dev.estaki.myFinancialApp.presentation.ShimmerListItems
import dev.estaki.myFinancialApp.ui.theme.ariaFaNumFontFamily
import dev.estaki.ui_utils.components.CreditCard
import kotlin.math.absoluteValue
import androidx.compose.runtime.getValue
import dev.estaki.myFinancialApp.presentation.intent.MainScreenActions
import dev.estaki.myFinancialApp.presentation.states.MainScreenState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController?, viewModel: MainViewModel = hiltViewModel()) {

    val state by viewModel.smsList.collectAsState()

    MainScreenUi(
        state = state,
        navController = navController,
        onActions = viewModel::onAction
    )


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenUi(
    modifier: Modifier = Modifier,
    navController: NavHostController?,
    state: MainScreenState,
    onActions: (MainScreenActions) -> Unit
) {
    LaunchedEffect(key1 = false) {
        onActions.invoke(MainScreenActions.LoadSms())
    }
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MediumTopAppBar(

                title = {
                    Text(
                        text = "سلام ممد جون👋    مدیریت اتوماتیک دخل و خرج",
                        fontFamily = ariaFaNumFontFamily,
                        fontWeight = FontWeight.Black,
                        fontSize = 12.sp,
                        modifier = Modifier.padding(12.dp)
                    )
                },

//                            navigationIcon = {
//                                IconButton(onClick = { /*TODO*/ }) {
//                                    Icon(
//                                        imageVector = Icons.Rounded.ArrowBack,
//                                        contentDescription = "back"
//                                    )
//                                }
//                            },
//                            actions = {
//                                IconButton(onClick = { /* do something */ }) {
//                                    Icon(
//                                        imageVector = Icons.Filled.Menu,
//                                        contentDescription = "Localized description"
//                                    )
//                                }
//                            },
                scrollBehavior = scrollBehavior
            )
        }
    ) { innerPadding ->


        Column(modifier = Modifier.padding(innerPadding)) {
            BankCardView()
            Surface(shape = RoundedCornerShape(topStart = 26.dp, topEnd = 26.dp)) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .wrapContentHeight(),
                    contentPadding = PaddingValues(vertical = 8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    items(if (state.isLoading) 5 else state.smsList.size) { itemIndex ->

                        ShimmerListItems(
                            isLoading = state.isLoading,
                            contentAfterLoading = {
                                if (state.smsList.isNotEmpty()) {
                                    MyCardItem(
                                        state.smsList[itemIndex],
                                        onCardClick = { navController?.navigate("AddDetailScreen/${state.smsList[itemIndex].id}") })
                                }

                            })

                    }
                }
            }

        }

    }
}


@Composable
fun BankCardView(modifier: Modifier = Modifier) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0.1F,
        pageCount = { 10 })
    Text(
        "حساب های موجود در پیامک ها",
        modifier = Modifier
            .padding(horizontal = 8.dp)
            .padding(top = 22.dp, bottom = 8.dp),
        fontSize = 14.sp,
        fontWeight = FontWeight.Black
    )
    Surface(modifier = Modifier.padding(bottom = 8.dp)) {
        HorizontalPager(
            state = pagerState,
            pageSpacing = 8.dp,
            contentPadding = PaddingValues(horizontal = 22.dp)
        ) { page ->
            Surface(modifier = Modifier
                .height(225.dp)
                .graphicsLayer {
                    // Calculate the absolute offset for the current page from the
                    // scroll position. We use the absolute value which allows us to mirror
                    // any effects for both directions
                    val pageOffset = (
                            (pagerState.currentPage - page) + pagerState
                                .currentPageOffsetFraction
                            ).absoluteValue

                    // We animate the alpha, between 50% and 100%
                    alpha = lerp(
                        start = 0.5f,
                        stop = 1f,
                        fraction = 1f - pageOffset.coerceIn(0f, 1f)
                    )
                }) {
                CreditCard()
            }
        }
    }
    Row(
        Modifier
            .wrapContentHeight()
            .fillMaxWidth()
            .padding(bottom = 8.dp),
        horizontalArrangement = Arrangement.Center
    ) {
        repeat(pagerState.pageCount) { iteration ->
            val color =
                if (pagerState.currentPage == iteration) Color.DarkGray else Color.LightGray
            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .clip(CircleShape)
                    .background(color)
                    .size(6.dp)
            )
        }
    }
}















@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainScreenPreview() {
    MainScreen(null)
}


