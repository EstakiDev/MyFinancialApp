package dev.estaki.myFinancialApp.presentation.detailScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ehsanmsz.mszprogressindicator.progressindicator.BallPulseProgressIndicator
import com.gmail.hamedvakhide.compose_jalali_datepicker.JalaliDatePickerDialog
import dev.estaki.domain.models.BankCardModel
import dev.estaki.domain.models.SmsModel
import dev.estaki.domain.models.TransactionType
import dev.estaki.myFinancialApp.presentation.actions.TransactionDetailScreenActions
import dev.estaki.myFinancialApp.presentation.main.MyCardItem
import dev.estaki.myFinancialApp.presentation.states.MyTopAppBarState
import dev.estaki.myFinancialApp.presentation.states.TransactionDetailScreenState
import dev.estaki.myFinancialApp.presentation.timepicker.MyTimePicker
import dev.estaki.ui_utils.R
import dev.estaki.ui_utils.components.AmountTextField
import dev.estaki.ui_utils.components.MyOutlinedButton
import dev.estaki.ui_utils.ui.theme.ColorTextGrayOnDarkTheme
import dev.estaki.ui_utils.ui.theme.ColorTextGrayOnLiteTheme
import dev.estaki.ui_utils.ui.theme.DarkYellow
import dev.estaki.ui_utils.ui.theme.LiteWhite
import dev.estaki.ui_utils.ui.theme.ariaFaNumFontFamily
import ir.huri.jcal.JalaliCalendar
import timber.log.Timber
import java.util.Date


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionDetail(
    detailScreenViewModel: TransactionDetailScreenViewModel = hiltViewModel(),
    navController: NavHostController? = null,
    smsId: Long,
    onComposing: (MyTopAppBarState) -> Unit
) {
    val state by detailScreenViewModel.state.collectAsState()

    DisposableEffect(true) {
        onComposing(
            MyTopAppBarState(
                title = if (smsId != 0L) "ویرایش اطلاعات تراکنش" else "افزودن تراکنش جدید",
                navigationIcon = {
                    IconButton(onClick = {
                        navController?.navigateUp()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                            contentDescription = "back"
                        )
                    }
                }
            )
        )
        onDispose {
        }
    }

    LaunchedEffect(false) {
        detailScreenViewModel.onAction(
            TransactionDetailScreenActions.LoadTransaction(
                smsId = smsId
            )
        )
    }
    TransactionDetailUi(
        onAction = detailScreenViewModel::onAction,
        navController = navController,
        state = state
    )

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionDetailUi(
    modifier: Modifier = Modifier,
    state: TransactionDetailScreenState,
    onAction: (TransactionDetailScreenActions) -> Unit,
    navController: NavHostController? = null
) {

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {

        val scrollState = rememberScrollState()
        var selectedTransactionType by remember { mutableStateOf(TransactionType.DEPOSIT) }
        var bankCardDropDownExpanded by remember { mutableStateOf(false) }
        var menuItemData by remember { mutableStateOf<List<BankCardModel>>(state.bankCardList) }
        var bankAccountNumber by remember {
            mutableStateOf(
                state.smsModel?.bankAccountNumber ?: ""
            )
        }

        var text by rememberSaveable {
            mutableStateOf("")
        }
        var localCategoryList by remember { mutableStateOf(state.categoryList) }
        LaunchedEffect(key1 = state) {
            if (state.categoryList.isEmpty().not()) {
                localCategoryList = state.categoryList
            }
            state.smsModel?.let { smsM ->
                if (smsM.categoryIds.isNotEmpty())
                    localCategoryList = state.categoryList.map {
                        if (smsM.categoryIds.contains(it.id))
                            it.copy(isChecked = true)
                        else
                            it
                    }
            }
        }
        LaunchedEffect(key1 = state.bankCardList) {
            menuItemData = state.bankCardList
        }

        LaunchedEffect(key1 = state.smsModel) {
            bankAccountNumber = state.smsModel?.bankAccountNumber ?: ""
        }
        val context = LocalContext.current
        var amount by remember {
            mutableStateOf(
                TextFieldValue(
                    state.smsModel?.transactionAmount ?: "",
                    selection = TextRange(state.smsModel?.transactionAmount?.length ?: 0)
                )
            )
        }
        var bankName by remember { mutableStateOf(state.smsModel?.bankName ?: "") }
        var time by remember { mutableStateOf(state.smsModel?.transactionTime ?: "") }
        var date by remember { mutableStateOf(state.smsModel?.transactionDate ?: "") }
        val coroutine = rememberCoroutineScope()
        val datePickerState = remember { mutableStateOf(false) }
        val timePickerState = remember { mutableStateOf(false) }
        var initialDateForDatePicker by remember {
            mutableStateOf(listOf<String>())
        }
        val dateInteractionSource = remember {
            MutableInteractionSource()
        }
        val timeInteractionSource = remember {
            MutableInteractionSource()
        }
        val segmentedButtonList = mapOf<TransactionType, String>(
            TransactionType.DEPOSIT to "دخل",
            TransactionType.WITHDRAW to "خرج"
        )


        val newSmsModel = state.smsModel?.copy(
            transactionAmount = amount.text,
            bankName = bankName,
            transactionTime = time,
            transactionDate = date,
            transactionType = selectedTransactionType
        ) ?: SmsModel(
            id = null,
            bankName = bankName,
            bankAccountNumber = "",
            transactionType = selectedTransactionType,
            transactionAmount = amount.text,
            transactionDate = date,
            transactionDateTime = Date(System.currentTimeMillis()).time,
            transactionTime = time,
            bankCardBalance = "",
            listOf(),
            null,
            "",
            ""
        )


        LaunchedEffect(key1 = state.smsModel) {
            state.smsModel?.let {
                amount = TextFieldValue(it.transactionAmount)
                bankName = it.bankName
                time = it.transactionTime
                date = it.transactionDate
                text = it.description ?: ""
                selectedTransactionType = it.transactionType
            }

        }

        Box(
            Modifier.fillMaxSize(),
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .alpha(if (state.isLoading) 0f else 1f)
                    .padding(bottom = 92.dp)
            ) {
                Spacer(Modifier.size(8.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    MyCardItem(smsModel = newSmsModel) { }

                    Spacer(modifier = Modifier.size(12.dp))
                    Row(modifier = Modifier.fillMaxWidth()) {
                        AmountTextField(
                            amount = amount,
                            modifier = Modifier.fillMaxWidth(0.5F),
                            unit = "ريال"
                        ) {
                            Timber.tag("TAG").d("length: ${it.selection.length}")
                            Timber.tag("TAG").d("length+1: ${it.selection.length + 1}")
                            amount = it
                        }
                        Spacer(modifier = Modifier.size(12.dp))

                        TextField(
                            value = bankName,
                            onValueChange = {
                                bankName = it
                            },
                            label = {
                                Text(
                                    "نام بانک",
                                    style = TextStyle(
                                        fontSize = 14.sp,
                                        fontFamily = ariaFaNumFontFamily
                                    ),
                                    fontWeight = FontWeight.Bold
                                )
                            },
                            modifier = Modifier.fillMaxWidth(1F),
                            textStyle = TextStyle(
                                fontSize = 17.sp,
                                fontFamily = ariaFaNumFontFamily,
                                fontWeight = FontWeight.Bold
                            ),

                            )
                    }
                    Spacer(modifier = Modifier.size(12.dp))

                    Row(modifier = Modifier.fillMaxWidth()) {

                        TextField(
                            modifier = Modifier.fillMaxWidth(0.5F),
                            interactionSource = dateInteractionSource,
                            value = date,
                            onValueChange = {
                                date = it
                            },
                            readOnly = true,
                            label = {
                                Text(
                                    text = "تاریخ",
                                    style = TextStyle(
                                        fontSize = 17.sp,
                                        fontFamily = ariaFaNumFontFamily
                                    ),
                                    fontWeight = FontWeight.Bold
                                )
                            },
                            textStyle = TextStyle(
                                fontSize = 17.sp,
                                fontFamily = ariaFaNumFontFamily,
                                fontWeight = FontWeight.Bold
                            )
                        )

                        if (dateInteractionSource.collectIsPressedAsState().value) {
                            initialDateForDatePicker = date.split("/")
                            datePickerState.value = true

                        }

                        Spacer(modifier = Modifier.size(12.dp))

                        TextField(
                            value = time,
                            onValueChange = {
                                time = it
                            },
                            readOnly = true,
                            interactionSource = timeInteractionSource,
                            label = {
                                Text(
                                    "ساعت",
                                    style = TextStyle(
                                        fontSize = 17.sp,
                                        fontFamily = ariaFaNumFontFamily
                                    ),
                                    fontWeight = FontWeight.Bold
                                )
                            },
                            textStyle = TextStyle(
                                fontSize = 17.sp,
                                fontFamily = ariaFaNumFontFamily,
                                fontWeight = FontWeight.Bold
                            ),
                            modifier = Modifier.fillMaxWidth(),
                        )
                        if (timeInteractionSource.collectIsPressedAsState().value) {
                            timePickerState.value = true
                        }


                    }
                    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                        if (initialDateForDatePicker.isNotEmpty() && initialDateForDatePicker.size == 3)
                            JalaliDatePickerDialog(
                                openDialog = datePickerState,
                                initialDate = JalaliCalendar(
                                    initialDateForDatePicker[0].toInt(),
                                    initialDateForDatePicker[1].toInt(),
                                    initialDateForDatePicker[2].toInt()
                                ),
                                onSelectDay = { //it:JalaliCalendar
                                    Timber.tag("Date")
                                        .d("onSelect: ${it.day} ${it.monthString} ${it.year}")
                                },
                                onConfirm = {
                                    Timber.tag("Date")
                                        .d("onConfirm: ${it.day} ${it.monthString} ${it.year}")
                                    date = "${it.year}/${it.month}/${it.day}"
                                },
                                fontFamily = FontFamily(
                                    Font(R.font.aria_bold)
                                ),
                                fontSize = 17.sp,
                            )
                        else
                            JalaliDatePickerDialog(
                                openDialog = datePickerState,
                                onSelectDay = { //it:JalaliCalendar
                                    Timber.tag("Date")
                                        .d("onSelect: ${it.day} ${it.monthString} ${it.year}")
                                },
                                onConfirm = {
                                    Timber.tag("Date")
                                        .d("onConfirm: ${it.day} ${it.monthString} ${it.year}")
                                    date = "${it.year}/${it.month}/${it.day}"
                                },
                                fontFamily = FontFamily(
                                    Font(R.font.aria_bold)
                                ),
                                fontSize = 17.sp,
                            )
                    }

                    if (timePickerState.value)
                        MyTimePicker(onConfirm = {
                            timePickerState.value = false
                            Timber.tag("TAG")
                                .d("CreateNewDetail: hour: ${it.hour} minute ${it.minute} ")
                            time =
                                "${if (it.hour.toString().length == 1) "0${it.hour}" else it.hour}:${it.minute}"
                        }) {
                            timePickerState.value = false
                        }

                }
                SingleChoiceSegmentedButtonRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    segmentedButtonList.forEach { item ->
                        SegmentedButton(
                            selected = selectedTransactionType == item.key,
                            onClick = {
                                selectedTransactionType = item.key
                            },
                            enabled = true,
                            shape = RoundedCornerShape(10.dp),
                            icon = {
                                Icon(
                                    painter = painterResource(if (item.key == TransactionType.DEPOSIT) R.drawable.ic_income_32 else R.drawable.ic_expenses_32),
                                    contentDescription = "income",
                                    tint = if (item.key == TransactionType.DEPOSIT) Color.Green else Color.Red
                                )
                            }
                        ) {
                            Text(
                                text = item.value, style = TextStyle(
                                    fontSize = 15.sp,
                                    fontFamily = ariaFaNumFontFamily,
                                    fontWeight = FontWeight.Medium
                                )
                            )
                        }
                    }
                }
                Text(
                    text = "انتخاب حساب:", Modifier.padding(start = 12.dp),
                    fontFamily = ariaFaNumFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                )
                Surface(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .wrapContentWidth()
                        .height(45.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(LiteWhite)
                        .clickable(onClick = {
                            bankCardDropDownExpanded = true
                        })
                        .padding(horizontal = 8.dp)

                ) {
                    Row(
                        modifier = Modifier.background(LiteWhite),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(Icons.Rounded.ArrowDropDown, contentDescription = "")
                        Text(
                            text = bankAccountNumber.ifBlank { "انتخاب کنید" },
                            Modifier.padding(start = 12.dp),
                            fontFamily = ariaFaNumFontFamily,
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                        )
                        DropdownMenu(
                            expanded = bankCardDropDownExpanded,
                            scrollState = rememberScrollState(),
                            onDismissRequest = { bankCardDropDownExpanded = false }
                        ) {
                            menuItemData.forEach { bankCard ->
                                DropdownMenuItem(
                                    text = {
                                        Text(
                                            "${bankCard.bankName} / ${bankCard.bankAccountNumber}",
                                            fontFamily = ariaFaNumFontFamily,
                                            fontWeight = FontWeight.Medium,
                                            fontSize = 15.sp,
                                        )
                                    },
                                    onClick = {
                                        bankAccountNumber = bankCard.bankAccountNumber
                                        bankCardDropDownExpanded = false
                                    }
                                )
                            }
                        }

                    }
                }

                TextField(
                    modifier = Modifier
                        .padding(12.dp)
                        .fillMaxSize()
                        .height(200.dp),
                    textStyle = TextStyle(
                        fontSize = 15.sp,
                        fontFamily = ariaFaNumFontFamily,
                        fontWeight = FontWeight.Medium
                    ),
                    placeholder = {
                        Text(
                            fontFamily = ariaFaNumFontFamily,
                            fontWeight = FontWeight.Black,
                            text = "توضیحات تراکنشت رو اینجا وارد کن...",
                            fontSize = 18.sp
                        )
                    },
                    label = {
                        Text(
                            modifier = Modifier.padding(bottom = 16.dp),
                            fontFamily = ariaFaNumFontFamily,
                            fontWeight = FontWeight.SemiBold,
                            text = "توضیحات",
                            fontSize = 18.sp,
                        )
                    },
                    value = text,
                    onValueChange = {
                        text = it
                    })

                Spacer(Modifier.size(8.dp))

                Text(
                    text = "دسته بندی:", Modifier.padding(start = 12.dp),
                    fontFamily = ariaFaNumFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                )

                LazyRow(
                    Modifier
                        .fillMaxHeight(0.6F)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    contentPadding = PaddingValues(horizontal = 12.dp),
                ) {

                    items(localCategoryList, key = {
                        it.id
                    }) { item ->
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            onClick = {
                                localCategoryList = localCategoryList.map {
                                    if (it.id == item.id)
                                        it.copy(isChecked = !it.isChecked)
                                    else it
                                }
                            }) {
                            Card(
                                modifier = Modifier
                                    .wrapContentSize()

                            ) {

                                Row(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .padding(8.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Timber.tag("TAG").d("item isChecked -> ${item.isChecked}")
                                    if (item.isChecked) {
                                        Timber.tag("TAG").d("item isChecked -> ${item.isChecked}")

                                        Icon(
                                            modifier = Modifier.size(18.dp),
                                            imageVector = Icons.Default.Check,
                                            contentDescription = "selected",
                                            tint = DarkYellow
                                        )
                                    }
                                    Text(
                                        modifier = Modifier
                                            .fillMaxHeight()
                                            .padding(horizontal = 8.dp),
                                        fontFamily = ariaFaNumFontFamily,
                                        fontWeight = FontWeight.SemiBold,
                                        text = item.title,
                                        textAlign = TextAlign.Center,
                                        style = TextStyle(fontSize = 13.sp),
                                    )

                                }
                            }


                        }
                    }
                }


            }


            MyOutlinedButton(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
                    .align(Alignment.BottomCenter)
                    .alpha(if (state.isLoading) 0f else 1f),
                text = "ذخیره"
            ) {
                state.smsModel?.let { sms ->

                    onAction.invoke(
                        TransactionDetailScreenActions.SaveTransaction(
                            sms.copy(
                                transactionAmount = amount.text,
                                bankAccountNumber = bankAccountNumber,
                                transactionDate = date,
                                transactionTime = time,
                                bankName = bankName,
                                description = text,
                                categoryIds = state.categoryList.filter { it.isChecked }
                                    .map { it.id },
                                transactionType = selectedTransactionType,
                                isModified = true
                            )
                        )
                    )

                    navController?.navigateUp()
                }
            }

            BallPulseProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(0.dp, 32.dp)
                    .alpha(if (state.isLoading) 1f else 0f),
                color = if (isSystemInDarkTheme()) ColorTextGrayOnDarkTheme else ColorTextGrayOnLiteTheme,
                animationDuration = 800,
                animationDelay = 200,
                startDelay = 0,
                ballCount = 3,
                maxBallDiameter = 17.dp

            )

        }

    }

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddDetailScreenPreview() {
    TransactionDetail(smsId = 0L) {}
}
