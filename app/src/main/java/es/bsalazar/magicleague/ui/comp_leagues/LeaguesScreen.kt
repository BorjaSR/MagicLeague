package es.bsalazar.magicleague.ui.comp_leagues

import android.content.res.Configuration
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import es.bsalazar.magicleague.R
import es.bsalazar.magicleague.data.SharedPreferences
import es.bsalazar.magicleague.models.League
import es.bsalazar.magicleague.ui.theme.MagicLeagueTheme
import es.bsalazar.magicleague.ui.theme.MtgGreen
import es.bsalazar.magicleague.ui.theme.MtgRed
import es.bsalazar.magicleague.ui.theme.OrangeSecondary
import es.bsalazar.magicleague.utils.Constants

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun PreviewLeagueItem() {
    MagicLeagueTheme {
        LeagueItem(League("league Name"))
    }
}

@Composable
fun LeaguesScreen(
    leaguesViewModel: LeaguesViewModel = hiltViewModel(),
    navigateToLeagueDetail: (String) -> Unit = {}
) {

    val leaguesUiState = leaguesViewModel.uiState.collectAsStateWithLifecycle()

    leaguesViewModel.getMyLeagues(
        SharedPreferences.getInstance(LocalContext.current).getUserID()
    )

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        contentPadding = PaddingValues(16.dp)
    ) {
        items(leaguesUiState.value.leagues.size) { index ->
            LeagueItem(leaguesUiState.value.leagues[index]) {
                navigateToLeagueDetail(leaguesUiState.value.leagues[index].id.orEmpty())
            }
        }
    }
}

@Composable
fun LeagueItem(
    league: League,
    onClick: () -> Unit = {}
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        onClick = onClick,
        colors = CardDefaults.cardColors().copy(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier
                    .size(30.dp),
                painter = painterResource(id = R.drawable.ic_trophy),
                contentDescription = "",
                tint = MaterialTheme.colorScheme.onSurface
            )
            Column(modifier = Modifier.padding(start = 16.dp)) {
                Text(
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    text = league.name
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row(verticalAlignment = Alignment.CenterVertically) {

                    val stateColor = when(league.state){
                        Constants.LEAGUE_STATE.GETTING_ORGANIZED -> OrangeSecondary
                        Constants.LEAGUE_STATE.IN_PROGRESS -> MtgGreen
                        Constants.LEAGUE_STATE.FINISHED -> MtgRed
                    }

                    Box(
                        modifier = Modifier
                            .size(10.dp)
                            .background(
                                color = stateColor,
                                shape = CircleShape
                            )
                    )
                    Spacer(modifier = Modifier.width(8.dp))

                    Text(
                        style = MaterialTheme.typography.labelMedium,
                        text = when(league.state){
                            Constants.LEAGUE_STATE.GETTING_ORGANIZED -> stringResource(R.string.state_league_getting_organized)
                            Constants.LEAGUE_STATE.IN_PROGRESS -> stringResource(R.string.state_league_in_progress)
                            Constants.LEAGUE_STATE.FINISHED -> stringResource(R.string.state_league_finished)
                        },
                        color = stateColor
                    )
                }
            }
        }
    }
}