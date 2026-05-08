package es.bsalazar.magicleague.ui.comp_league

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import es.bsalazar.magicleague.R
import es.bsalazar.magicleague.models.Match
import es.bsalazar.magicleague.models.MatchPlayer
import es.bsalazar.magicleague.ui.league.matches.MatchesAdapter
import es.bsalazar.magicleague.utils.createBrushForDeckList

@Composable
fun MatchesPageScreen(
    modifier: Modifier = Modifier,
    leagueViewModel: LeagueViewModel
) {

    val leaguesUiState = leagueViewModel.uiState.collectAsStateWithLifecycle()

    Column(modifier = modifier) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(leaguesUiState.value.league.matches.size) {
                MatchItem(leaguesUiState.value.league.matches[it])
            }
        }
    }
}

@Composable
fun MatchItem(match: Match = Match()) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        colors = CardDefaults.cardColors().copy(containerColor = MaterialTheme.colorScheme.surface)
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 16.dp)
        ) {
            Text(
                modifier = Modifier
                    .weight(1f),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                text = if (match.startingPlayer == 1)
                    match.player1.playerName
                else
                    match.player2.playerName
            )
            Text(
                text = stringResource(R.string.vs)
            )
            Text(
                modifier = Modifier
                    .weight(1f),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                text = if (match.startingPlayer == 1)
                    match.player2.playerName
                else
                    match.player1.playerName
            )
        }
    }
}

@Preview
@Composable
private fun MatchItemPreview() {
    MaterialTheme {
        MatchItem(
            Match(
                MatchPlayer("1", "Player 1"),
                MatchPlayer("2", "Player 2"),
                1
            )
        )
    }
}