package dev.estaki.myFinancialApp.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import dev.estaki.myFinancialApp.ui.theme.ariaFaNumFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController?, viewModel: MainViewModel = hiltViewModel()) {


    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val isloading = viewModel.isLoading.observeAsState().value ?: true
    viewModel.getAllSms()
    val smsList = viewModel.smsLiveDataList.observeAsState()
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),

        topBar = {
            MediumTopAppBar(

                title = {

                    Column {
                        Text(
                            text = "سلام ممد جون👋🏻 ",
                            fontFamily = ariaFaNumFontFamily,
                            fontWeight = FontWeight.Black,
                            fontSize = 12.sp
                        )
                        Text(
                            text = "مدیریت اتوماتیک دخل و خرج ",
                            fontFamily = ariaFaNumFontFamily,
                            fontWeight = FontWeight.Black,
                            fontSize = 15.sp
                        )
                    }

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


        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentHeight(),
            contentPadding = innerPadding,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            userScrollEnabled = true,

            ) {
            items(if (smsList.value?.isEmpty() == true) 10 else smsList.value?.size!!) { itemIndex ->

                ShimmerListItems(
                    isLoading = isloading,
                    contentAfterLoading = {
                        MyCardItemNew(
                            smsList.value!![itemIndex],
                            onCardClick = { navController?.navigate("AddDetailScreen") })
                    })

            }
            smsList.value?.let {

            }
        }


    }


}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainScreenPreview() {
    MainScreen(null)
}


