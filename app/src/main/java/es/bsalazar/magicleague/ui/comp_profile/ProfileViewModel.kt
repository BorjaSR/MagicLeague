package es.bsalazar.magicleague.ui.comp_profile

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import es.bsalazar.magicleague.models.Deck
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(ProfileUIState())
    val uiState: StateFlow<ProfileUIState> = _uiState

    fun setIsNameEditing(isEditing: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                isNameEditing = isEditing
            )
        }
    }

    fun setCreateDeckBottomSheetVisibility(isVisible: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                isCreateDeckBottomSheetVisible = isVisible
            )
        }
    }

    fun setDeckList(deckList: ArrayList<Deck>) {
        _uiState.update { currentState ->
            currentState.copy(
                deckList = deckList
            )
        }
    }

    fun addDeck(deck: Deck) {
        _uiState.update { currentState ->
            currentState.copy(
                deckList = currentState.deckList.apply { add(deck) }
            )
        }
    }

    fun removeDeck(deck: Deck) {
        _uiState.update { currentState ->
            currentState.copy(
                deckList = currentState.deckList.apply { remove(deck) }
            )
        }
    }

    fun setAlertVisible(bool: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(
                isAlertVisible = bool
            )
        }
    }
}

data class ProfileUIState(
    val deckList: ArrayList<Deck> = arrayListOf(),
    val isNameEditing: Boolean = false,
    val isCreateDeckBottomSheetVisible: Boolean = false,
    val isAlertVisible: Boolean = false
)