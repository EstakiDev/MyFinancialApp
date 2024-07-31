package dev.estaki.myfinancialapp.presentation

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.estaki.data.entities.SmsEntity
import dev.estaki.domain.models.TransactionType
import dev.estaki.myapplication.R
import dev.estaki.myfinancialapp.ui.theme.ColorCardExpenses
import dev.estaki.myfinancialapp.ui.theme.ColorCardIncome
import dev.estaki.myfinancialapp.ui.theme.ColorGrayLite
import dev.estaki.myfinancialapp.ui.theme.GreenDark
import dev.estaki.myfinancialapp.ui.theme.RedDark


@Composable
fun MyCardItem(smsEntity: dev.estaki.data.entities.SmsRawModel?, position: Int = 0) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Card(
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 4.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(8.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .fillMaxWidth()
                    .wrapContentHeight(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .padding(16.dp)
                        .background(color = ColorGrayLite, shape = CircleShape)
                ) {
                    Image(
                        painter = painterResource(id = if (smsEntity?.description!!.contains("واریز")) R.drawable.ic_income_32 else R.drawable.ic_expenses_32),
                        modifier = Modifier
                            .size(42.dp)
                            .scale(0.55F),
                        contentDescription = "Income and Expenses icon",
                    )
                }

                Column(modifier = Modifier.padding(horizontal = 12.dp)) {
                    val title = smsEntity?.description!!.split("\n")[0]
                    Text(
                        text = smsEntity.senderName,
                        fontWeight = FontWeight.Black,
                        fontSize = 16.sp
                    )

                    Text(text = title, fontWeight = FontWeight.Black, fontSize = 15.sp)
                    Row {
                        Text(text = "حساب: ", fontWeight = FontWeight.Normal, fontSize = 13.sp)
                        Text(
                            text = "123321456654789987",
                            fontWeight = FontWeight.Normal,
                            fontSize = 13.sp,
                        )
                    }
                    Row {
                        Text(
                            text = "نوع عملیات: ",
                            fontWeight = FontWeight.Normal,
                            fontSize = 13.sp
                        )
                        Text(
                            text = if (smsEntity.description.contains("واریز")) "واریز " else "برداشت ",
                            fontWeight = FontWeight.Normal,
                            fontSize = 13.sp
                        )

                    }
                    Row {
                        Text(
                            text = "مبلغ: ",
                            fontWeight = FontWeight.Normal,
                            fontSize = 13.sp
                        )
                        Text(text = "1,525,000", fontWeight = FontWeight.Normal, fontSize = 13.sp)
                        Text(text = " تومان", fontWeight = FontWeight.Normal, fontSize = 13.sp)
                    }


                }
            }

        }
    }

}

@Composable
fun MyCardItemNew(smsEntity: dev.estaki.data.entities.SmsEntity, onCardClick: () -> Unit) {

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Card(
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 4.dp)
                .fillMaxWidth()
                .wrapContentHeight(),
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            onClick = {onCardClick()}

        ) {
            Box(
                modifier = Modifier
                    .background(brush = if (smsEntity.transactionType == TransactionType.DEPOSIT) ColorCardIncome else ColorCardExpenses),
                contentAlignment = Alignment.BottomEnd
            ) {
                Row(
                    modifier = Modifier
                        .padding(vertical = 26.dp, horizontal = 12.dp)
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween

                ) {
                    IconWithCircleBackground(resId = if (smsEntity.transactionType == TransactionType.DEPOSIT) R.drawable.ic_income_32 else R.drawable.ic_expenses_32)

                    Column(modifier = Modifier.padding(horizontal = 12.dp)) {
                        Text(
                            text = smsEntity.bankName,
                            fontWeight = FontWeight.Black,
                            fontSize = 14.sp
                        )
                        Text(
                            text = if (smsEntity.transactionType == TransactionType.WITHDRAW) "خرج" else "دخل",
                            fontWeight = FontWeight.Black,
                            fontSize = 13.sp,
                            lineHeight = 26.sp,
                            color = if (smsEntity.transactionType == TransactionType.WITHDRAW) RedDark else GreenDark
                        )
                        Text(
                            text = if (!smsEntity.transactionAmount.contains("ریال")) smsEntity.transactionAmount.plus(" ریال ") else smsEntity.transactionAmount,
                            fontWeight = FontWeight.Medium,
                            fontSize = 13.sp,
                            lineHeight = 26.sp
                        )
                    }

                    IconWithCircleBackground(resId = R.drawable.baseline_directions_car_24)

                }
                Row {
                    Text(
                        text = smsEntity.transactionTime,
                        fontWeight = FontWeight.Bold,
                        fontSize = 9.sp,
                        lineHeight = 26.sp,
                    )
                    Text(
                        text = smsEntity.transactionDate,
                        fontWeight = FontWeight.Bold,
                        fontSize = 9.sp,
                        lineHeight = 26.sp,
                        modifier = Modifier.padding(end = 12.dp, start = 4.dp)
                    )
                }

            }


        }
    }

}
@Composable
fun IconWithCircleBackground(resId:Int){
    Box(
        modifier = Modifier
            .padding(16.dp)
            .background(color = ColorGrayLite, shape = CircleShape)
    ) {
        Image(
            painter = painterResource(id = resId ),
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
        MyCardItemNew(
            SmsEntity(
                id = 1L,
                "تجارت",
                "12558484.247",
                TransactionType.DEPOSIT,
                "123,152,125",
                "21/66/99",
                "22:10",
                "123,153,155",
                0L

                ),
            onCardClick = {}
        )
    }
}