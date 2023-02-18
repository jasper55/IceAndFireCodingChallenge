package wagner.jasper.iceandfirecodingchallenge.common.presentation.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp

@Composable
fun ShimmerView(
    size: IntSize,
    cornerSize: CornerSize = CornerSize(2.dp),
    padding: PaddingValues = PaddingValues(0.dp),
) {
    Box(
        Modifier
            .padding(padding)
            .height(size.height.dp)
            .width(size.width.dp)
            .clip(RoundedCornerShape(cornerSize))
            .shimmer(),
    )
}
