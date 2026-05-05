package es.bsalazar.magicleague.ui.comp_league

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun LeagueScreen(leagueViewModel: LeagueViewModel = hiltViewModel()) {
    Text(text = "League Detail")
}