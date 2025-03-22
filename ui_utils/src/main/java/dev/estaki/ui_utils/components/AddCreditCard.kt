package dev.estaki.ui_utils.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import dev.estaki.ui_utils.R
import dev.estaki.ui_utils.ui.theme.ColorGrayLite
import dev.estaki.ui_utils.ui.theme.White
import dev.estaki.ui_utils.ui.theme.ariaFaNumFontFamily

@Composable
fun AddCreditCard(
    modifier: Modifier = Modifier,
    onClicked: () -> Unit
) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        Card(
            modifier = Modifier.alpha(0.5F),
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(
                containerColor = ColorGrayLite
            ),
            onClick = {onClicked.invoke()}
        ) {


            Column(
                modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    modifier = modifier.size(65.dp),
                    painter = painterResource(R.drawable.round_add_24),
                    contentDescription = "افزودن کارت اعتباری",
                    colorFilter = ColorFilter.tint(color = White)
                )
                Spacer(modifier.height(12.dp))
                Text(
                    text = "افزودن کارت اعتباری",
                    color = White,
                    fontSize = 16.sp,
                    fontFamily = ariaFaNumFontFamily,
                    fontWeight = FontWeight.Black
                )
            }
        }
    }

}

@Preview
@Composable
private fun AddCreditCardPreview() {
    AddCreditCard(){

    }
}