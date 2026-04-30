package es.bsalazar.magicleague.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import es.bsalazar.magicleague.R
import es.bsalazar.magicleague.data.SharedPreferences
import es.bsalazar.magicleague.models.Deck
import es.bsalazar.magicleague.utils.createBrushForDeckList

@Preview(showBackground = true)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectDeck(
    modifier: Modifier = Modifier,
    enable: Boolean = true,
    onDeckSelected: (Deck) -> Unit = {}
) {
//    Text(
//        text = stringResource(R.string.league_finded),
//        style = MaterialTheme.typography.headlineMedium
//    )
//    Spacer(modifier = Modifier.height(16.dp))

    Text(
        text = stringResource(R.string.choose_deck_description),
        color = if (enable) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
    )
    Spacer(modifier = Modifier.height(16.dp))

    var expanded by remember { mutableStateOf(false) }
    var selection by remember { mutableStateOf("") }

    ExposedDropdownMenuBox(
        modifier = Modifier
            .fillMaxWidth(),
        expanded = expanded,
        onExpandedChange = { if (enable) expanded = !expanded }) {

        OutlinedTextField(
            modifier = modifier
                .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
                .fillMaxWidth(),
            readOnly = true,
            value = selection,
            onValueChange = {},
            enabled = enable,
            label = { Text("Mazo") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) })

        DropdownMenu(
            modifier = Modifier
                .fillMaxWidth(),
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            SharedPreferences.getInstance(LocalContext.current).getUserDecks().forEach {
                DropdownMenuItem(
                    modifier = Modifier
                        .background(
                            brush = createBrushForDeckList(
                                colorList = it.colors,
                                startOffset = 0.3f
                            )
                        ),
                    text = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(it.name, style = MaterialTheme.typography.bodyMedium)
                        }
                    },
                    onClick = {
                        selection = it.name
                        expanded = false
                        onDeckSelected(it)
                    }
                )
            }
        }
    }
}