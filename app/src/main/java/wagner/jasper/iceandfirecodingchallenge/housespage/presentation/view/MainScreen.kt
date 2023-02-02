package wagner.jasper.iceandfirecodingchallenge.housespage.presentation.view

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.R
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.paging.compose.items

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.flow.flowOf
import wagner.jasper.iceandfirecodingchallenge.housespage.presentation.viewmodel.HousesViewModel
import wagner.jasper.iceandfirecodingchallenge.housespage.domain.model.House

@Composable
fun MainScreen(
    viewModel: HousesViewModel,
) {

    val houses = viewModel.houses.collectAsLazyPagingItems()

    HousesOverviewView(houses = houses) {
        viewModel.onHouseClicked(it)
    }

}

@Composable
fun HousesOverviewView(houses: LazyPagingItems<House>, onHouseClicked: (Int) -> Unit) {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(houses) { house ->
                house?.run {
                    HouseItem(
                        house = this,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onHouseClicked(this.id) }
                            .padding(top = 6.dp, bottom = 6.dp)
                    )
                } ?:
                Text(text = "error")

                }
        }
    }
}

@Preview
@Composable
fun HousesOverviewLayoutLoadingPreview() {
    val data by flowOf(emptyList<House>()).collectAsState(initial = emptyList())

//    HousesOverviewView(data) {}
}

@Composable
fun HouseItem(house: House, modifier: Modifier = Modifier) {
    Card(
        modifier = modifier,
        elevation = 4.dp
    ) {
        Column(Modifier.padding(8.dp)) {
            Text(text = house.name, style = MaterialTheme.typography.overline)
        }
    }
}
