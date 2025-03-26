package dev.estaki.myFinancialApp.presentation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import dev.estaki.myFinancialApp.presentation.addAndEditBankCard.AddOrEditCreditCardScreen
import dev.estaki.myFinancialApp.presentation.detailScreen.TransactionDetail
import dev.estaki.myFinancialApp.presentation.main.MainScreen
import dev.estaki.myFinancialApp.presentation.splash.MySplashScreen
import dev.estaki.myFinancialApp.presentation.states.MyTopAppBarState
import dev.estaki.ui_utils.ui.theme.ariaFaNumFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Nav(modifier: Modifier = Modifier,navController: NavHostController) {
    val scrollBehavior =TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

    var topAppbarState by remember {
        mutableStateOf(MyTopAppBarState(
            title = "مدیریت اتوماتیک دخل و خرج",
            scrollBehavior = scrollBehavior
        ))
    }

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(

                title = {
                    Text(
                        text = topAppbarState.title,
                        fontFamily = ariaFaNumFontFamily,
                        fontWeight = FontWeight.Black,
                        fontSize = 15.sp,
                        modifier = Modifier.padding(12.dp)
                    )
                },
                navigationIcon = topAppbarState.navigationIcon,
                actions = topAppbarState.actions,
                scrollBehavior = topAppbarState.scrollBehavior
            )
        }
    ) { innerPadding ->
        NavHost(
            modifier = modifier.padding(innerPadding),
            navController = navController,
            startDestination = "SplashScreen"
        ) {
            composable(
                route = "SplashScreen",
            ) {
                MySplashScreen(navController = navController){
                    topAppbarState = it
                }
            }
            composable(
                route = "MainScreen",
            ) {
                MainScreen(navController){
                    topAppbarState = it
                }
            }
            composable(
                route = "AddDetailScreen/{smsId}", arguments = listOf(
                    navArgument("smsId") {
                        type = NavType.LongType
                    }
                )) { backStackEntry ->
                TransactionDetail(
                    smsId = backStackEntry.arguments?.getLong("smsId")!!,
                    navController = navController
                ){
                    topAppbarState = it
                }
            }

            composable(
                route = "AddOrEditCreditCard/{creditAccountNumber}/{position}",
                arguments = listOf(
                    navArgument("creditAccountNumber") {
                        type = NavType.StringType
                    },
                    navArgument("position") {
                        type = NavType.IntType
                    }
                )) { backStackEntry ->
                AddOrEditCreditCardScreen(
                    creditAccountNumber = backStackEntry.arguments?.getString("creditAccountNumber")!!,
                    position = backStackEntry.arguments?.getInt("position")!!,
                    navController = navController
                ){
                    topAppbarState = it
                }
            }

        }
    }
}