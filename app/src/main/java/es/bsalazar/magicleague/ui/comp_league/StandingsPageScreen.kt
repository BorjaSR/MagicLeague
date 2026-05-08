package es.bsalazar.magicleague.ui.comp_league

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.graphics.ColorUtils
import es.bsalazar.magicleague.R
import es.bsalazar.magicleague.models.PlayerLeague
import es.bsalazar.magicleague.ui.theme.Gray05
import es.bsalazar.magicleague.ui.theme.MagicLeagueTheme
import es.bsalazar.magicleague.ui.theme.MtgBlack
import es.bsalazar.magicleague.ui.theme.White
import es.bsalazar.magicleague.utils.Constants
import es.bsalazar.magicleague.utils.createBrushForDeckList

@Composable
fun StandingsPageScreen(
    modifier: Modifier = Modifier,
    leaguesUiState: State<LeagueUIState>
) {
    val players = leaguesUiState.value.league.playersInfo.sortedByDescending { it.points }

    Column(modifier = modifier.fillMaxSize()) {
        LazyColumn {
            stickyHeader {
                HeaderPlayerList()
            }
            items(players.size) {
                PlayerLeagueItem(playerLeagueInfo = players[it])
                HorizontalDivider()
            }
        }
    }
}


@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PlayerLeagueItemPreview() {
    MagicLeagueTheme {
        PlayerLeagueItem(
            playerLeagueInfo = PlayerLeague(
                name = "Borja",
                points = 10,
                wins = 10,
                defeats = 10,
                ties = 10,
                lifeGap = 10,
                colors = arrayListOf(
                    Constants.MTG_COLOR.GREEN,
                    Constants.MTG_COLOR.GREEN,
                    Constants.MTG_COLOR.BLACK,
                )
            )
        )
    }
}

data class HeaderPlayerTable(
    val name: String = "",
    val points: String = "",
    val wins: String = "",
    val defeats: String = "",
    val ties: String = "",
    val lifeGap: String = "",
    val colors: ArrayList<Constants.MTG_COLOR> = arrayListOf()
)

fun getTextColorByDeckColorsBackground(colors: ArrayList<Constants.MTG_COLOR>): Color {
    if (colors.size <= 2 && colors.contains(Constants.MTG_COLOR.BLACK)) return White
    return Gray05
}

@Composable
fun PlayerLeagueItem(
    playerLeagueInfo: PlayerLeague = PlayerLeague()
) {
    Row(
        modifier = Modifier
            .background(
                brush = createBrushForDeckList(
                    endOffset = 0.65f,
                    colorList = playerLeagueInfo.colors
                )
            )
            .padding(start = 16.dp, top = 16.dp, bottom = 16.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        Text(
            modifier = Modifier.weight(0.3f),
            text = playerLeagueInfo.name,
            style = MaterialTheme.typography.titleMedium,
            color = getTextColorByDeckColorsBackground(playerLeagueInfo.colors)
        )
        Row(modifier = Modifier.weight(0.7f)) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                text = playerLeagueInfo.points.toString()
            )
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                text = playerLeagueInfo.wins.toString()
            )
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                text = playerLeagueInfo.defeats.toString()
            )
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                text = playerLeagueInfo.ties.toString()
            )
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                text = playerLeagueInfo.lifeGap.toString()
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HeaderPlayerList(
    playerLeagueInfo: HeaderPlayerTable = HeaderPlayerTable(
        name = stringResource(R.string.player),
        points = "Pts",
        wins = "V",
        defeats = "D",
        ties = "E",
        lifeGap = "DV"
    )
) {
    Row(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.surface)
            .padding(start = 16.dp, top = 16.dp, bottom = 16.dp)
    ) {
        Text(
            modifier = Modifier.weight(0.3f),
            text = playerLeagueInfo.name
        )
        Row(modifier = Modifier.weight(0.7f)) {
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                text = playerLeagueInfo.points,
                fontWeight = FontWeight.Bold
            )
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                text = playerLeagueInfo.wins
            )
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                text = playerLeagueInfo.defeats
            )
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                text = playerLeagueInfo.ties
            )
            Text(
                modifier = Modifier.weight(1f),
                textAlign = TextAlign.Center,
                text = playerLeagueInfo.lifeGap
            )
        }
    }
}