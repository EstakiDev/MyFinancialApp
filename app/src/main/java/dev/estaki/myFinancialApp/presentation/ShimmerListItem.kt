package dev.estaki.myFinancialApp.presentation

import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.valentinilk.shimmer.shimmer
import dev.estaki.myFinancialApp.ui.theme.ColorBorderWhite
import dev.estaki.myFinancialApp.ui.theme.ColorOfCenterShimmer
import dev.estaki.myFinancialApp.ui.theme.ColorOfShimmer
import dev.estaki.myFinancialApp.ui.theme.ColorShimmerGrayMedium


@Composable
fun ShimmerListItems(
    isLoading: Boolean,
    contentAfterLoading: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    if (isLoading) {
        ShimmerListItem()


    } else {
        contentAfterLoading()
    }
}

@Preview
@Composable
fun ShimmerListItemPre() {
    ShimmerListItem()

}

@Composable
fun ShimmerListItem() {
    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Card(
            shape = RoundedCornerShape(8.dp),
            elevation = CardDefaults.cardElevation(8.dp),
            border = BorderStroke(
                2.dp,
                ColorBorderWhite
            ),
            modifier = Modifier
                .padding(horizontal = 12.dp, vertical = 4.dp)
                .fillMaxWidth()
                .height(130.dp)) {
            Column(
                modifier = Modifier.fillMaxSize().shimmer(),
                horizontalAlignment = Alignment.End,
                verticalArrangement = Arrangement.SpaceAround
            ) {
                Row(
                    modifier = Modifier
                        .padding(vertical = 8.dp, horizontal = 12.dp)
                        .fillMaxWidth()
                        .wrapContentHeight(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround

                ) {

                    Box(
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .size(50.dp)
                            .padding(1.dp)
                            .clip(CircleShape)
                            .background(ColorShimmerGrayMedium).shimmer()
                    )
                    Column(modifier = Modifier.padding(horizontal = 12.dp)) {
                        Box(
                            modifier = Modifier
                                .width(70.dp)
                                .height(20.dp)
                                .background(
                                    ColorShimmerGrayMedium,
                                    shape = RoundedCornerShape(8.dp)
                                ).shimmer()
                        )
                        Spacer(modifier = Modifier.size(4.dp))
                        Box(
                            modifier = Modifier
                                .width(90.dp)
                                .height(20.dp)
                                .background(
                                    ColorShimmerGrayMedium,
                                    shape = RoundedCornerShape(8.dp)
                                ).shimmer()
                        )
                        Spacer(modifier = Modifier.size(4.dp))

                        Box(
                            modifier = Modifier
                                .width(30.dp)
                                .height(20.dp)
                                .background(
                                    ColorShimmerGrayMedium,
                                    shape = RoundedCornerShape(8.dp)
                                ).shimmer()
                        )
                    }


                    Box(
                        modifier = Modifier
                            .padding(end = 12.dp)
                            .size(50.dp)
                            .padding(1.dp)
                            .clip(CircleShape)
                            .background(ColorShimmerGrayMedium)
                            .shimmer()
                    )


                }
                Row(
                    modifier = Modifier.padding(8.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .width(60.dp)
                            .height(20.dp)
                            .background(
                                ColorShimmerGrayMedium,
                                shape = RoundedCornerShape(8.dp)
                            ).shimmer()
                    )
                    Spacer(modifier = Modifier.size(4.dp))
                    Box(
                        modifier = Modifier
                            .width(40.dp)
                            .height(20.dp)
                            .background(
                                ColorShimmerGrayMedium,
                                shape = RoundedCornerShape(8.dp)
                            ).shimmer()

                    )
                }

            }


        }
    }

}


fun Modifier.shimmerEffect(): Modifier = composed {
    var size by remember {
        mutableStateOf(IntSize.Zero)
    }
    val transition = rememberInfiniteTransition(label = "")
    val startOffsetX by transition.animateFloat(
        initialValue = -2 * size.width.toFloat(),
        targetValue = 2 * size.width.toFloat(),
        animationSpec = infiniteRepeatable(
            animation = tween(3000)
        ), label = ""
    )

    background(
        brush = Brush.linearGradient(
            colors = listOf(
                ColorOfShimmer,
                ColorOfCenterShimmer,
                ColorOfShimmer,
            ),
            start = Offset(startOffsetX, 0f),
            end = Offset(startOffsetX + size.width.toFloat(), size.height.toFloat())
        )
    ).onGloballyPositioned {
        size = it.size
    }
}

