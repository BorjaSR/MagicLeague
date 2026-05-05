package es.bsalazar.magicleague.ui.comp_profile

import android.content.res.Configuration
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
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
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboard
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import es.bsalazar.magicleague.R
import es.bsalazar.magicleague.ui.components.LoadingCircular
import es.bsalazar.magicleague.ui.components.MagicLeaguePrimaryButton
import es.bsalazar.magicleague.ui.components.MagicSeparator
import es.bsalazar.magicleague.ui.components.SelectDeck
import es.bsalazar.magicleague.ui.theme.MagicLeagueTheme
import es.bsalazar.magicleague.ui.theme.MtgBlack
import es.bsalazar.magicleague.ui.theme.MtgBlue
import es.bsalazar.magicleague.ui.theme.MtgColorless
import es.bsalazar.magicleague.ui.theme.MtgGreen
import es.bsalazar.magicleague.ui.theme.MtgRed
import es.bsalazar.magicleague.ui.theme.MtgWhite
import es.bsalazar.magicleague.utils.Constants

@OptIn(ExperimentalMaterial3Api::class)
@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun PreviewCreateDeckBottomSheet() {
    MagicLeagueTheme {
        CreateDeckBottomSheet()
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateDeckBottomSheet(
    sheetState: SheetState = rememberModalBottomSheetState(),
    onDismiss: () -> Unit = {},
    onCreateDeck: (String, ArrayList<Constants.MTG_COLOR>) -> Unit = { _, _ -> }
) {

    val scrollState = rememberScrollState()
    var deckName by remember { mutableStateOf("") }
    var numDeckColors by remember { mutableIntStateOf(0) }
    val deckColors = arrayListOf<Constants.MTG_COLOR>()

    ModalBottomSheet(
        onDismissRequest = {
            onDismiss()
        },
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface
    ) {
        // Sheet content
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(16.dp)
        ) {
            Text(
                text = stringResource(R.string.create_deck_title),
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.height(16.dp))

            Text(text = stringResource(R.string.create_deck_description))
            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = deckName,
                label = { Text(text = stringResource(R.string.deck_name)) },
                onValueChange = {
                    deckName = it
                }
            )

            MagicSeparator(verticalPadding = 32.dp)

            Text(text = stringResource(R.string.create_deck_color_description))

            Spacer(modifier = Modifier.height(16.dp))

            DeckColorsSelector { isChecked, color ->
                if (isChecked) {
                    deckColors.add(color)
                    numDeckColors = deckColors.size
                } else {
                    deckColors.remove(color)
                    numDeckColors = deckColors.size
                }
            }

            Spacer(modifier = Modifier.height(60.dp))
            MagicLeaguePrimaryButton(
                text = stringResource(R.string.add_deck),
                enabled = deckName.length >= 3 && numDeckColors > 0,
                onClick = {
                    onCreateDeck(deckName, deckColors)
                })
        }
    }
}

@Composable
fun DeckColorsSelector(
    modifier: Modifier = Modifier,
    onChangeColorCheckbox: (Boolean, Constants.MTG_COLOR) -> Unit
) {
    Column(modifier = modifier) {
        Row {
            MagicCheckbox(
                modifier = Modifier.weight(1f),
                text = stringResource(R.string.white_deck),
                colors = CheckboxDefaults.colors(
                    checkedColor = MtgWhite,
                    uncheckedColor = MtgWhite,
                    checkmarkColor = MaterialTheme.colorScheme.onSurface
                )
            ) { isChecked ->
                onChangeColorCheckbox(isChecked, Constants.MTG_COLOR.WHITE)
            }
            MagicCheckbox(
                modifier = Modifier.weight(1f),
                text = stringResource(R.string.green_deck),
                colors = CheckboxDefaults.colors(
                    checkedColor = MtgGreen,
                    uncheckedColor = MtgGreen
                )
            ) { isChecked ->
                onChangeColorCheckbox(isChecked, Constants.MTG_COLOR.GREEN)
            }
            MagicCheckbox(
                modifier = Modifier.weight(1f),
                text = stringResource(R.string.red_deck),
                colors = CheckboxDefaults.colors(
                    checkedColor = MtgRed,
                    uncheckedColor = MtgRed
                )
            ) { isChecked ->
                onChangeColorCheckbox(isChecked, Constants.MTG_COLOR.RED)
            }
        }
        Row {
            MagicCheckbox(
                modifier = Modifier.weight(1f),
                text = stringResource(R.string.blue_deck),
                colors = CheckboxDefaults.colors(
                    checkedColor = MtgBlue,
                    uncheckedColor = MtgBlue
                )
            ) { isChecked ->
                onChangeColorCheckbox(isChecked, Constants.MTG_COLOR.BLUE)
            }
            MagicCheckbox(
                modifier = Modifier.weight(1f),
                text = stringResource(R.string.black_deck),
                colors = CheckboxDefaults.colors(
                    checkedColor = MtgBlack,
                    uncheckedColor = MtgBlack
                )
            ) { isChecked ->
                onChangeColorCheckbox(isChecked, Constants.MTG_COLOR.BLACK)
            }
            MagicCheckbox(
                modifier = Modifier.weight(1f),
                text = stringResource(R.string.colorless_deck),
                colors = CheckboxDefaults.colors(
                    checkedColor = MtgColorless,
                    uncheckedColor = MtgColorless
                )
            ) { isChecked ->
                onChangeColorCheckbox(isChecked, Constants.MTG_COLOR.COLORLESS)
            }
        }
    }
}

@Composable
fun MagicCheckbox(
    modifier: Modifier = Modifier,
    text: String = "",
    colors: CheckboxColors = CheckboxDefaults.colors(),
    onCheckedChange: (Boolean) -> Unit = {}
) {
    var isChecked by remember { mutableStateOf(false) }

    Row(
        modifier = modifier.clickable {
            isChecked = !isChecked
            onCheckedChange(isChecked)
        },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = isChecked,
            onCheckedChange = { checked ->
                isChecked = checked
                onCheckedChange(isChecked)
            },
            colors = colors
        )
        Text(text = text)
    }
}