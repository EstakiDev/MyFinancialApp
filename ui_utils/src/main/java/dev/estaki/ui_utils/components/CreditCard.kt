package dev.estaki.ui_utils.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.estaki.domain.models.BankCardModel
import dev.estaki.ui_utils.R
import dev.estaki.ui_utils.ui.theme.CreditColors
import dev.estaki.ui_utils.ui.theme.LiteWhite

@Composable
fun CreditCard(
    modifier: Modifier = Modifier
        .wrapContentSize(),
    item: BankCardModel?,
    position: Int = 0,
    onClicked: () -> Unit
) {
    val backgroundColor = if (position >= CreditColors.size) {
        CreditColors[1]
    } else
        CreditColors[position]
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        Card(
            modifier = modifier,
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(
                containerColor = backgroundColor
            ),
            onClick = {
                onClicked.invoke()
            }
        ) {
            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "کارت اعتباری",
                        color = Color(0xFFB7B39F),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = item?.bankName ?: "نام بانک",
                        color = Color(0xFFB7B39F),
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Black
                    )
                }
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.chip),
                        contentDescription = "",
                        modifier = modifier
                            .size(42.dp)

                    )
                    Text(
                        modifier = modifier.wrapContentSize(),
                        textAlign = TextAlign.Center,
                        text = item?.bankAccountNumber ?: "---",
                        fontSize = 16.sp,
                        color = Color(0xFFB7B39F),
                        fontFamily = FontFamily(fonts = listOf(Font(resId = R.font.ocr_a))),

                        )
                }

                Surface(
                    shape = RoundedCornerShape(topEnd = 30.dp, topStart = 30.dp),
                    color = LiteWhite,
                ) {
                    Column(
                        modifier = modifier.padding(15.dp),
                        verticalArrangement = Arrangement.Center,

                        ) {
                        Text(
                            modifier = modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            text = item?.bankCardNumber?.ifBlank { "**** **** **** ****" }?:"**** **** **** ****",
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily(fonts = listOf(Font(resId = R.font.ocr_a))),
                            fontSize = 17.sp,
                            color = Color(0xFFB7B39F), letterSpacing = 5.sp

                        )
                        Spacer(modifier.height(12.dp))
                        Row(
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {

                                Text(
                                    textAlign = TextAlign.Center,
                                    text = "${item?.bankCardBalance}",
                                    fontSize = 13.sp,
                                    fontFamily = FontFamily(fonts = listOf(Font(resId = R.font.ocr_a))),
                                    color = Color(0xFFB7B39F)

                                )

                            Text(
                                textAlign = TextAlign.Center,
                                text = "**/**",
                                fontSize = 15.sp,
                                fontFamily = FontFamily(fonts = listOf(Font(resId = R.font.ocr_a))),
                                color = Color(0xFFB7B39F)

                            )

                        }

                    }

                }
            }
        }
    }


}

@Preview
@Composable
fun CreditCardPreview() {
    CreditCard(modifier = Modifier, null) {}
}

@Composable
fun CreditCard(
    modifier: Modifier = Modifier
        .wrapContentSize(),
    bankName: String,
    bankAccountNumber: String,
    bankCardNumber: String,
    bankCardBalance: String,
    position: Int = 0,
    onClicked: () -> Unit
) {
    val backgroundColor = if (position >= CreditColors.size) {
        CreditColors[1]
    } else
        CreditColors[position]
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        Card(
            modifier = modifier,
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(
                containerColor = backgroundColor
            ),
            onClick = {
                onClicked.invoke()
            }
        ) {
            Column(
                modifier = modifier,
                verticalArrangement = Arrangement.SpaceBetween
            ) {

                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = "کارت اعتباری",
                        color = Color(0xFFB7B39F),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = bankName ?: "نام بانک",
                        color = Color(0xFFB7B39F),
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Black
                    )
                }
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.chip),
                        contentDescription = "",
                        modifier = modifier
                            .size(42.dp)

                    )
                    Text(
                        modifier = modifier.wrapContentSize(),
                        textAlign = TextAlign.Center,
                        text = bankAccountNumber ?: "---",
                        fontSize = 16.sp,
                        color = Color(0xFFB7B39F),
                        fontFamily = FontFamily(fonts = listOf(Font(resId = R.font.ocr_a))),

                        )
                }

                Surface(
                    shape = RoundedCornerShape(topEnd = 30.dp, topStart = 30.dp),
                    color = LiteWhite,
                ) {
                    Column(
                        modifier = modifier.padding(15.dp),
                        verticalArrangement = Arrangement.Center,

                        ) {
                        Text(
                            modifier = modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center,
                            text = bankCardNumber.ifBlank { "**** **** **** ****" }?:"**** **** **** ****",
                            fontWeight = FontWeight.Bold,
                            fontFamily = FontFamily(fonts = listOf(Font(resId = R.font.ocr_a))),
                            fontSize = 17.sp,
                            color = Color(0xFFB7B39F), letterSpacing = 5.sp

                        )
                        Spacer(modifier.height(12.dp))
                        Row(
                            modifier = modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
//                        Text(
//                            textAlign = TextAlign.Center,
//                            text = "نقی معمولی",
//                            fontSize = 11.sp,
//                            fontFamily = FontFamily(fonts = listOf(Font(resId = R.font.far_bank))),
//                            color = Color(0xFFB7B39F)
//
//                        )
                            Row {
                                Text(
                                    textAlign = TextAlign.Center,
                                    text = " موجودی: ",
                                    fontSize = 13.sp,
                                    fontFamily = FontFamily(fonts = listOf(Font(resId = R.font.far_bank))),
                                    color = Color(0xFFB7B39F)

                                )
                                Text(
                                    textAlign = TextAlign.Center,
                                    text = bankCardBalance?:"",
                                    fontSize = 13.sp,
                                    fontFamily = FontFamily(fonts = listOf(Font(resId = R.font.ocr_a))),
                                    color = Color(0xFFB7B39F)

                                )
                            }

                            Text(
                                textAlign = TextAlign.Center,
                                text = "**/**",
                                fontSize = 15.sp,
                                fontFamily = FontFamily(fonts = listOf(Font(resId = R.font.ocr_a))),
                                color = Color(0xFFB7B39F)

                            )

                        }

                    }

                }
            }
        }
    }


}