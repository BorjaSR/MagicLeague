package es.bsalazar.magicleague.ui.comp_actions

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import es.bsalazar.magicleague.data.Firestore
import es.bsalazar.magicleague.models.League
import es.bsalazar.magicleague.models.PlayerLeague
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActionsViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(ActionsUIState())
    val uiState: StateFlow<ActionsUIState> = _uiState

    fun setCreateBottomSheetVisibility(isVisible: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                isCreateLeagueBottomSheetVisible = isVisible
            )
        }
    }

    fun setJoinBottomSheetVisibility(isVisible: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                isJoinLeagueBottomSheetVisible = isVisible
            )
        }
    }

    fun createLeague(league: League) {
        _uiState.update { currentState ->
            currentState.copy(
                createLeagueState = JoinLeagueBottomSheets.LOADING
            )
        }
        viewModelScope.launch(Dispatchers.IO) {
            Firestore.instance.saveLeague(
                league,
                success = {
                    _uiState.update { currentState ->
                        currentState.copy(
                            createLeagueState = JoinLeagueBottomSheets.JOIN_OK
                        )
                    }
                })
        }
    }

    fun joinToLeague(leagueCode: String, playerLeague: PlayerLeague) {
        _uiState.update { currentState ->
            currentState.copy(
                joinLeagueState = JoinLeagueBottomSheets.LOADING
            )
        }
        viewModelScope.launch(Dispatchers.IO) {
            Firestore.instance.addPlayerToLeague(playerLeague, leagueCode) { result ->
                _uiState.update { currentState ->
                    currentState.copy(
                        joinLeagueState = if (result) JoinLeagueBottomSheets.JOIN_OK else JoinLeagueBottomSheets.INITAL
                    )
                }
            }
        }
    }

}

data class ActionsUIState(
    val isCreateLeagueBottomSheetVisible: Boolean = false,
    val isJoinLeagueBottomSheetVisible: Boolean = false,
    val createLeagueState: JoinLeagueBottomSheets = JoinLeagueBottomSheets.INITAL,
    val joinLeagueState: JoinLeagueBottomSheets = JoinLeagueBottomSheets.INITAL
)