package wagner.jasper.iceandfirecodingchallenge.housedetailspage.presentation.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import wagner.jasper.iceandfirecodingchallenge.housedetailspage.presentation.viewmodel.HouseDetailsViewModel
import wagner.jasper.iceandfirecodingchallenge.housespage.domain.model.House
import wagner.jasper.iceandfirecodingchallenge.housespage.presentation.view.ErrorView
import wagner.jasper.iceandfirecodingchallenge.housespage.presentation.view.ShimmerView

@Destination
@Composable
fun HouseDetailsScreen(
    navigator: DestinationsNavigator,
    id: Int,
) {
    val viewModel = hiltViewModel<HouseDetailsViewModel>()
    val details = viewModel.houseDetails.collectAsState(null)
    val isLoading = viewModel.isLoading.collectAsState(false)
    val hasError = viewModel.hasError.collectAsState(true)

    Column(
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
    ) {
        details.value?.let { details ->
            Text(text = details.name)
            Text(text = details.region)
            Text(text = details.coatOfArms)
            Text(text = details.words)
            Text(text = details.founded)
            Text(text = details.founder)
            Text(text = details.diedOut)
            Text(text = details.overlord)
            Text(text = details.currentLord)
            Text(text = details.heir)
        } ?: if (isLoading.value) {
            ShimmerView()
        }
        if (hasError.value) {
            ErrorView {
                viewModel.reload()
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
