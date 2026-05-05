package es.bsalazar.magicleague.ui.comp_profile

import android.content.res.Configuration
import android.widget.EditText
import android.widget.Toast
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import es.bsalazar.magicleague.R
import es.bsalazar.magicleague.data.SharedPreferences
import es.bsalazar.magicleague.models.Deck
import es.bsalazar.magicleague.ui.comp_actions.CreateLeagueBottomSheet
import es.bsalazar.magicleague.ui.components.MagicLeaguePrimaryButton
import es.bsalazar.magicleague.ui.components.MagicSeparator
import es.bsalazar.magicleague.ui.theme.MagicLeagueTheme
import es.bsalazar.magicleague.ui.theme.MtgIntenseRed
import es.bsalazar.magicleague.ui.theme.MtgRed
import es.bsalazar.magicleague.ui.theme.White
import es.bsalazar.magicleague.utils.Constants
import es.bsalazar.magicleague.utils.createBrushForDeckList
import kotlinx.coroutines.launch

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun ProfileScreenPreview() {
    MagicLeagueTheme {
        ProfileScreen()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(profileViewModel: ProfileViewModel = hiltViewModel()) {

    var newName by remember { mutableStateOf("") }
    val profileUiState = profileViewModel.uiState.collectAsStateWithLifecycle()

    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()
    val createDeckSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val editFocusRequester = remember { FocusRequester() }
    
    var deckToRemove by remember { mutableStateOf<Deck?>(null) }

    profileViewModel.setDeckList(SharedPreferences.getInstance(LocalContext.current).getUserDecks())

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {
        Spacer(modifier = Modifier.padding(30.dp))

        AnimatedContent(profileUiState.value.isNameEditing) { showEdit ->
            if (!showEdit) {
                Box(
                    contentAlignment = Alignment.CenterEnd,
                ) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        text = SharedPreferences.getInstance(context).getUserName().orEmpty(),
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onBackground
                    )

                    Icon(
                        modifier = Modifier
                            .padding(end = 16.dp)
                            .background(
                                color = MaterialTheme.colorScheme.primary,
                                shape = MaterialTheme.shapes.medium
                            )
                            .clickable {
                                profileViewModel.setIsNameEditing(true)
                                editFocusRequester.requestFocus()
                            }
                            .padding(8.dp),
                        painter = painterResource(R.drawable.ic_edit),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            } else {
                Column {
                    OutlinedTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(editFocusRequester),
                        label = { Text(text = "Nombre de usuario") },
                        value = newName,
                        onValueChange = {
                            newName = it
                        },
                        trailingIcon = {
                            Icon(
                                modifier = Modifier.clickable {
                                    profileViewModel.setIsNameEditing(
                                        false
                                    )
                                },
                                painter = painterResource(R.drawable.ic_close_small),
                                contentDescription = null
                            )
                        }
                    )
                    Spacer(modifier = Modifier.padding(16.dp))
                    MagicLeaguePrimaryButton(
                        enabled = newName.length >= 3,
                        text = stringResource(R.string.save)
                    ) {
                        SharedPreferences.getInstance(context).saveUserName(newName)
                        profileViewModel.setIsNameEditing(false)
                    }
                }
            }
        }

        Spacer(modifier = Modifier.padding(30.dp))
        MagicSeparator()
        Spacer(modifier = Modifier.padding(16.dp))

        Text(
            modifier = Modifier.padding(bottom = 20.dp),
            text = "Mis mazos",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onBackground
        )

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            ),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            AddDeckItem { profileViewModel.setCreateDeckBottomSheetVisibility(true) }
            profileUiState.value.deckList.forEach { deck ->
                DeckItem(deck) {
                    profileViewModel.setAlertVisible(true)
                    deckToRemove = it
                }
            }
        }
    }

    if (profileUiState.value.isCreateDeckBottomSheetVisible)
        CreateDeckBottomSheet(
            createDeckSheetState,
            onDismiss = {
                profileViewModel.setCreateDeckBottomSheetVisibility(false)
            }, onCreateDeck = { deckname, colors ->
                val deck = Deck(deckname, colors)
                SharedPreferences.getInstance(context).saveDeck(deck)
                profileViewModel.addDeck(deck)

                scope.launch { createDeckSheetState.hide() }.invokeOnCompletion {
                    profileViewModel.setCreateDeckBottomSheetVisibility(false)
                }
            })

    if (profileUiState.value.isAlertVisible) {
        AlertDialogExample(
            dialogText = stringResource(R.string.delete_dialog_description),
            textConfirmButton = stringResource(R.string.delete),
            textDismissButton = stringResource(R.string.cancel),
            onDismissRequest = { profileViewModel.setAlertVisible(false) },
            onConfirmation = {
                deckToRemove?.let {
                    SharedPreferences.getInstance(context).removeDeck(it)
                    profileViewModel.removeDeck(it)
                }
                profileViewModel.setAlertVisible(false)
            }
        )
    }
}

@Composable
fun DeckItem(deck: Deck, onSwipeToDelete: (Deck) -> Unit) {
    val swipeToDismissBoxState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            if (it == SwipeToDismissBoxValue.EndToStart) {
                onSwipeToDelete(deck)
            }
            // Reset item when toggling done status
            false
        }
    )

    SwipeToDismissBox(
        state = swipeToDismissBoxState,
        enableDismissFromStartToEnd = false,
        backgroundContent = {
            DeleteBackgroundSwipe()
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = createBrushForDeckList(
                            colorList = deck.colors,
                            startOffset = 0.3f
                        )
                    )
                    .padding(16.dp),
                text = deck.name,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
fun DeleteBackgroundSwipe() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(MtgIntenseRed)
            .padding(end = 16.dp),
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_delete_sweep),
            contentDescription = null,
            tint = White
        )
    }
}

@Composable
fun AddDeckItem(onAddDeckClicked: () -> Unit = {}) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onAddDeckClicked)
            .padding(16.dp)
    ) {
        Icon(
            painter = painterResource(R.drawable.ic_add),
            tint = MaterialTheme.colorScheme.primary,
            contentDescription = null
        )
        Text(
            modifier = Modifier
                .padding(start = 12.dp),
            text = "Añadir mazo",
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
fun AlertDialogExample(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    textConfirmButton: String,
    textDismissButton: String,
    dialogText: String
) {
    AlertDialog(
        text = {
            Text(text = dialogText)
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmation()
                }
            ) {
                Text(text = textConfirmButton)
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text(text = textDismissButton)
            }
        }
    )
}