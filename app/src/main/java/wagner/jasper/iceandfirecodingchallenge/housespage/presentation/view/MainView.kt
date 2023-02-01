package wagner.jasper.iceandfirecodingchallenge.housespage.presentation.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.flow.flowOf
import wagner.jasper.iceandfirecodingchallenge.housespage.presentation.viewmodel.HousesViewModel
import wagner.jasper.iceandfirecodingchallenge.housespage.domain.model.House

@Composable
fun HousesOverviewScreen(
    viewModel: HousesViewModel,
) {

    val houses by viewModel.houses.collectAsState(initial = emptyList())

    HousesOverviewView(houses = houses) {
        viewModel.onHouseClicked(it)
    }

}

@Composable
fun HousesOverviewView(houses: List<House>, onHouseClicked: (Int) -> Unit) {
    Box(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            items(
                items = houses,
                itemContent = { house ->
                    HouseItem(
                        house = house,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { onHouseClicked(house.id) }
                            .padding(top = 2.dp, bottom = 2.dp)
                    )
                }
            )
        }
    }
}

@Preview
@Composable
fun HousesOverviewLayoutLoadingPreview() {
    val data by flowOf(emptyList<House>()).collectAsState(initial = emptyList())

    HousesOverviewView(data) {}
}

@Composable
fun HouseItem(house: House, modifier: Modifier = Modifier) {
    Card(

    ) {
        Column(Modifier.padding(8.dp)) {
            Text(text = house.name, style = MaterialTheme.typography.h1)
        }
    }
}
