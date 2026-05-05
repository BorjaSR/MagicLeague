package es.bsalazar.magicleague.ui.comp_dashboard

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import es.bsalazar.magicleague.R
import es.bsalazar.magicleague.ui.navigation.Dashboard
import es.bsalazar.magicleague.ui.navigation.Leagues
import es.bsalazar.magicleague.ui.navigation.Profile
import es.bsalazar.magicleague.ui.theme.MagicLeagueTheme

data class NavItem(
    val route: Any,
    val name: String,
    val icon: Painter
)

@Preview(showBackground = true)
@Composable
fun PreviewMagicNavigationBar() {
    MagicLeagueTheme {
        MagicNavigationBar()
    }
}

@Composable
fun MagicNavigationBar(onNavItemClick: (NavItem) -> Unit = {}) {
    var selectedIndex by rememberSaveable { mutableIntStateOf(0) }

    val navItemList = listOf(
        NavItem(
            Leagues,
            stringResource(R.string.title_leagues),
            painterResource(R.drawable.ic_trophy)
        ),
        NavItem(
            Dashboard,
            stringResource(R.string.title_dashboard),
            painterResource(R.drawable.ic_dashboard_black_24dp)
        ),
        NavItem(
            Profile,
            stringResource(R.string.title_profile),
            painterResource(R.drawable.ic_account_circle)
        )
    )

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surfaceVariant
    ) {
        navItemList.forEachIndexed { index, item ->
            MyNavigationItem(
                selected = selectedIndex == index,
                text = item.name,
                iconPainter = item.icon
            ) {
                selectedIndex = index
                onNavItemClick(item)
            }
        }
    }
}

@Composable
fun RowScope.MyNavigationItem(
    selected: Boolean, text: String, iconPainter: Painter, onClick: () -> Unit
) {
    NavigationBarItem(
        selected = selected,
        onClick = onClick,
        icon = { Icon(painter = iconPainter, contentDescription = null) },
        label = { Text(text) },
        alwaysShowLabel = false,
        colors = NavigationBarItemDefaults.colors().copy(
            selectedIndicatorColor = MaterialTheme.colorScheme.primaryFixed,
            unselectedIconColor = MaterialTheme.colorScheme.secondaryFixed
        )
    )
}