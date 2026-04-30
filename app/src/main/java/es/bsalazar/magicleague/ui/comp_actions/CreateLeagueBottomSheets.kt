package es.bsalazar.magicleague.ui.comp_actions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import es.bsalazar.magicleague.R
import es.bsalazar.magicleague.models.League
import es.bsalazar.magicleague.ui.components.LoadingCircular
import es.bsalazar.magicleague.ui.components.MagicLeaguePrimaryButton
import es.bsalazar.magicleague.ui.components.SelectDeck
import es.bsalazar.magicleague.ui.theme.MagicLeagueTheme
import es.bsalazar.magicleague.utils.Constants

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun PreviewBottomSheet() {
    MagicLeagueTheme {
        CreateLeagueBottomSheet()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateLeagueBottomSheet(
    sheetState: SheetState = rememberModalBottomSheetState(),
    onDismiss: () -> Unit = {},
    onCreateLeague: (League, ArrayList<Constants.MTG_COLOR>) -> Unit = {_, _ -> },
    state: JoinLeagueBottomSheets = JoinLeagueBottomSheets.INITAL
) {
    var leagueName by remember { mutableStateOf("") }
    var selectedDeckColors = arrayListOf<Constants.MTG_COLOR>()

    var isValidLeagueName by remember { mutableStateOf(false) }
    var isValidDeck by remember { mutableStateOf(false) }

    ModalBottomSheet(
        onDismissRequest = {
            onDismiss()
        },
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        // Sheet content
        Column(modifier = Modifier.padding(16.dp)) {
            when (state) {
                JoinLeagueBottomSheets.INITAL -> {
                    Text(
                        text = stringResource(R.string.create_league),
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Spacer(modifier = Modifier.height(8.dp))

                    Text(text = stringResource(R.string.create_league_description))
                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = leagueName,
                        label = { Text(text = "Nombre de la liga") },
                        onValueChange = {
                            leagueName = it
                            isValidLeagueName = it.length >= 4
                        })
                    Spacer(modifier = Modifier.height(20.dp))
                    Box(
                        modifier = Modifier
                            .height(1.dp)
                            .background(
                                brush = Brush.horizontalGradient(
                                    colors = listOf(
                                        MaterialTheme.colorScheme.primary,
                                        MaterialTheme.colorScheme.secondary,
                                        MaterialTheme.colorScheme.primary
                                    )
                                )
                            )
                    )
                    Spacer(modifier = Modifier.height(20.dp))

                    SelectDeck(enable = isValidLeagueName) { selectedDeck ->
                        selectedDeckColors = selectedDeck.colors
                        isValidDeck = true
                    }
                }
                JoinLeagueBottomSheets.LOADING -> {
                    LoadingCircular(text = stringResource(R.string.creating_league))
                }
                JoinLeagueBottomSheets.JOIN_OK -> {

                }
            }


            Spacer(modifier = Modifier.height(40.dp))
            MagicLeaguePrimaryButton(
                text = stringResource(R.string.create_league_button),
                enabled = isValidLeagueName && isValidDeck,
                onClick = {
                    onCreateLeague(League(name = leagueName), selectedDeckColors)
                })
        }
    }
}