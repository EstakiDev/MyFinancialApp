package dev.estaki.myFinancialApp.presentation.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.util.lerp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.navigation.NavHostController
import com.ehsanmsz.mszprogressindicator.progressindicator.BallPulseProgressIndicator
import dev.estaki.domain.error.MyCustomSnackBarType
import dev.estaki.domain.models.BankCardModel
import dev.estaki.myFinancialApp.presentation.actions.MainScreenActions
import dev.estaki.myFinancialApp.presentation.states.MainScreenState
import dev.estaki.myFinancialApp.presentation.states.MyTopAppBarState
import dev.estaki.ui_utils.SnackBarController
import dev.estaki.ui_utils.SnackBarEvent
import dev.estaki.ui_utils.components.AddCreditCard
import dev.estaki.ui_utils.components.CreditCard
import dev.estaki.ui_utils.ui.theme.ColorTextGrayOnDarkTheme
import dev.estaki.ui_utils.ui.theme.ColorTextGrayOnLiteTheme
import dev.estaki.ui_utils.utils.isScrollingUp
import kotlinx.coroutines.launch
import timber.log.Timber
import kotlin.math.absoluteValue


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavHostController?,
    viewModel: MainViewModel = hiltViewModel(),
    onComposing: (MyTopAppBarState) -> Unit
) {
    val state by viewModel.mainScreenState.collectAsState()
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            when (event) {
                Lifecycle.Event.ON_START -> {
                    // Code to execute when composable is visible
                    Timber.i("Composable is visible")
                    onComposing(
                        MyTopAppBarState(
                            title = "مدیریت اتوماتیک دخل و خرج",
                        )
                    )

                }

                Lifecycle.Event.ON_RESUME -> {
                    // Code to execute when composable is in foreground
                    Timber.i("Composable is in foreground")

                }

                Lifecycle.Event.ON_STOP -> {
                    // Code to execute when composable is in background
                    Timber.i("Composable is in background")
                }

                Lifecycle.Event.ON_DESTROY -> {
                    // Code to execute when composable is destroyed
                    Timber.i("Composable is destroyed")
                }

                else -> {}
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    DisposableEffect(Unit, navController) {
        viewModel.onAction(MainScreenActions.LoadCardsFromDb)
        onDispose {
            viewModel.prepareDataForEditOrCreateCard()
        }
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
    var firstInit by remember { mutableStateOf(true) }
    val lazyColumnState = rememberLazyListState()
    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0F,
        pageCount = { state.listBankAccountNumber.size + 1 })
    var cardPosition by remember { mutableIntStateOf(0) }
    val scope = rememberCoroutineScope()
    val isScrollingUp = lazyColumnState.isScrollingUp().value
    LaunchedEffect(
        state.listBankAccountNumber,
        state.smsList,
        state.isLoading,
        state.currentBankAccountNumber
    ) {
//        if (state.listBankAccountNumber.isNotEmpty()) {
//            onActions.invoke(MainScreenActions.ReloadSmsByScrollCards(state.listBankAccountNumber[cardPosition].bankAccountNumber))
//        } else
        if (state.listBankAccountNumber.isEmpty() && state.smsList.isEmpty() && state.isLoading.not()) {
            SnackBarController.sendEvent(
                event = SnackBarEvent(
                    message = "متاسفانه هیچ تراکنش بانکی ار درون پیامک های شما یافت نشد! \n شما میتوانید به صورت دستی کارت اعتباری و تراکنش جدید تعریف کنید. ",
                    type = MyCustomSnackBarType.ERROR
                )
            )
        }
    }
    Box(Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {
            if (state.listBankAccountNumber.isNotEmpty()) {
                AnimatedVisibility(
                    visible = if (firstInit) true else if (state.smsList.size > 7) isScrollingUp else true,
                    enter = expandVertically(),
                ) {
                    LaunchedEffect(key1 = Unit) {
                        if (firstInit) {
                            firstInit = false
                        }
                    }
                    BankCardView(
                        modifier = modifier,
                        listOfBankAccountNumber = state.listBankAccountNumber,
                        pagerState = pagerState,
                        onItemClicked = { bankAccountNumber, position, isEditMode ->
                            if (isEditMode) {
                                navController.navigate(
                                    "AddOrEditCreditCard/$bankAccountNumber/$position"
                                ) {
                                    popUpTo("AddOrEditCreditCard/$bankAccountNumber/$position") {
                                        inclusive = true
                                    }
                                }
                            } else {
                                navController.navigate(
                                    "AddOrEditCreditCard/$bankAccountNumber/0"
                                ) {
                                    popUpTo("AddOrEditCreditCard/$bankAccountNumber/0") {
                                        inclusive = true
                                    }
                                }
                            }
                        }
                    ) { bankAccountNumber, page ->
                        cardPosition = page
                        onActions.invoke(MainScreenActions.ReloadSmsByScrollCards(bankAccountNumber))
                    }
                    scope.launch {
                        pagerState.animateScrollToPage(cardPosition)
                    }
                }
            } else {
                BankCardView(
                    modifier = modifier,
                    listOfBankAccountNumber = emptyList(),
                    pagerState = pagerState,
                    onItemClicked = { bankAccountNumber, position, isEditMode ->
                        if (isEditMode) {
                            navController.navigate(
                                "AddOrEditCreditCard/$bankAccountNumber/$position"
                            ) {
                                popUpTo("AddOrEditCreditCard/$bankAccountNumber/$position") {
                                    inclusive = true
                                }
                            }
                        } else {
                            navController.navigate(
                                "AddOrEditCreditCard/$bankAccountNumber/0"
                            ) {
                                popUpTo("AddOrEditCreditCard/$bankAccountNumber/0") {
                                    inclusive = true
                                }
                            }
                        }
                    },
                    onScroll = { bankAccountNumber, position ->
                        onActions.invoke(MainScreenActions.ReloadSmsByScrollCards(bankAccountNumber))
                    }
                )
            }

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
                Column {
                    Row {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                                .alpha(if (state.smsList.isNotEmpty()) 1F else 0F)
                        ) {
                            Text(
                                text = "تعداد تراکنش های این حساب: ",
                                fontWeight = FontWeight.Medium,
                                fontSize = 14.sp
                            )
                            Text(
                                text = "${state.smsList.size} عدد ",
                                fontWeight = FontWeight.Medium,
                                fontSize = 14.sp
                            )
                        }
                    }
                    Surface(shape = RoundedCornerShape(topStart = 26.dp, topEnd = 26.dp)) {

                        LazyColumn(
                            modifier = Modifier
                                .fillMaxSize()
                                .wrapContentHeight(),
                            contentPadding = PaddingValues(
                                top = 4.dp,
                                start = 4.dp,
                                end = 4.dp,
                                bottom = 150.dp
                            ),
                            verticalArrangement = Arrangement.spacedBy(8.dp),
                            state = lazyColumnState
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

        }

        FloatingActionButton(
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(12.dp),
            onClick = {
                scope.launch {
                    if (state.listBankAccountNumber.isEmpty()) {
                        SnackBarController.sendEvent(
                            event = SnackBarEvent(
                                message = "افزودن تراکنش بانکی پس از تعریف یک کارت اعتباری و شماره حساب امکان پذیر خواهد بود. \n لطفا ابتدا کارت اعتباری را اضافه کنید. ",
                                type = MyCustomSnackBarType.ERROR
                            )
                        )
                    } else {
                        navController.navigate("AddDetailScreen/0")
                    }
                }

            }
        ) {
            Icon(
                imageVector = Icons.Rounded.Add,
                contentDescription = "Edit"
            )
        }

    }


}


@Composable
fun BankCardView(
    modifier: Modifier = Modifier,
    listOfBankAccountNumber: List<BankCardModel>,
    pagerState: PagerState,
    onItemClicked: (bankAccountNumber: String, position: Int, isEditMode: Boolean) -> Unit,
    onScroll: (bankAccountNumber: String, position: Int) -> Unit,
) {


    LaunchedEffect(pagerState) {
        snapshotFlow { pagerState.currentPage }.collect { page ->
            onScroll.invoke(
                if (pagerState.currentPage < listOfBankAccountNumber.size) listOfBankAccountNumber[pagerState.currentPage].bankAccountNumber else "",
                page
            )
        }
    }

    Column {
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
                            onItemClicked.invoke(bankAccountNumber, page, false)
                        }
                    else
                        if (listOfBankAccountNumber.isNotEmpty()) {
                            CreditCard(
                                item = listOfBankAccountNumber[pagerState.currentPage],
                                position = page
                            ) {
                                val bankAccountNumber =
                                    listOfBankAccountNumber[pagerState.currentPage].bankAccountNumber
                                onItemClicked.invoke(bankAccountNumber, page, true)

                            }
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


}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainScreenPreview() {
    MainScreen(null) {}
}


