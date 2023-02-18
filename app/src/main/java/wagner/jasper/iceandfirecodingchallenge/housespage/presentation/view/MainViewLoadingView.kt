package wagner.jasper.iceandfirecodingchallenge.housespage.presentation.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import wagner.jasper.iceandfirecodingchallenge.common.presentation.view.ShimmerView

@Composable
fun HousesShimmerView() {
    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        for (i in 1..20) {
            ShimmerListItem()
        }
    }
}

@Composable
fun ErrorView(onErrorAction: () -> Unit) {
    Button(onErrorAction) {
        Text("Error")
    }
}

@Composable
fun ShimmerListItem(modifier: Modifier = Modifier) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = 4.dp,
    ) {
        Column {
            ShimmerView(
                size = IntSize(120, 20),
                padding = PaddingValues(
                    top = 8.dp,
                    start = 8.dp,
                    bottom = 8.dp,
                ),
            )
            Row {
                ShimmerView(
                    size = IntSize(20, 20),
                    padding = PaddingValues(
                        start = 8.dp,
                        end = 8.dp,
                        bottom = 8.dp,
                    ),
                )
                ShimmerView(
                    size = IntSize(80, 20),
                    padding = PaddingValues(bottom = 8.dp),
                )
            }
            Row {
                ShimmerView(
                    size = IntSize(20, 20),
                    padding = PaddingValues(
                        start = 8.dp,
                        end = 8.dp,
                        bottom = 8.dp,
                    ),
                )
                ShimmerView(
                    size = IntSize(150, 20),
                    padding = PaddingValues(bottom = 8.dp),
                )
            }
        }
    }
}
