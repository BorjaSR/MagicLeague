package es.bsalazar.magicleague.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import es.bsalazar.magicleague.ui.theme.MagicLeagueTheme
import java.util.Locale
import androidx.compose.ui.graphics.Brush


@Preview(showBackground = true)
@Composable
fun MagicLeaguePrimaryButtonPreview() {
    MagicLeagueTheme {
        Column(modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)) {
            val enabled = true
            MagicLeaguePrimaryButton(
                text = "Boton Primario",
                enabled = enabled
            )
            Spacer(modifier = Modifier.height(20.dp))
            MagicLeagueSecondaryButton(
                text = "Boton Secundario",
                enabled = enabled
            )
        }
    }
}

@Composable
fun MagicLeaguePrimaryButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    enabled: Boolean = true,
    textColor: Color = MaterialTheme.colorScheme.onPrimary,
    text: String,
    shape: Shape = ButtonDefaults.shape
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            disabledContainerColor = MaterialTheme.colorScheme.inversePrimary
        ),
        onClick = { onClick() },
        shape = shape,
        content = {
            Text(
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                text = text.uppercase(Locale.getDefault()),
                color = textColor
            )
        }
    )
}

@Composable
fun MagicLeagueSecondaryButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    enabled: Boolean = false,
    textColor: Color = MaterialTheme.colorScheme.primary,
    text: String,
) {
    OutlinedButton (
        modifier = modifier
            .fillMaxWidth()
            .height(50.dp),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.background,
            disabledContainerColor = MaterialTheme.colorScheme.background
        ),
        border = ButtonDefaults.outlinedButtonBorder(enabled).copy(
            brush = if (enabled) {
                Brush.horizontalGradient(
                    0f to MaterialTheme.colorScheme.primary,
                    1f to MaterialTheme.colorScheme.secondary
                )
            } else {
                Brush.horizontalGradient(
                    0f to MaterialTheme.colorScheme.inversePrimary,
                    1f to MaterialTheme.colorScheme.inversePrimary
                )
            }
        ),
        onClick = { onClick() },
        content = {
            Text(
                modifier = Modifier.padding(8.dp),
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold,
                text = text.uppercase(Locale.getDefault()),
                color =  if(enabled) textColor else MaterialTheme.colorScheme.inversePrimary
            )
        }
    )
}

@Composable
fun MagicSeparator(enable: Boolean) {
    Box(
        modifier = Modifier
            .height(1.dp)
            .fillMaxWidth()
            .background(
                brush = Brush.horizontalGradient(
                    colors = if(enable) listOf(
                        MaterialTheme.colorScheme.primary,
                        MaterialTheme.colorScheme.secondary,
                        MaterialTheme.colorScheme.primary
                    )
                    else listOf(
                        MaterialTheme.colorScheme.inversePrimary,
                        MaterialTheme.colorScheme.inversePrimary
                    )
                )
            )
    )
}