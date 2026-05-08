package es.bsalazar.magicleague.ui.comp_league

import android.widget.RadioGroup
import android.widget.Space
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import es.bsalazar.magicleague.R
import es.bsalazar.magicleague.data.SharedPreferences
import es.bsalazar.magicleague.ui.components.MagicLeaguePrimaryButton
import es.bsalazar.magicleague.ui.components.MagicSeparator
import es.bsalazar.magicleague.utils.Constants

@Composable
fun ConfigurationPageScreen(
    modifier: Modifier = Modifier,
    leagueViewModel: LeagueViewModel
) {

    val leaguesUiState = leagueViewModel.uiState.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()

    Column(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {
            Text(
                modifier = Modifier.padding(bottom = 16.dp),
                text = stringResource(R.string.league_name)
            )
            TextWithButtonForEdit(
                text = leaguesUiState.value.league.name,
                onEdit = {
                    leagueViewModel.updateLeagueName(it)
                }
            )
            MagicSeparator(verticalPadding = 16.dp)

            if (leaguesUiState.value.league.state == Constants.LEAGUE_STATE.GETTING_ORGANIZED) {
                Text(
                    modifier = Modifier.padding(bottom = 16.dp),
                    text = stringResource(R.string.laps_number_text)
                )
                RadioButtonSingleSelection()

                Spacer(modifier = Modifier.weight(1f))
                Spacer(modifier = Modifier.height(20.dp))
                MagicLeaguePrimaryButton(text = stringResource(R.string.start_league))
            } else {
                Text(
                    modifier = Modifier.padding(bottom = 16.dp),
                    text = stringResource(R.string.laps_number_text) + " ${leaguesUiState.value.league.laps}"
                )
            }
        }
    }
}

@Composable
fun RadioButtonSingleSelection(modifier: Modifier = Modifier) {
    val radioOptions = listOf("Indefinida", "Numero de vueltas limitado")
    val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }
    val focusRequester = remember { FocusRequester() }

    var laps by remember { mutableStateOf("") }
    // Note that Modifier.selectableGroup() is essential to ensure correct accessibility behavior
    Column(modifier.selectableGroup()) {
        radioOptions.forEachIndexed { index, text ->

            LaunchedEffect(selectedOption) {
                if (selectedOption == "Numero de vueltas limitado")
                    focusRequester.requestFocus()
            }

            Column {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = (text == selectedOption),
                            onClick = {
                                onOptionSelected(text)
                            },
                            role = Role.RadioButton
                        )
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (text == selectedOption),
                        onClick = null // null recommended for accessibility with screen readers
                    )

                    if (index == 1) {
                        OutlinedTextField(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(2f)
                                .padding(start = 16.dp)
                                .focusRequester(focusRequester),
                            enabled = text == selectedOption,
                            label = { Text(text = stringResource(R.string.laps_number_text)) },
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            value = laps,
                            onValueChange = {
                                laps = it
                            }
                        )
                        Spacer(modifier = Modifier.weight(1f))
                    } else {
                        Text(
                            text = text,
                            style = MaterialTheme.typography.bodyLarge,
                            modifier = Modifier.padding(start = 16.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TextWithButtonForEdit(
    text: String,
    onEdit: (String) -> Unit
) {
    var newText by remember { mutableStateOf(text) }
    var isEditing by remember { mutableStateOf(false) }

    AnimatedContent(isEditing) { showEdit ->
        if (!showEdit) {
            Box(
                contentAlignment = Alignment.CenterEnd,
            ) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = newText,
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
                            isEditing = true
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
                        .fillMaxWidth(),
                    label = { Text(text = "Nombre de la liga") },
                    value = newText,
                    onValueChange = {
                        newText = it
                    },
                    trailingIcon = {
                        Icon(
                            modifier = Modifier.clickable {
                                isEditing = false
                            },
                            painter = painterResource(R.drawable.ic_close_small),
                            contentDescription = null
                        )
                    }
                )
                Spacer(modifier = Modifier.padding(16.dp))
                MagicLeaguePrimaryButton(
                    enabled = newText.length in 3..30,
                    text = stringResource(R.string.save)
                ) {
                    onEdit(newText)
                    isEditing = false
                }
            }
        }
    }
}
