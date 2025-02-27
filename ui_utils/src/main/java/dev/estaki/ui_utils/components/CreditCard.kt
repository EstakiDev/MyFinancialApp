package dev.estaki.ui_utils.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.graphics.shapes.CornerRounding
import androidx.graphics.shapes.RoundedPolygon
import dev.estaki.ui_utils.CreditColors
import dev.estaki.ui_utils.R

@Composable
fun CreditCard(modifier: Modifier = Modifier) {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
        Card(
            modifier = modifier,
            shape = RoundedCornerShape(22.dp),
            colors = CardDefaults.cardColors(
                containerColor = CreditColors.random()
            ),
        ) {
            Column(
                modifier = modifier
                    .padding(vertical = 8.dp)
                    .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(36.dp),

                ) {
                Row(
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "کارت اعتباری", color = Color(0xFFB7B39F),fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                    Text(text = "نام بانک", color = Color(0xFFB7B39F), fontSize = 12.sp, fontWeight = FontWeight.SemiBold)
                }
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp)
                        .padding(start = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.chip),
                        contentDescription = "",
                        modifier = modifier
                            .size(32.dp)

                    )
                    Text(
                        modifier = modifier.wrapContentSize(),
                        textAlign = TextAlign.Center,
                        text = "1254226.6455.6",
                        fontSize = 13.sp,
                        color = Color(0xFFB7B39F)

                    )
                }
                Column {
                    Text(
                        modifier = modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = "**** **** **** ****",
                        fontSize = 14.sp,
                        color = Color(0xFFB7B39F), letterSpacing = 5.sp

                    )
                    Spacer(modifier.height(8.dp))
                    Row(
                        modifier = modifier.fillMaxWidth().padding(horizontal = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            textAlign = TextAlign.Center,
                            text = "نقی معمولی",
                            fontSize = 13.sp,
                            color = Color(0xFFB7B39F)

                        )
                        Text(
                            textAlign = TextAlign.Center,
                            text = "**/**",
                            fontSize = 13.sp,
                            color = Color(0xFFB7B39F)

                        )
                    }

                }
            }
        }
    }


}

@Preview
@Composable
fun CreditCardPreview() {
    CreditCard(modifier = Modifier)
}