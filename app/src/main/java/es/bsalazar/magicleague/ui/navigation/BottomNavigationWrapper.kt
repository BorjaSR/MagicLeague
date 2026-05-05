package es.bsalazar.magicleague.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import es.bsalazar.magicleague.ui.comp_actions.ActionsScreen
import es.bsalazar.magicleague.ui.comp_leagues.LeaguesScreen
import es.bsalazar.magicleague.ui.comp_profile.ProfileScreen

@Composable
fun BottomNavigationWrapper(
    navController: NavHostController,
    modifier: Modifier,
    navigationToLeague: (String) -> Unit) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Leagues
    ) {
        composable<Leagues> {
            LeaguesScreen(navigateToLeagueDetail = navigationToLeague)
        }
        composable<Dashboard> {
            ActionsScreen()
        }
        composable<Profile> {
            ProfileScreen()
        }
    }
}
