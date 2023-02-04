package wagner.jasper.iceandfirecodingchallenge.housespage.presentation.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import wagner.jasper.iceandfirecodingchallenge.common.presentation.view.shimmer

@Composable
fun LoadingView(state: CombinedLoadStates, onErrorAction: () -> Unit) {

    if (state.append is LoadState.Loading) {
        ShimmerView()
    }
    if (state.append is LoadState.Error) {
        ErrorView { onErrorAction }
    }

}

@Composable
fun ShimmerView() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        ShimmerListItem()
        ShimmerListItem()
        ShimmerListItem()
        ShimmerListItem()
        ShimmerListItem()
        ShimmerListItem()
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
            .padding(bottom = 8.dp),
        elevation = 4.dp
    ) {
        Box(
            Modifier
                .padding(8.dp)
                .height(20.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(2.dp, 2.dp, 2.dp, 2.dp))
                .shimmer()
        )
    }
}