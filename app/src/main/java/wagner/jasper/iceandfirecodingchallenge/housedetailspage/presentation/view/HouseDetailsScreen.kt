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
import androidx.compose.foundation.shape.RoundedCornerShape
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
    viewModel.loadHouseDetails(id)
    val details = viewModel.houseDetails.collectAsState(null)
    val isLoading = viewModel.isLoading.collectAsState(false)
    val hasError = viewModel.hasError.collectAsState(true)

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
                HouseDetailsView(houseDetails = details)
            } ?: if (isLoading.value) {
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
fun HouseDetailsView(houseDetails: HouseDetails) {
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

                if (houseDetails.titles.isNotEmpty()) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                    ) {
                        houseDetails.titles.forEach { title ->
                            Text(text = title, style = MaterialTheme.typography.subtitle1)
                        }
                    }
                }

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

                if (houseDetails.founded.isNotEmpty()) {
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
                            text = houseDetails.founder,
                            style = MaterialTheme.typography.subtitle1,
                            fontWeight = FontWeight.Medium,
                        )
                    }
                }

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

                if (houseDetails.swornMembers.any { it.isNotBlank() }) {
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
                            houseDetails.swornMembers.forEach { member ->
                                Text(
                                    text = member,
                                    style = MaterialTheme.typography.subtitle2,
                                    fontWeight = FontWeight.Normal,
                                )
                            }
                        }
                    }
                }
            }
        }
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
