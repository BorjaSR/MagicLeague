package es.bsalazar.magicleague.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import es.bsalazar.magicleague.ui.comp_dashboard.DashboardScreen
import es.bsalazar.magicleague.ui.comp_league.LeagueScreen

@Composable
fun NavigationWrapper() {
    val navController: NavHostController = rememberNavController()
    NavHost(navController = navController, startDestination = DashboardScreen) {
        composable<DashboardScreen> {
            DashboardScreen(navigationToLeague = { leagueId ->
                navController.navigate(League(leagueId))
            })
        }
        composable<League> { navBackStackEntry ->
            LeagueScreen(leagueId = navBackStackEntry.toRoute<League>().id, onNavigateBack = { navController.popBackStack() })
        }
    }
}
