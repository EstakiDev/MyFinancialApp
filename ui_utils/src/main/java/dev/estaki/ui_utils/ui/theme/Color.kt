package dev.estaki.ui_utils.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val CreditColors = listOf<Color>(
    Color(0xFFA1630C),
    Color(0xFF673AB7),
    Color(0xFF2A866D),
    Color(0xFFA20F4F),
    Color(0xFF867151),
    Color(0xFF427218),
    Color(0xFF3A488D),
    Color(0xFF3E7491),
    Color(0xFF3C4F1E),
    Color(0xFFB03A13),
    Color(0xFF7A2828),
    Color(0xFF0B4B9A),
)



val DarkYellow = Color(0xFFDA8300)
val White = Color(0xFFFFFFFF)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF715B5F)
val Pink40 = Color(0xFF7D5260)
val ColorGrayLite = Color(0xCCECECEC)
val ColorGrayDark = Color(0xCCA4A4A4)
val ColorGrayLiteShimmer = Color(0x65ECECEC)
val ColorShimmerGrayMedium = Color(0xE4EDFFFF)

val ColorTextGrayOnDarkTheme = Color(0xEEEEEEEE)
val ColorTextGrayOnLiteTheme = Color(0xCC535353)
val ColorBorderWhite = Color(0x0AFFFFFF)


val ColorOfShimmer = Color(0x74C2C2C2)
val ColorOfCenterShimmer = Color(0xFFF0F0F0)

val GreenDark = Color(0xFF00861D)
val RedDark = Color(0xFFAA0014)
val LiteWhite = Color(0x2AFFFFFF)
val ColorCardIncomeB = Color(0xCC54BB6A)
val ColorCardIncomeA = Color(0xCCABD6B4)
val ColorCardExpensesA = Color(0xCCDA9D9D)
val ColorCardExpensesB = Color(0xFFFF3F3C)
val BlueSky= Color(0xFF4478a9)
val NightSky =  Color(0xFF333333)
val BorderColor = Color(0x40000000)
val ColorCardIncome = Brush.horizontalGradient(
    colors = listOf(
        ColorCardIncomeB, ColorCardIncomeA
    )
)
val ColorCardExpenses = Brush.horizontalGradient(
    colors = listOf(
        ColorCardExpensesB, ColorCardExpensesA
    )
)
