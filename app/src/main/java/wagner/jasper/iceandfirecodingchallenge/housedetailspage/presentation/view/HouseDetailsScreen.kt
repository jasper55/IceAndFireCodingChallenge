package wagner.jasper.iceandfirecodingchallenge.housedetailspage.presentation.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocationCity
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.Dispatchers
import wagner.jasper.iceandfirecodingchallenge.character.domain.model.GoTCharacter
import wagner.jasper.iceandfirecodingchallenge.housedetailspage.domain.model.HouseDetails
import wagner.jasper.iceandfirecodingchallenge.housedetailspage.presentation.viewmodel.HouseDetailsViewModel
import wagner.jasper.iceandfirecodingchallenge.housespage.presentation.view.ErrorView
import wagner.jasper.iceandfirecodingchallenge.housespage.presentation.view.HousesShimmerView

@Destination
@Composable
fun HouseDetailsScreen(
    navigator: DestinationsNavigator,
    id: Int,
) {
    val viewModel = hiltViewModel<HouseDetailsViewModel>()

    LaunchedEffect(Dispatchers.IO) {
        viewModel.loadHouseDetails(id)
    }
    val details = viewModel.houseDetails.collectAsState(null)
    val members = viewModel.members.collectAsState(emptyList())
    val isLoading = viewModel.isLoading.collectAsState(true)
    val hasError = viewModel.hasError.collectAsState(false)
    val founder = viewModel.founder.collectAsState()

    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .background(color = Color.White),
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
                .weight(1f),
        ) {
            details.value?.let { details ->
                HouseDetailsView(
                    details,
                    members.value,
                    founder.value,
                )
            }
            if (isLoading.value) {
                HousesShimmerView()
            }
            if (hasError.value) {
                ErrorView {
                    viewModel.reload()
                }
            }
        }
        BackButtonFab { navigator.popBackStack() }
    }
}

@Composable
fun HouseDetailsView(
    houseDetails: HouseDetails,
    members: List<GoTCharacter>,
    founder: GoTCharacter?,
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Text(
            text = "House Details",
            style = MaterialTheme.typography.h5,
            color = MaterialTheme.colors.primary,
        )

        Card(
            backgroundColor = MaterialTheme.colors.surface,
            shape = RoundedCornerShape(8.dp),
            modifier = Modifier.fillMaxWidth(),
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Title(houseDetails)
                Location(houseDetails)
                Quote(houseDetails)
                Titles(houseDetails)
                Foundation(houseDetails)
                Founder(founder)
                HouseObit(houseDetails)
                Weapons(houseDetails)
                CadetBranches(houseDetails)
                SwornMembers(members)
            }
        }
    }
}

@Composable
private fun SwornMembers(members: List<GoTCharacter>) {
    if (members.isNotEmpty()) {
        Row(
            verticalAlignment = Alignment.Top,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                "Members:",
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.Medium,
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier
                    .verticalScroll(rememberScrollState()),
            ) {
                members.forEach { member ->
                    Text(
                        text = member.name,
                        style = MaterialTheme.typography.subtitle2,
                        fontWeight = FontWeight.Normal,
                    )
                }
            }
        }
    }
}

@Composable
private fun CadetBranches(houseDetails: HouseDetails) {
    if (houseDetails.cadetBranches.any { it.isNotBlank() }) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                "Weapons:",
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.Medium,
            )
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                houseDetails.cadetBranches.forEach { branch ->
                    Text(
                        text = branch,
                        style = MaterialTheme.typography.subtitle2,
                        fontWeight = FontWeight.Normal,
                    )
                }
            }
        }
    }
}

@Composable
private fun Weapons(houseDetails: HouseDetails) {
    if (houseDetails.ancestralWeapons.any { it.isNotBlank() }) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                "Weapons:",
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.Medium,
            )
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                houseDetails.ancestralWeapons.forEach { weapon ->
                    Text(
                        text = weapon,
                        style = MaterialTheme.typography.subtitle2,
                        fontWeight = FontWeight.Normal,
                    )
                }
            }
        }
    }
}

@Composable
private fun HouseObit(houseDetails: HouseDetails) {
    if (houseDetails.diedOut.isNotEmpty()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                Icons.Default.Warning,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = MaterialTheme.colors.error,
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Died out: " + houseDetails.diedOut,
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colors.error,
            )
        }
    }
}

@Composable
private fun Founder(founder: GoTCharacter?) {
    val founder = founder ?: return
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            "Founder:",
            style = MaterialTheme.typography.subtitle1,
            fontWeight = FontWeight.Medium,
        )
        Text(
            text = founder.name,
            style = MaterialTheme.typography.subtitle1,
            fontWeight = FontWeight.Medium,
        )
    }
}

@Composable
private fun Foundation(houseDetails: HouseDetails) {
    if (houseDetails.founded.isNotEmpty()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                Icons.Default.CalendarToday,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = MaterialTheme.colors.primary,
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "Founded: " + houseDetails.founded,
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.Medium,
            )
        }
    }
}

@Composable
private fun Titles(houseDetails: HouseDetails) {
    if (houseDetails.titles.isNotEmpty()) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(4.dp),
        ) {
            houseDetails.titles.forEach { title ->
                Text(text = title, style = MaterialTheme.typography.subtitle1)
            }
        }
    }
}

@Composable
private fun Quote(houseDetails: HouseDetails) {
    if (houseDetails.words.isNotEmpty()) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                Icons.Default.FormatQuote,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = MaterialTheme.colors.primary,
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = houseDetails.words,
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.Medium,
                fontStyle = FontStyle.Italic,
            )
        }
    }
}

@Composable
private fun Location(houseDetails: HouseDetails) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            Icons.Default.LocationCity,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = MaterialTheme.colors.primary,
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = houseDetails.region,
            style = MaterialTheme.typography.subtitle1,
            fontWeight = FontWeight.Medium,
        )
    }
}

@Composable
private fun Title(houseDetails: HouseDetails) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            Icons.Default.Home,
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            tint = MaterialTheme.colors.primary,
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = houseDetails.name,
            style = MaterialTheme.typography.subtitle1,
            fontWeight = FontWeight.Medium,
        )
    }
}

@Composable
fun BackButtonFab(onClick: () -> Unit) {
    FloatingActionButton(
        onClick = onClick,
        modifier = Modifier.padding(16.dp),
    ) {
        Icon(Icons.Filled.ArrowBack, contentDescription = "Zurück")
    }
}
