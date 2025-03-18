package dev.estaki.ui_utils.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import dev.estaki.ui_utils.ui.theme.ColorGrayLite

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