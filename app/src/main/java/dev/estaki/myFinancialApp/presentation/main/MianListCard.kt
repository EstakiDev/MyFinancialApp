package dev.estaki.myFinancialApp.presentation.main

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import dev.estaki.domain.models.SmsModel
import dev.estaki.domain.models.TransactionType
import dev.estaki.myFinancialApp.R
import dev.estaki.myFinancialApp.ui.theme.ColorCardExpenses
import dev.estaki.myFinancialApp.ui.theme.ColorCardIncome
import dev.estaki.myFinancialApp.ui.theme.ColorGrayLite
import dev.estaki.myFinancialApp.ui.theme.GreenDark
import dev.estaki.myFinancialApp.ui.theme.RedDark



@Composable
fun MyCardItem(smsEntity: SmsModel, onCardClick: () -> Unit) {

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Card(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 0.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            onClick = { onCardClick() }

        ) {
            Box(
                modifier = Modifier
                    .background(brush = if (smsEntity.transactionType == TransactionType.DEPOSIT) ColorCardIncome else ColorCardExpenses),
                contentAlignment = Alignment.BottomEnd
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
                        resId = if (smsEntity.transactionType == TransactionType.DEPOSIT) R.drawable.ic_income_32 else R.drawable.ic_expenses_32,
                        visibilityState = true
                    )

                    Column(modifier = Modifier.padding(horizontal = 12.dp),
                        verticalArrangement = Arrangement.Center,) {
                        Text(
                            text = smsEntity.bankName,
                            fontWeight = FontWeight.Black,
                            fontSize = 14.sp,


                            )
                        Row(
                            modifier = Modifier.height(32.dp),
                            verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = if (!smsEntity.transactionAmount.contains("ریال")) smsEntity.transactionAmount.plus(
                                    " ریال "
                                ) else smsEntity.transactionAmount,
                                fontWeight = FontWeight.SemiBold,
                                fontSize = 13.sp,
                                lineHeight = 26.sp
                            )
                            Spacer(modifier = Modifier.size(8.dp))
                            Text(
                                text = if (smsEntity.transactionType == TransactionType.WITHDRAW) "خرج" else "دخل",
                                fontWeight = FontWeight.Black,
                                fontSize = 13.sp,
                                lineHeight = 26.sp,
                                color = if (smsEntity.transactionType == TransactionType.WITHDRAW) RedDark else GreenDark
                            )
                        }


                        Row {
                            Text(
                                text = smsEntity.transactionTime,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                lineHeight = 26.sp,
                            )
                            Text(
                                text = smsEntity.transactionDate,
                                fontWeight = FontWeight.Bold,
                                fontSize = 13.sp,
                                lineHeight = 26.sp,
                                modifier = Modifier.padding(end = 12.dp, start = 4.dp)
                            )
                        }
                    }

                    IconWithCircleBackground(
                        resId = R.drawable.baseline_directions_car_24,
                        visibilityState = smsEntity.categoryIds.isNotEmpty()
                    )

                }


            }


        }
    }

}

@Composable
fun IconWithCircleBackground(resId: Int, visibilityState: Boolean = false) {
    Box(
        modifier = Modifier
            .alpha(if (visibilityState) 1F else 0F)
            .padding(16.dp)
            .background(color = ColorGrayLite, shape = CircleShape)
    ) {
        Image(
            painter = painterResource(id = resId),
            modifier = Modifier
                .size(42.dp)
                .scale(0.55F),
            contentDescription = "Income and Expenses icon",
        )

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
                "22:10",
                "123,153,155",
                listOf(0L),
                description = null
            ),
            onCardClick = {}
        )
    }
}