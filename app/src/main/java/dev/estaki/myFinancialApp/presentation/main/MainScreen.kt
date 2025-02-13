package dev.estaki.myFinancialApp.presentation.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import dev.estaki.myFinancialApp.presentation.ShimmerListItems
import dev.estaki.myFinancialApp.ui.theme.ariaFaNumFontFamily

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navController: NavHostController?, viewModel: MainViewModel = hiltViewModel()) {


    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val isloading = viewModel.isLoading.observeAsState().value ?: true
    viewModel.getAllSms()
    val smsList = viewModel.smsList.collectAsState()

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


        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentHeight(),
            contentPadding = innerPadding,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            userScrollEnabled = true,

            ) {
            items(if (smsList.value.isEmpty()) 3 else smsList.value.size) { itemIndex ->

                ShimmerListItems(
                    isLoading = isloading,
                    contentAfterLoading = {
                        if (smsList.value.isNotEmpty()) {
                            MyCardItem(
                                smsList.value[itemIndex],
                                onCardClick = { navController?.navigate("AddDetailScreen/${smsList.value[itemIndex].id}") })
                        }

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


