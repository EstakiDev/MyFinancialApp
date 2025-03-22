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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import dev.estaki.domain.models.BankCardModel
import dev.estaki.myFinancialApp.presentation.ShimmerListItems
import dev.estaki.myFinancialApp.presentation.actions.MainScreenActions
import dev.estaki.myFinancialApp.presentation.states.MainScreenState
import dev.estaki.myFinancialApp.presentation.states.MyTopAppBarState
import dev.estaki.ui_utils.components.AddCreditCard
import dev.estaki.ui_utils.components.CreditCard
import kotlin.math.absoluteValue


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController?, viewModel: MainViewModel = hiltViewModel(),onComposing :(MyTopAppBarState) -> Unit) {

    LaunchedEffect(true) {
        onComposing(MyTopAppBarState(
            title = "مدیریت اتوماتیک دخل و خرج",
        ))
    }
    val state by viewModel.smsList.collectAsState()

    MainScreenUi(
        state = state,
        navController = navController!!,
        onActions = viewModel::onAction
    )


}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenUi(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    state: MainScreenState,
    onActions: (MainScreenActions) -> Unit
) {
    LaunchedEffect(key1 = false) {
        onActions.invoke(MainScreenActions.LoadSms())
    }

    Column(modifier = Modifier) {
        if (state.listBankAccountNumber.isNotEmpty()) {
            BankCardView(
                modifier = modifier,
                listOfBackAccountNumber = state.listBankAccountNumber,
                navController = navController
            ) { bankAccountNumber ->
                onActions.invoke(MainScreenActions.ReloadSmsByScrollCards(bankAccountNumber))
            }
        }
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


@Composable
fun BankCardView(
    modifier: Modifier = Modifier,
    listOfBackAccountNumber: List<BankCardModel>,
    navController: NavHostController,
    onScroll: (bankAccountNumber: String) -> Unit,
) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0.1F,
        pageCount = { listOfBackAccountNumber.size + 1 })

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            onScroll.invoke(if (pagerState.currentPage < listOfBackAccountNumber.size) listOfBackAccountNumber[pagerState.currentPage].bankAccountNumber else "")
        }
    }
    LaunchedEffect(Unit) {
        pagerState.animateScrollToPage(0)
    }
    Text(
        "حساب های موجود در پیامک ها",
        modifier = Modifier
            .padding(start = 22.dp, top = 22.dp, bottom = 12.dp),
        fontSize = 14.sp,
        fontWeight = FontWeight.Black
    )
    Surface(modifier = Modifier.padding(bottom = 8.dp)) {
        HorizontalPager(
            state = pagerState,
            contentPadding = PaddingValues(horizontal = 22.dp),
            pageSpacing = 8.dp,
        ) { page ->

            Surface(
                modifier = Modifier
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
                if (pagerState.currentPage == pagerState.pageCount - 1)
                    AddCreditCard {
                        val bankAccountNumber = ""
                        navController.navigate(
                            "AddOrEditCreditCard/$bankAccountNumber/0"
                        )
                    }
                else
                    CreditCard(
                        item = listOfBackAccountNumber[pagerState.currentPage],
                        position = page
                    ) {
                        val bankAccountNumber =
                            listOfBackAccountNumber[pagerState.currentPage].bankAccountNumber
                        navController.navigate(
                            "AddOrEditCreditCard/$bankAccountNumber/$page"
                        )
                    }

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
                if (pagerState.currentPage == iteration) Color.LightGray else Color.DarkGray
            Box(
                modifier = Modifier
                    .padding(2.dp)
                    .clip(CircleShape)
                    .background(color)
                    .size(
                        height = 6.dp,
                        width = if (pagerState.currentPage == iteration) 18.dp else 6.dp
                    )
            )
        }
    }
}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainScreenPreview() {
    MainScreen(null){}
}


