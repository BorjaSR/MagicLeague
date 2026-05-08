package es.bsalazar.magicleague.ui.comp_league

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.bsalazar.magicleague.data.Firestore
import es.bsalazar.magicleague.models.League
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LeagueViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(LeagueUIState())
    val uiState: StateFlow<LeagueUIState> = _uiState

    fun getLeagueDetails(leagueId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            Firestore.instance.getRealTimeLeague(leagueId) {
                it?.let { leagueNotNull ->
                    _uiState.update { state ->
                        state.copy(league = leagueNotNull)
                    }
                }
            }
        }
    }

    fun updateLeagueName(leagueName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val newLeague = uiState.value.league.copy(name = leagueName)
            Firestore.instance.updateLeague(newLeague) { success ->

            }
        }
    }
}

data class LeagueUIState(
    val league: League = League(),
    val isLoading: Boolean = true
)