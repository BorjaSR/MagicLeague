package es.bsalazar.magicleague.ui.comp_dashboard

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import es.bsalazar.magicleague.ui.navigation.BottomNavigationWrapper
import es.bsalazar.magicleague.ui.theme.MagicLeagueTheme

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO, name = "Light mode")
@Composable
fun PreviewDashboardScreen() {
    MagicLeagueTheme {
        DashboardScreen()
    }
}

@Composable
fun DashboardScreen(navigationToLeague: (String) -> Unit = {}) {

    val navController: NavHostController = rememberNavController()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            MagicNavigationBar {
                navController.navigate(it.route)
            }
        }
    ) { contentPadding ->
        BottomNavigationWrapper(
            navController,
            Modifier.padding(contentPadding),
            navigationToLeague
        )
    }
}
