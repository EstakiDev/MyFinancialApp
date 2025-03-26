package dev.estaki.myFinancialApp.presentation.main

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineBreak
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.estaki.domain.models.SmsModel
import dev.estaki.domain.models.TransactionType
import dev.estaki.ui_utils.R
import dev.estaki.ui_utils.components.IconWithCircleBackground
import dev.estaki.ui_utils.ui.theme.ColorCardExpenses
import dev.estaki.ui_utils.ui.theme.ColorCardIncome
import dev.estaki.ui_utils.ui.theme.GreenDark
import dev.estaki.ui_utils.ui.theme.RedDark
import dev.estaki.ui_utils.ui.theme.ariaFaNumFontFamily


@Composable
fun MyCardItem(smsModel: SmsModel, onCardClick: () -> Unit) {

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            onClick = { onCardClick() }

        ) {
            Box(
                modifier = Modifier
                    .background(brush = if (smsModel.transactionType == TransactionType.DEPOSIT) ColorCardIncome else ColorCardExpenses),
            ) {
                Row(
                    modifier = Modifier
                        .padding(vertical = 6.dp, horizontal = 6.dp)
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween

                ) {
                    IconWithCircleBackground(
                        resId = if (smsModel.transactionType == TransactionType.DEPOSIT) R.drawable.ic_income_32 else R.drawable.ic_expenses_32,
                        visibilityState = true
                    )

                    Column(
                        modifier = Modifier
                            .padding(horizontal = 12.dp)
                            .wrapContentSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start
                    ) {
                        Text(
                            modifier = Modifier.wrapContentSize(),
                            text = smsModel.bankName,
                            fontWeight = FontWeight.Black,
                            fontSize = 14.sp,


                            )
                        Row(
                            modifier = Modifier.wrapContentSize(),
                            verticalAlignment = Alignment.CenterVertically,

                            ) {
                            Text(
                                modifier = Modifier.wrapContentSize(),
                                text = if (!smsModel.transactionAmount.contains("ریال")) smsModel.transactionAmount.plus(
                                    " ریال "
                                ) else smsModel.transactionAmount,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 13.sp,
                                lineHeight = 26.sp,
                                overflow = TextOverflow.Visible,
                                style = TextStyle(
                                    lineBreak = LineBreak.Paragraph,
                                    fontFamily = ariaFaNumFontFamily,
                                    lineHeight = 8.sp
                                )
                            )
                            Spacer(modifier = Modifier.fillMaxWidth(0.05F))
                            Text(
                                modifier = Modifier.wrapContentSize(),
                                text = if (smsModel.transactionType == TransactionType.WITHDRAW) "خرج" else "دخل",
                                fontWeight = FontWeight.Black,
                                fontSize = 13.sp,
                                lineHeight = 26.sp,
                                color = if (smsModel.transactionType == TransactionType.WITHDRAW) RedDark else GreenDark
                            )
                        }


                        Row(Modifier.wrapContentSize()) {
                            Text(
                                text = smsModel.transactionTime,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                lineHeight = 26.sp,
                            )
                            Text(
                                text = smsModel.transactionDate,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                lineHeight = 26.sp,
                                modifier = Modifier.padding(end = 12.dp, start = 4.dp)
                            )
                        }
                    }

                    IconWithCircleBackground(
                        resId = R.drawable.baseline_directions_car_24,
                        visibilityState = smsModel.categoryIds.isNotEmpty()
                    )

                }


            }


        }
    }

}


@Preview
@Composable
fun MyCardItemPreview() {
    Column {
        MyCardItem(
            SmsModel(
                id = 1L,
                "تجارت",
                "12558484.247",
                TransactionType.DEPOSIT,
                "123,152,125",
                "21/66/99",
                transactionDateTime = 0L,
                "22:10",
                "123,153,155",
                listOf(0L),
                description = null,
                smsBody = "",
                smsSender = ""
            ),
            onCardClick = {}
        )
    }
}