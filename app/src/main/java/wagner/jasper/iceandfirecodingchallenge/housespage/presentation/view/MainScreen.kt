package wagner.jasper.iceandfirecodingchallenge.housespage.presentation.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import wagner.jasper.iceandfirecodingchallenge.housespage.domain.model.House
import wagner.jasper.iceandfirecodingchallenge.housespage.presentation.viewmodel.HousesViewModel

@Composable
fun MainScreen(
    viewModel: HousesViewModel,
) {
    val houses = viewModel.houses.collectAsLazyPagingItems()
    HousesOverviewView(houses, viewModel)
}

@Composable
fun HousesOverviewView(houses: LazyPagingItems<House>, viewModel: HousesViewModel) {
    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            items(houses) { house ->
                house?.run {
                    HouseItem(
                        house = this,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { viewModel.onHouseClicked(this.id) }
                            .padding(top = 6.dp, bottom = 6.dp),
                    )
                }
            }
            if (houses.loadState.append is LoadState.Loading) {
                item {
                    LoadingView(houses.loadState) { viewModel.retry() }
                }
            }
        }
    }
}

@Composable
fun HouseItem(house: House, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        elevation = 4.dp,
        shape = RoundedCornerShape(8.dp),
    ) {
        Column(Modifier.padding(8.dp)) {
            Text(
                text = house.name,
                style = MaterialTheme.typography.body1,
                modifier = Modifier.padding(bottom = 4.dp),
            )
            Row(verticalAlignment = Alignment.Bottom) {
                Icon(
                    Icons.Default.LocationOn,
                    null,
                    tint = Color.Gray,
                    modifier = Modifier.size(16.dp),
                )
                Text(text = house.region, style = MaterialTheme.typography.overline)
            }
        }
    }
}
