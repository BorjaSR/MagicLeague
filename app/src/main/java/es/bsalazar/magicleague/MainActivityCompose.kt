package es.bsalazar.magicleague

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import dagger.hilt.android.AndroidEntryPoint
import es.bsalazar.magicleague.ui.navigation.NavigationWrapper
import es.bsalazar.magicleague.ui.theme.MagicLeagueTheme

@AndroidEntryPoint
class MainActivityCompose : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MagicLeagueTheme {
                NavigationWrapper()
            }
        }
    }
}