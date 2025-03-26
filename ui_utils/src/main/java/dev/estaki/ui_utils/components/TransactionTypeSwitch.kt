package dev.estaki.ui_utils.components

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.selection.toggleable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.estaki.domain.models.TransactionType
import dev.estaki.ui_utils.R
import dev.estaki.ui_utils.ui.theme.ariaFaNumFontFamily
import dev.estaki.ui_utils.utils.pxToDp


@Composable
fun TransactionTypeSwitch(
    modifier: Modifier = Modifier,
    checked: Boolean,
    transactionType: TransactionType,
    onValueChange: (Boolean) -> Unit
) {

    val handlePadding = 7.dp

    var width by remember { mutableStateOf(160.dp) }
    var height by remember { mutableStateOf(64.dp) }
    var handleSize by remember { mutableStateOf(52.dp) }

    val offset: Dp by animateDpAsState(
        targetValue = if (checked) width - handleSize - handlePadding else handlePadding, // مقدار جابجایی
        animationSpec = tween(
            durationMillis = 1000, // مدت زمان انیمیشن (میلی ثانیه)
            easing = FastOutSlowInEasing // نوع Easing (سرعت انیمیشن)
        ), label = ""
    )

    val context = LocalContext.current
    Row(
        modifier
            .onGloballyPositioned {
                width = context.pxToDp(it.size.width.toFloat()).dp
            }
            .fillMaxWidth()
            .height(height)
            .clip(RoundedCornerShape(height))
            .border(1.dp, Color.DarkGray, CircleShape)
            .background(Color.LightGray)
            .toggleable(
                value = checked,
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                enabled = true,
                role = Role.Switch,
                onValueChange = onValueChange
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {


        Row(
            modifier = Modifier
                .offset(offset)
                .clip(RoundedCornerShape(50))
                .graphicsLayer {
                    translationX = size.width * (1f - offset.value)
                }) {
            Surface(
                modifier = Modifier
                    .size(handleSize / 2)
                    .paint(painterResource(R.drawable.ic_income_32)),
            ) {

            }
            Text(
                text = "سلام",
                modifier = Modifier
                    .size(handleSize)
                    .clip(RoundedCornerShape(50))
            )
        }


    }


}


@Preview(showBackground = true, showSystemUi = true)
@Composable
fun TransactionTypeSwitchPreview() {
    Box(contentAlignment = Alignment.Center) {
        var value by remember { mutableStateOf(false) }
        Column {
            TransactionTypeSwitch(
                modifier = Modifier.fillMaxWidth(),
                transactionType = TransactionType.DEPOSIT,
                checked = value
            ) { newValue ->
                value = newValue
            }
            DarkModeSwitch(value, modifier = Modifier) {
                value = it
            }
            Text(
                text = if (true) "دخل" else "خرج",
                modifier = Modifier.wrapContentSize(),
                style = TextStyle(
                    fontFamily = ariaFaNumFontFamily,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            )
        }

    }

}