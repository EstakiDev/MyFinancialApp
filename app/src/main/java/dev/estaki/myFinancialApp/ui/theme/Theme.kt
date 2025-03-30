package dev.estaki.myFinancialApp.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import dev.estaki.ui_utils.ui.theme.DarkGray
import dev.estaki.ui_utils.ui.theme.DarkYellow
import dev.estaki.ui_utils.ui.theme.Pink40
import dev.estaki.ui_utils.ui.theme.PurpleGrey40
import dev.estaki.ui_utils.ui.theme.TypographyDark
import dev.estaki.ui_utils.ui.theme.TypographyLite
import dev.estaki.ui_utils.ui.theme.White

private val DarkColorScheme = darkColorScheme(
    primary = DarkYellow,
    secondary = PurpleGrey40,
    tertiary = Pink40,
    primaryContainer = Pink40,
//     Other default colors to override
//    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = DarkGray,
    onSurface = Color.White,
    inverseSurface = DarkGray,
    inverseOnSurface = White

)

private val LightColorScheme = lightColorScheme(
    primary = DarkYellow,
    secondary = PurpleGrey40,
    tertiary = Pink40,
    primaryContainer = Pink40,
//     Other default colors to override
//    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = White,
    onSurface = DarkGray,
    inverseSurface = White,
    inverseOnSurface = DarkGray
)

@Composable
fun FinancialTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }


    MaterialTheme(
        colorScheme = colorScheme,
        typography = if (darkTheme) TypographyDark else TypographyLite,
        content = content
    )
}