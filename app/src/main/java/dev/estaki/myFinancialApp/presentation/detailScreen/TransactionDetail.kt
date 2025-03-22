package dev.estaki.myFinancialApp.presentation.detailScreen

import android.util.Log
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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
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
import dev.estaki.domain.models.SmsModel
import dev.estaki.myFinancialApp.presentation.main.MyCardItem
import dev.estaki.myFinancialApp.presentation.states.MyTopAppBarState
import dev.estaki.myFinancialApp.presentation.timepicker.MyTimePicker
import dev.estaki.ui_utils.components.AmountTextField
import dev.estaki.ui_utils.components.MyOutlinedButton
import dev.estaki.ui_utils.ui.theme.ColorTextGrayOnDarkTheme
import dev.estaki.ui_utils.ui.theme.ColorTextGrayOnLiteTheme
import dev.estaki.ui_utils.ui.theme.DarkYellow
import dev.estaki.ui_utils.ui.theme.ariaFaNumFontFamily
import ir.huri.jcal.JalaliCalendar


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddDetailScreen(
    detailScreenViewModel: DetailScreenViewModel = hiltViewModel(),
    navController: NavHostController? = null,
    smsId: Long? = null,
    onComposing: (MyTopAppBarState) -> Unit
) {
    LaunchedEffect(true) {
        onComposing(
            MyTopAppBarState(
                title = "ویرایش اطلاعات تراکنش",
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
    }
    smsId?.let {
        detailScreenViewModel.loadSmsById(it)
    }
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {

        val scrollState = rememberScrollState()


        Log.d("TAG", "categoryList remember start ")

        Log.d("TAG", "categoryList remember finish ")

        val loadingState by detailScreenViewModel.isLoading.collectAsState()
        val smsModel by detailScreenViewModel.sms.collectAsState()
        val cat by detailScreenViewModel.categoryList.collectAsState()
        var categoryList by rememberSaveable(cat) { mutableStateOf(cat) }
        var text by rememberSaveable {
            mutableStateOf("")
        }
        LaunchedEffect(key1 = cat) {
            smsModel?.let { smsM ->
                if (smsM.categoryIds.isNotEmpty())
                    categoryList = categoryList.map {
                        if (smsM.categoryIds.contains(it.id))
                            it.copy(isChecked = true)
                        else
                            it
                    }
            }
        }
        val context = LocalContext.current
        var amount by remember {
            mutableStateOf(
                TextFieldValue(
                    smsModel?.transactionAmount ?: "",
                    selection = TextRange(smsModel?.transactionAmount?.length ?: 0)
                )
            )
        }
        var bankName by remember { mutableStateOf(smsModel?.bankName ?: "") }
        var time by remember { mutableStateOf(smsModel?.transactionTime ?: "") }
        var date by remember { mutableStateOf(smsModel?.transactionDate ?: "") }
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
        val newSmsModel = smsModel?.copy(
            transactionAmount = amount.text,
            bankName = bankName,
            transactionTime = time,
            transactionDate = date,

            ) ?: SmsModel(
            null,
            "",
            "",
            dev.estaki.domain.models.TransactionType.DEPOSIT,
            "",
            "",
            0L,
            "",
            "",
            listOf(),
            null,
            "",
            ""
        )

        LaunchedEffect(key1 = smsModel) {
            smsModel?.let {
                amount = TextFieldValue(it.transactionAmount)
                bankName = it.bankName
                time = it.transactionTime
                date = it.transactionDate
                text = it.description ?: ""
            }

        }

        Box(
            Modifier.fillMaxSize(),
        ) {
            Column(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .alpha(if (loadingState) 0f else 1f)
            ) {
                Spacer(Modifier.size(8.dp))

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp)
                ) {
                    smsModel?.let {
                        MyCardItem(smsModel = newSmsModel) { }
                    }
                    Spacer(modifier = Modifier.size(12.dp))
                    Row(modifier = Modifier.fillMaxWidth()) {
                        AmountTextField(
                            amount = amount,
                            modifier = Modifier.fillMaxWidth(0.5F),
                            unit = "ريال"
                        ) {
                            Log.d("TAG", "length: ${it.selection.length}")
                            Log.d("TAG", "length+1: ${it.selection.length + 1}")
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
                        if (initialDateForDatePicker.isNotEmpty())
                            JalaliDatePickerDialog(
                                openDialog = datePickerState,
                                initialDate = JalaliCalendar(
                                    initialDateForDatePicker[0].toInt(),
                                    initialDateForDatePicker[1].toInt(),
                                    initialDateForDatePicker[2].toInt()
                                ),
                                onSelectDay = { //it:JalaliCalendar
                                    Log.d(
                                        "Date",
                                        "onSelect: ${it.day} ${it.monthString} ${it.year}"
                                    )
                                },
                                onConfirm = {
                                    Log.d(
                                        "Date",
                                        "onConfirm: ${it.day} ${it.monthString} ${it.year}"
                                    )
                                    date = "${it.year}/${it.month}/${it.day}"
                                },
                                fontFamily = FontFamily(
                                    Font(dev.estaki.ui_utils.R.font.aria_bold)
                                ),
                                fontSize = 17.sp,
                            )
                        else
                            JalaliDatePickerDialog(
                                openDialog = datePickerState,
                                onSelectDay = { //it:JalaliCalendar
                                    Log.d(
                                        "Date",
                                        "onSelect: ${it.day} ${it.monthString} ${it.year}"
                                    )
                                },
                                onConfirm = {
                                    Log.d(
                                        "Date",
                                        "onConfirm: ${it.day} ${it.monthString} ${it.year}"
                                    )
                                    date = "${it.year}/${it.month}/${it.day}"
                                },
                                fontFamily = FontFamily(
                                    Font(dev.estaki.ui_utils.R.font.aria_bold)
                                ),
                                fontSize = 17.sp,
                            )
                    }

                    if (timePickerState.value)
                        MyTimePicker(onConfirm = {
                            timePickerState.value = false
                            Log.d(
                                "TAG",
                                "CreateNewDetail: hour: ${it.hour} minute ${it.minute} "
                            )

                        }) {
                            timePickerState.value = false
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
                Log.d("TAG", "check categoryList size")
                Log.d("TAG", "check $categoryList")

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

                    items(categoryList, key = {
                        it.id
                    }) { item ->
                        Surface(
                            shape = RoundedCornerShape(10.dp),
                            onClick = {
                                Log.d("TAG", "check before clickable $categoryList.value ")
                                categoryList = categoryList.map {
                                    if (it.id == item.id)
                                        it.copy(isChecked = !it.isChecked)
                                    else it
                                }
                                Log.d("TAG", "check after clickable $categoryList")
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
                                    Log.d("TAG", "item isChecked -> ${item.isChecked}")
                                    if (item.isChecked) {
                                        Log.d("TAG", "item isChecked -> ${item.isChecked}")

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
                    .alpha(if (loadingState) 0f else 1f),
                text = "ذخیره"
            ) {
                smsModel?.let { sms ->
                    detailScreenViewModel.saveSms(
                        sms.copy(
                            transactionAmount = amount.text,
                            transactionDate = date,
                            transactionTime = time,
                            bankName = bankName,
                            description = text,
                            categoryIds = categoryList.filter { it.isChecked }
                                .map { it.id }
                        )
                    )
                    navController?.navigateUp()
                }
            }

            BallPulseProgressIndicator(
                modifier = Modifier
                    .align(Alignment.Center)
                    .padding(0.dp, 32.dp)
                    .alpha(if (loadingState) 1f else 0f),
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionDetail(modifier: Modifier = Modifier, sms: SmsModel?) {

}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun AddDetailScreenPreview() {
    AddDetailScreen(){}
}