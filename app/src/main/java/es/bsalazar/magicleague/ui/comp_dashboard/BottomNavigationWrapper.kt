package es.bsalazar.magicleague.ui.comp_dashboard

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import es.bsalazar.magicleague.ui.comp_actions.ActionsScreen
import es.bsalazar.magicleague.ui.comp_leagues.LeaguesScreen
import es.bsalazar.magicleague.ui.navigation.Dashboard
import es.bsalazar.magicleague.ui.navigation.Leagues
import es.bsalazar.magicleague.ui.navigation.Profile

@Composable
fun BottomNavigationWrapper(navController: NavHostController, modifier: Modifier) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Leagues
    ) {
        composable<Leagues> {
            LeaguesScreen()
        }
        composable<Dashboard> {
            ActionsScreen()
        }
        composable<Profile> {
            Text(text = "Profile")
        }
    }
}
