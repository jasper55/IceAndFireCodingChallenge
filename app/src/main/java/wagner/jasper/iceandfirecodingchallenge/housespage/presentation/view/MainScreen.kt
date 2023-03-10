package wagner.jasper.iceandfirecodingchallenge.housespage.presentation.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Portrait
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import wagner.jasper.iceandfirecodingchallenge.character.presentation.viewmodel.CharacterViewModel
import wagner.jasper.iceandfirecodingchallenge.common.presentation.view.DotsFlashing
import wagner.jasper.iceandfirecodingchallenge.destinations.HouseDetailsScreenDestination
import wagner.jasper.iceandfirecodingchallenge.housespage.domain.model.House
import wagner.jasper.iceandfirecodingchallenge.housespage.presentation.viewmodel.HousesViewModel

@Destination(start = true)
@Composable
fun MainScreen(
    navigator: DestinationsNavigator,
) {
    val viewModel = hiltViewModel<HousesViewModel>()

    val houses = viewModel.houses.collectAsLazyPagingItems()

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
                            .clickable {
                                navigator.navigate(HouseDetailsScreenDestination(house.id))
                            }
                            .padding(vertical = 6.dp),
                    )
                }
            }
            if (houses.loadState.append is LoadState.Loading ||
                houses.loadState.refresh is LoadState.Loading
            ) {
                item {
                    HousesShimmerView()
                }
            }

            if (houses.loadState.append is LoadState.Error ||
                houses.loadState.refresh is LoadState.Error
            ) {
                item {
                    ErrorView { viewModel.retry() }
                }
            }
        }
    }
}

@Composable
fun HouseItem(
    house: House,
    modifier: Modifier = Modifier,
) {
    val viewModel = hiltViewModel<CharacterViewModel>()
    val lordName = viewModel.characters.collectAsState().value[house.currentLord]?.name
    val isLoading = viewModel.isLoading.collectAsState().value[house.currentLord]

    LaunchedEffect(key1 = house.currentLord) {
        viewModel.loadCharacter(house.currentLord)
    }

    Card(
        modifier = modifier,
        elevation = 4.dp,
        shape = RoundedCornerShape(8.dp),
    ) {
        Column(
            modifier = Modifier.padding(8.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            Text(
                text = house.name,
                style = MaterialTheme.typography.h5,
            )
            RowItem(Icons.Default.LocationOn, house.region)
            RowItem(Icons.Default.Portrait, lordName, isLoading == true)
        }
    }
}

@Composable
private fun RowItem(
    icon: ImageVector,
    text: String?,
    isLoading: Boolean = false,
) {
    if (text == null && !isLoading) {
        return
    } else {
        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.spacedBy(2.dp),
        ) {
            Icon(
                icon,
                null,
                tint = Color.Gray,
                modifier = Modifier.size(24.dp),
            )
            if (isLoading) {
                DotsFlashing()
            } else {
                Text(text = text!!, style = MaterialTheme.typography.body1)
            }
        }
    }
}
