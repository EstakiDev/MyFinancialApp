package dev.estaki.myFinancialApp.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import dev.estaki.myFinancialApp.R


// Set of Material typography styles to start with
val iranSansFontFamily = FontFamily(
    Font(R.font.iran_sans_mobile_ultra_light, FontWeight.ExtraLight),
    Font(R.font.iran_sans_mobile_light, FontWeight.Light),
    Font(R.font.iran_sans_mobile, FontWeight.Normal),
    Font(R.font.iran_sans_mobile_medium, FontWeight.Medium),
    Font(R.font.iran_sans_mobile_bold, FontWeight.Bold),
    Font(R.font.iran_sans_mobile_black, FontWeight.Black),
)

val iranSansFaNumFontFamily = FontFamily(
    Font(R.font.iran_sans_mobile_fa_num_ultra_light, FontWeight.ExtraLight),
    Font(R.font.iran_sans_mobile_fa_num_light, FontWeight.Light),
    Font(R.font.iran_sans_mobile_fa_num, FontWeight.Normal),
    Font(R.font.iran_sans_mobile_fa_num_medium, FontWeight.Medium),
    Font(R.font.iran_sans_mobile_fa_num_bold, FontWeight.Bold),
    Font(R.font.iran_sans_mobile_fa_num_black, FontWeight.Black),
)
val ariaFaNumFontFamily = FontFamily(
    Font(R.font.aria_thin, FontWeight.Thin),
    Font(R.font.aria_extra_light, FontWeight.ExtraLight),
    Font(R.font.aria_light, FontWeight.Light),
    Font(R.font.aria_normal, FontWeight.Normal),
    Font(R.font.aria_medium, FontWeight.Medium),
    Font(R.font.aria_bold, FontWeight.Bold),
    Font(R.font.aria_extra_bold, FontWeight.ExtraBold),
    Font(R.font.aria_black, FontWeight.Black),
    Font(R.font.aria_heavy, FontWeight.ExtraBold),
)

val coolakFaNumFontFamily = FontFamily(
    Font(R.font.colak_fa_num, FontWeight.Normal),
)

val TypographyDark = Typography(

    bodySmall = TextStyle(
        fontFamily = ariaFaNumFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 8.sp,
        letterSpacing = 1.sp,
        color = ColorTextGrayOnDarkTheme,
    ),
    bodyMedium = TextStyle(
        fontFamily = ariaFaNumFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 17.sp,
        lineHeight = 8.sp,
        letterSpacing = 1.sp,
        color = ColorTextGrayOnDarkTheme,
    ),
    bodyLarge = TextStyle(
        fontFamily = ariaFaNumFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 8.sp,
        letterSpacing = 1.sp,
        color = ColorTextGrayOnDarkTheme,
    ),
)

val TypographyLite = Typography(
    bodySmall =  TextStyle(
        fontFamily = ariaFaNumFontFamily,
        fontWeight = FontWeight.Medium,
        fontSize = 14.sp,
        lineHeight = 8.sp,
        letterSpacing = 1.sp,
        color = ColorTextGrayOnLiteTheme,
    ),
    bodyMedium = TextStyle(
        fontFamily = ariaFaNumFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 17.sp,
        lineHeight = 8.sp,
        letterSpacing = 1.sp,
        color = ColorTextGrayOnLiteTheme,
    ),
    bodyLarge = TextStyle(
        fontFamily = ariaFaNumFontFamily,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
        lineHeight = 8.sp,
        letterSpacing = 1.sp,
        color = ColorTextGrayOnLiteTheme,
    ),
)

/* Other default text styles to override
titleLarge = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Normal,
    fontSize = 22.sp,
    lineHeight = 28.sp,
    letterSpacing = 0.sp
),
labelSmall = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Medium,
    fontSize = 11.sp,
    lineHeight = 16.sp,
    letterSpacing = 0.5.sp
)
*/
