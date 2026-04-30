package es.bsalazar.magicleague.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import es.bsalazar.magicleague.ui.comp_dashboard.DashboardScreen

@Composable
fun NavigationWrapper() {
    val navController: NavHostController = rememberNavController()
    NavHost(navController = navController, startDestination = DashboardScreen) {
        composable<DashboardScreen> {
            DashboardScreen()
        }
        composable<League> {

        }
    }
}
