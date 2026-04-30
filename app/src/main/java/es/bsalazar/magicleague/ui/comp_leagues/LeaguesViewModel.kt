package es.bsalazar.magicleague.ui.comp_leagues

import androidx.lifecycle.MutableLiveData
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
class LeaguesViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(LeaguesUIState())
    val uiState: StateFlow<LeaguesUIState> = _uiState

    fun getMyLeagues(userID: String) {

        viewModelScope.launch(Dispatchers.IO) {
            Firestore.instance.getMyLeagues(userID) {
                _uiState.update { state ->
                    state.copy(leagues = it)
                }
            }
        }
    }

}

data class LeaguesUIState(
    val leagues: List<League> = listOf(),
    val isLoading: Boolean = false
)