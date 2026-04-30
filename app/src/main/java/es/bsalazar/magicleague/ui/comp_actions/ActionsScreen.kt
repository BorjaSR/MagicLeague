package es.bsalazar.magicleague.ui.comp_actions

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import es.bsalazar.magicleague.R
import es.bsalazar.magicleague.data.SharedPreferences
import es.bsalazar.magicleague.models.PlayerLeague
import es.bsalazar.magicleague.ui.components.MagicLeaguePrimaryButton
import es.bsalazar.magicleague.ui.theme.MagicLeagueTheme
import kotlinx.coroutines.launch

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun ActionsScreenPreview() {
    MagicLeagueTheme {
        ActionsScreen()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActionsScreen(actionsViewModel: ActionsViewModel = hiltViewModel()) {

    val actionsUiState = actionsViewModel.uiState.collectAsStateWithLifecycle()

    val scope = rememberCoroutineScope()
    val createLeagueSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val joinLeagueSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(modifier = Modifier.weight(0.375f))
        MagicLeaguePrimaryButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            text = stringResource(R.string.create_league_button),
            onClick = {
                actionsViewModel.setCreateBottomSheetVisibility(true)
            },
            shape = RoundedCornerShape(20)
        )

        Spacer(modifier = Modifier.weight(0.25f))

        MagicLeaguePrimaryButton(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp),
            text = stringResource(R.string.join_league),
            onClick = {
                actionsViewModel.setJoinBottomSheetVisibility(true)
            },
            shape = RoundedCornerShape(20)
        )
        Spacer(modifier = Modifier.weight(0.375f))
    }

    val context = LocalContext.current
    if (actionsUiState.value.isCreateLeagueBottomSheetVisible)
        CreateLeagueBottomSheet(
            createLeagueSheetState,
            onDismiss = {
                actionsViewModel.setCreateBottomSheetVisibility(false)
            }, onCreateLeague = { league, colors ->
                actionsViewModel.createLeague(
                    league.copy(
                        players = arrayListOf(SharedPreferences.getInstance(context).getUserID()),
                        playersInfo = arrayListOf(
                            PlayerLeague(
                                id = SharedPreferences.getInstance(context).getUserID(),
                                name = SharedPreferences.getInstance(context).getUserName().orEmpty(),
                                colors = colors
                            )
                        )
                    )
                )
                scope.launch { createLeagueSheetState.hide() }.invokeOnCompletion {
                    actionsViewModel.setCreateBottomSheetVisibility(false)
                }
            })

    if (actionsUiState.value.isJoinLeagueBottomSheetVisible)
        JoinLeagueBottomSheet(
            sheetState = joinLeagueSheetState,
            onDismiss = {
                actionsViewModel.setJoinBottomSheetVisibility(false)
            },
            onJoinLeague = { leagueCode, selectedDeckColors ->
                actionsViewModel.joinToLeague(
                    leagueCode, PlayerLeague(
                        SharedPreferences.getInstance(context).getUserID(),
                        SharedPreferences.getInstance(context).getUserName().orEmpty(),
                        colors = selectedDeckColors
                    )
                )
                scope.launch { joinLeagueSheetState.hide() }.invokeOnCompletion {
                    actionsViewModel.setJoinBottomSheetVisibility(false)
                }
            },
            state = actionsUiState.value.joinLeagueState
        )
}