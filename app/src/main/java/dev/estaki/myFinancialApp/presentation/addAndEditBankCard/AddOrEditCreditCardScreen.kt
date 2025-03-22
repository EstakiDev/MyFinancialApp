package dev.estaki.myFinancialApp.presentation.addAndEditBankCard

import android.util.Log
import android.widget.Space
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.rounded.ArrowBack
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.Paragraph
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.ehsanmsz.mszprogressindicator.progressindicator.BallPulseProgressIndicator
import dev.estaki.domain.models.BankCardModel
import dev.estaki.kt_pure_utils.formatCardNumber
import dev.estaki.kt_pure_utils.resetErr
import dev.estaki.myFinancialApp.presentation.actions.AddCreditCardActions
import dev.estaki.myFinancialApp.presentation.states.AddAndEditBankAccountScreenState
import dev.estaki.myFinancialApp.ui.theme.FinancialTheme
import dev.estaki.ui_utils.components.CreditCard
import dev.estaki.ui_utils.components.MyAlertDialog
import dev.estaki.ui_utils.components.MyOutlinedButton
import dev.estaki.ui_utils.ui.theme.ColorTextGrayOnDarkTheme
import dev.estaki.ui_utils.ui.theme.ColorTextGrayOnLiteTheme
import dev.estaki.ui_utils.ui.theme.RedDark
import dev.estaki.ui_utils.ui.theme.ariaFaNumFontFamily
import kotlin.math.sin

@Composable
fun AddOrEditCreditCardScreen(
    modifier: Modifier = Modifier,
    viewModel: AddCreditCardViewModel = hiltViewModel(),
    creditAccountNumber: String,
    position: Int,
    navController: NavHostController
) {
    Log.d("TAG", "AddOrEditCreditCardScreen: ")
    val state by viewModel.cardState.collectAsState()
    AddOrEditCreditCard(
        modifier = modifier
            .height(225.dp),
        onAction = viewModel::onAction,
        state = state,
        navController = navController,
        position = position,
        creditAccountNumber = creditAccountNumber
    )

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddOrEditCreditCard(
    modifier: Modifier = Modifier,
    state: AddAndEditBankAccountScreenState,
    creditAccountNumber: String,
    position: Int,
    navController: NavHostController,
    onAction: (AddCreditCardActions) -> Unit
) {
    LaunchedEffect(false) {
        onAction.invoke(AddCreditCardActions.LoadCard(bankAccountNumber = creditAccountNumber))
    }
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    var bankName by remember { mutableStateOf("") }
    var bankAccountNumber by remember { mutableStateOf("") }
    var cardNumber by remember { mutableStateOf(TextFieldValue()) }

    var bankNameErr by remember { mutableStateOf<Pair<Boolean, String>>(Pair(false, "")) }
    var bankAccountNumberErr by remember { mutableStateOf<Pair<Boolean, String>>(Pair(false, "")) }
    var cardNumberErr by remember { mutableStateOf<Pair<Boolean, String>>(Pair(false, "")) }

    var showWarningDialog by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(state.bankCardModel) {
        state.bankCardModel?.apply {
            bankName = this.bankName
            bankAccountNumber = this.bankAccountNumber
            cardNumber = TextFieldValue(this.bankCardNumber ?: "")
        }
    }
    FinancialTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "ویرایش جزئیات کارت",
                            fontFamily = ariaFaNumFontFamily,
                            fontWeight = FontWeight.Black,
                            fontSize = 14.sp,
                            modifier = Modifier.padding(12.dp)
                        )
                    },

                    actions = {
                        if (state.bankCardModel != null) {
                            IconButton(onClick = {
                                showWarningDialog = true
                            }) {
                                Icon(
                                    imageVector = Icons.Rounded.Delete,
                                    contentDescription = "delete"
                                )
                            }
                        }
                    },
                    navigationIcon = {
                        IconButton(onClick = {
                            navController?.navigateUp()
                        }) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Rounded.ArrowBack,
                                contentDescription = "back"
                            )
                        }
                    },
                    scrollBehavior = scrollBehavior

                )
            }
        ) { padding ->
            if (showWarningDialog) {
                MyAlertDialog(
                    onDismissRequest = {
                        showWarningDialog =false
                    },
                    onConfirmation = {
                        state.bankCardModel?.let {
                            onAction.invoke(AddCreditCardActions.DeleteCard(it))
                        }
                        showWarningDialog =false
                        navController.navigateUp()
                    },
                    "هشدار",
                    "از حذف کردن این کارت بانکی اطمینان دارید؟",
                    icon = Icons.Rounded.Warning
                )
            }

            if (state.isLoading) {
                Box(
                    modifier
                        .fillMaxSize(1F)
                        .padding(padding)
                ) {
                    BallPulseProgressIndicator(
                        modifier = Modifier
                            .align(Alignment.Center),
                        color = if (isSystemInDarkTheme()) ColorTextGrayOnDarkTheme else ColorTextGrayOnLiteTheme,
                        animationDuration = 800,
                        animationDelay = 200,
                        startDelay = 0,
                        ballCount = 3,
                        maxBallDiameter = 13.dp
                    )
                }

            } else {

                Column(
                    Modifier
                        .padding(
                            top = padding.calculateTopPadding(),
                            bottom = padding.calculateBottomPadding(),
                            start = 8.dp,
                            end = 8.dp
                        ),
                ) {
                    CreditCard(
                        modifier = Modifier,
                        bankName = bankName,
                        bankAccountNumber = bankAccountNumber,
                        bankCardNumber = cardNumber.text,
                        bankCardBalance = state.bankCardModel?.bankCardBalance ?: "",
                        position = position
                    ) {}



                    TextField(
                        value = bankName ?: "",
                        onValueChange = {
                            bankName = it
                        },
                        singleLine = true,
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
                        isError = bankNameErr.first,
                        supportingText = {
                            Text(text = bankNameErr.second, color = RedDark)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        textStyle = TextStyle(
                            fontSize = 17.sp,
                            fontFamily = ariaFaNumFontFamily,
                            fontWeight = FontWeight.Bold
                        ),

                        )
                    TextField(
                        value = bankAccountNumber,
                        onValueChange = {
                            bankAccountNumber = it
                        },
                        readOnly = state.bankCardModel != null,
                        singleLine = true,
                        label = {
                            Text(
                                "شماره حساب",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontFamily = ariaFaNumFontFamily
                                ),
                                fontWeight = FontWeight.Bold
                            )
                        },
                        isError = bankAccountNumberErr.first,
                        supportingText = {
                            Text(text = bankAccountNumberErr.second, color = RedDark)
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        textStyle = TextStyle(
                            fontSize = 17.sp,
                            fontFamily = ariaFaNumFontFamily,
                            fontWeight = FontWeight.Bold,
                            textDirection = TextDirection.Ltr
                        ),

                        )
                    TextField(
                        value = cardNumber,
                        onValueChange = {

                            try {
                                val number = it.text.formatCardNumber()
                                val newValue = it.copy(
                                    text = number,
                                    selection = TextRange(index = number.length)
                                )
                                cardNumber = newValue
                            } catch (e: Exception) {

                            }

                        },
                        isError = cardNumberErr.first,
                        supportingText = {
                            Text(text = cardNumberErr.second, color = RedDark)
                        },
                        label = {
                            Text(
                                "شماره کارت",
                                style = TextStyle(
                                    fontSize = 14.sp,
                                    fontFamily = ariaFaNumFontFamily
                                ),
                                fontWeight = FontWeight.Bold
                            )
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        textStyle = TextStyle(
                            fontSize = 17.sp,
                            fontFamily = ariaFaNumFontFamily,
                            fontWeight = FontWeight.Bold,
                            textDirection = TextDirection.Ltr
                        ),

                        )
                    MyOutlinedButton(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(12.dp),
                        text = "ذخیره"
                    ) {
                        bankNameErr = resetErr()
                        cardNumberErr = resetErr()
                        bankAccountNumberErr = resetErr()

                        if (bankName.isBlank()) {
                            bankNameErr = Pair(true, "نام بانک نمیتواند خالی باشد")
                        }
                        if (cardNumber.text.isBlank()) {
                            cardNumberErr = Pair(true, "شماره کارت نمیتواند خالی باشد")
                        }
                        if (bankAccountNumber.isBlank()) {
                            bankAccountNumberErr = Pair(true, "نام بانک نمیتواند خالی باشد")
                        }
                        if (bankNameErr.first || cardNumberErr.first || bankAccountNumberErr.first) {

                        } else {
                            onAction.invoke(
                                AddCreditCardActions.SaveCard(
                                    state.bankCardModel?.copy(
                                        bankName = bankName,
                                        bankCardNumber = cardNumber.text,
                                    ) ?: BankCardModel(
                                        0,
                                        bankName,
                                        bankAccountNumber,
                                        "0",
                                        cardNumber.text
                                    )
                                )
                            )
                            navController.navigateUp()
                        }

                    }
                }


            }

        }
    }

}



