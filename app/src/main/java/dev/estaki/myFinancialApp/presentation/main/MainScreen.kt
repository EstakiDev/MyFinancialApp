package dev.estaki.myFinancialApp.presentation.main

import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
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
import com.ehsanmsz.mszprogressindicator.progressindicator.BallPulseProgressIndicator
import dev.estaki.domain.models.BankCardModel
import dev.estaki.myFinancialApp.presentation.actions.MainScreenActions
import dev.estaki.myFinancialApp.presentation.states.MainScreenState
import dev.estaki.myFinancialApp.presentation.states.MyTopAppBarState
import dev.estaki.ui_utils.components.AddCreditCard
import dev.estaki.ui_utils.components.CreditCard
import dev.estaki.ui_utils.ui.theme.ColorTextGrayOnDarkTheme
import dev.estaki.ui_utils.ui.theme.ColorTextGrayOnLiteTheme
import kotlin.math.absoluteValue


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavHostController?,
    viewModel: MainViewModel = hiltViewModel(),
    onComposing: (MyTopAppBarState) -> Unit
) {

    LaunchedEffect(true) {
        onComposing(
            MyTopAppBarState(
                title = "مدیریت اتوماتیک دخل و خرج",
            )
        )
    }
    val state by viewModel.mainScreenState.collectAsState()
    LaunchedEffect(key1 = true) {
        viewModel.onAction(MainScreenActions.LoadCardsFromDb)
    }

    MainScreenUi(
        modifier = Modifier.fillMaxSize(),
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
    viewModel: MainViewModel = hiltViewModel(),
    onActions: (MainScreenActions) -> Unit
) {
    Box(Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            if (state.listBankAccountNumber.isNotEmpty()) {
                BankCardView(
                    modifier = modifier,
                    listOfBackAccountNumber = state.listBankAccountNumber,
                    navController = navController,
                    onItemClicked = { bankAccountNumber, position ,isEditMode ->
                        viewModel.prepareDataForEditOrCreatCard()
                        if (isEditMode){
                            navController.navigate(
                                "AddOrEditCreditCard/$bankAccountNumber/$position"
                            )
                        }else{
                            navController.navigate(
                                "AddOrEditCreditCard/$bankAccountNumber/0"
                            )
                        }
                    }
                ) { bankAccountNumber ->
                    onActions.invoke(MainScreenActions.ReloadSmsByScrollCards(bankAccountNumber))
                }
            }

            Surface(shape = RoundedCornerShape(topStart = 26.dp, topEnd = 26.dp)) {
                if (state.isLoading) {
                    Box(modifier.fillMaxSize()) {
                        BallPulseProgressIndicator(
                            modifier = Modifier
                                .align(Alignment.Center)
                                .padding(0.dp, 32.dp),
                            color = if (isSystemInDarkTheme()) ColorTextGrayOnDarkTheme else ColorTextGrayOnLiteTheme,
                            animationDuration = 800,
                            animationDelay = 200,
                            startDelay = 0,
                            ballCount = 3,
                            maxBallDiameter = 13.dp

                        )
                    }

                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .wrapContentHeight(),
                        contentPadding = PaddingValues( 4.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {

                        items(state.smsList.size) { itemIndex ->
                            MyCardItem(
                                state.smsList[itemIndex],
                                onCardClick = { navController.navigate("AddDetailScreen/${state.smsList[itemIndex].id}") })
//                                ShimmerListItems(
//                                    isLoading = state.isLoading,
//                                    contentAfterLoading = {
//                                        if (state.smsList.isNotEmpty()) {
//
//                                        }
//
//                                    })

                        }
                    }
                }
            }

        }

        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(12.dp),
            onClick = {
                navController.navigate("AddDetailScreen/0")
            }
        ) {
            Icon(
                imageVector = Icons.Rounded.Edit,
                contentDescription = "Edit"
            )
        }

    }


}


@Composable
fun BankCardView(
    modifier: Modifier = Modifier,
    listOfBackAccountNumber: List<BankCardModel>,
    navController: NavHostController,
    onItemClicked: (bankAccountNumber: String,position: Int,isEditMode: Boolean) -> Unit,
    onScroll: (bankAccountNumber: String) -> Unit,
) {
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0F,
        pageCount = { listOfBackAccountNumber.size + 1 })

    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            onScroll.invoke(if (pagerState.currentPage < listOfBackAccountNumber.size) listOfBackAccountNumber[pagerState.currentPage].bankAccountNumber else "")
        }
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
                        onItemClicked.invoke(bankAccountNumber,page,false)
                    }
                else
                    CreditCard(
                        item = listOfBackAccountNumber[pagerState.currentPage],
                        position = page
                    ) {
                        val bankAccountNumber =
                            listOfBackAccountNumber[pagerState.currentPage].bankAccountNumber
                        onItemClicked.invoke(bankAccountNumber,page,true)

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
    MainScreen(null) {}
}


