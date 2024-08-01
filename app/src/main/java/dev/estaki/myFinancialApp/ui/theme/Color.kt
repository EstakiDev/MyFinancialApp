package dev.estaki.myFinancialApp.ui.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF715B5F)
val Pink40 = Color(0xFF7D5260)
val ColorGrayLite = Color(0xCCECECEC)
val ColorGrayLiteShimmer = Color(0x65ECECEC)
val ColorShimmerGrayMedium = Color(0xE4EDFFFF)

val ColorTextGrayOnDarkTheme = Color(0xEEEEEEEE)
val ColorTextGrayOnLiteTheme = Color(0xCC535353)
val ColorBorderWhite = Color(0x0AFFFFFF)


val ColorOfShimmer = Color(0x74C2C2C2)
val ColorOfCenterShimmer = Color(0xFFF0F0F0)

val GreenDark = Color(0xFF00861D)
val RedDark = Color(0xFFAA0014)
val ColorCardIncomeB = Color(0xCC54BB6A)
val ColorCardIncomeA = Color(0xCCABD6B4)
val ColorCardExpensesA = Color(0xCCDA9D9D)
val ColorCardExpensesB = Color(0xCCCF5856)
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
