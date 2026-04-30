package es.bsalazar.magicleague.ui.comp_actions

import android.content.ClipData
import android.content.res.Configuration
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import es.bsalazar.magicleague.R
import es.bsalazar.magicleague.ui.components.LoadingCircular
import es.bsalazar.magicleague.ui.components.MagicLeaguePrimaryButton
import es.bsalazar.magicleague.ui.components.SelectDeck
import es.bsalazar.magicleague.ui.theme.MagicLeagueTheme
import es.bsalazar.magicleague.utils.Constants

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewJoinLeagueBottomSheet() {
    MagicLeagueTheme {
        JoinLeagueBottomSheet()
    }
}

enum class JoinLeagueBottomSheets {
    INITAL,
    LOADING,
    JOIN_OK
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun JoinLeagueBottomSheet(
    sheetState: SheetState = rememberModalBottomSheetState(),
    onDismiss: () -> Unit = {},
    onJoinLeague: (String, ArrayList<Constants.MTG_COLOR>) -> Unit = { _, _ -> },
    state: JoinLeagueBottomSheets = JoinLeagueBottomSheets.INITAL
) {
    var leagueCode by remember { mutableStateOf("") }
    var selectedDeckColors = arrayListOf<Constants.MTG_COLOR>()

    var isValidLeagueCode by remember { mutableStateOf(false) }
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

            AnimatedContent(targetState = state, label = "Join League Sheet State") { state ->
                when (state) {
                    JoinLeagueBottomSheets.INITAL -> {
                        Column {
                            JoinLeagueName(leagueCode) {
                                leagueCode = it
                                isValidLeagueCode = it.length >= 18
                            }
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

                            SelectDeck(enable = isValidLeagueCode) { selectedDeck ->
                                selectedDeckColors = selectedDeck.colors
                                isValidDeck = true
                            }
                        }
                    }

                    JoinLeagueBottomSheets.LOADING -> {
                        LoadingCircular(text = stringResource(R.string.looking_for_league))
                    }

                    JoinLeagueBottomSheets.JOIN_OK -> {
                        Text(
                            text = stringResource(R.string.league_finded),
                            style = MaterialTheme.typography.headlineMedium
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                }
            }
            Spacer(modifier = Modifier.height(60.dp))
            MagicLeaguePrimaryButton(
                text = stringResource(R.string.search_league_button),
                enabled = isValidLeagueCode && isValidDeck,
                onClick = {
                    if (leagueCode.isNotEmpty() && selectedDeckColors.isNotEmpty())
                        onJoinLeague(leagueCode, selectedDeckColors)
                })
        }
    }
}

@Composable
fun JoinLeagueName(
    leagueCode: String,
    onLeagueCodeChange: (String) -> Unit
) {
    val clipboardManager = LocalClipboard.current
    Text(
        text = stringResource(R.string.join_league),
        style = MaterialTheme.typography.headlineMedium
    )
    Spacer(modifier = Modifier.height(16.dp))

    Text(text = stringResource(R.string.join_league_description))
    Spacer(modifier = Modifier.height(16.dp))

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = leagueCode,
        label = { Text(text = "Codigo de la liga") },
        onValueChange = {
            onLeagueCodeChange(it)
        },
        trailingIcon = {
            val clipData = (clipboardManager.nativeClipboard.primaryClip as ClipData)
            if (clipData.itemCount > 0) {
                Icon(
                    modifier = Modifier.clickable {
                        onLeagueCodeChange(clipData.getItemAt(0).text.toString())
                    },
                    painter = painterResource(id = R.drawable.ic_content_paste_24),
                    tint = MaterialTheme.colorScheme.onSurface,
                    contentDescription = null
                )
            }
        })
}
