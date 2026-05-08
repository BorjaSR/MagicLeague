package es.bsalazar.magicleague.ui.comp_league

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryScrollableTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import es.bsalazar.magicleague.R
import kotlinx.coroutines.launch

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun LeagueScreen(
    leagueId: String = "",
    onNavigateBack: () -> Unit = { },
    leagueViewModel: LeagueViewModel = hiltViewModel()
) {
    val clipboardManager = LocalClipboardManager.current
    val context = LocalContext.current

    val copiedText  =stringResource(R.string.league_code_copied)
    val leaguesUiState = leagueViewModel.uiState.collectAsStateWithLifecycle()
    leagueViewModel.getLeagueDetails(leagueId)

    Scaffold(topBar = {
        LeagueTopBar(
            leagueName = leaguesUiState.value.league.name,
            leagueCode = leaguesUiState.value.league.id.orEmpty(),
            onClickBack = onNavigateBack,
            onClickLeagueCode = {
            clipboardManager.setText(AnnotatedString(leagueId))
            Toast.makeText(context, copiedText, Toast.LENGTH_SHORT).show()
        })
    }) { contentPadding ->
        MainContent(leagueViewModel, leaguesUiState, contentPadding)
    }
}

enum class LeagueScreen {
    CONFIGURATION,
    STANDINGS,
    MATCHES
}

@Composable
fun MainContent(
    leagueViewModel: LeagueViewModel,
    leaguesUiState: State<LeagueUIState>,
    contentPadding: PaddingValues = PaddingValues()
) {
    val pagerState = rememberPagerState(
        initialPage = LeagueScreen.STANDINGS.ordinal,
        pageCount = { LeagueScreen.entries.size })
    val coroutineScope = rememberCoroutineScope()

    Column {
        PrimaryScrollableTabRow(
            modifier = Modifier
                .padding(top = contentPadding.calculateTopPadding())
                .fillMaxWidth(),
            selectedTabIndex = pagerState.currentPage,
        ) {
            Tab(
                selected = pagerState.currentPage == LeagueScreen.CONFIGURATION.ordinal,
                text = {
                    Text(
                        text = stringResource(R.string.tab_configuration)
                    )
                },
                selectedContentColor = MaterialTheme.colorScheme.primary,
                unselectedContentColor = MaterialTheme.colorScheme.onSurface,
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(LeagueScreen.CONFIGURATION.ordinal)
                    }
                })

            Tab(
                selected = pagerState.currentPage == LeagueScreen.STANDINGS.ordinal,
                text = {
                    Text(
                        text = stringResource(R.string.tab_clasification)
                    )
                },
                selectedContentColor = MaterialTheme.colorScheme.primary,
                unselectedContentColor = MaterialTheme.colorScheme.onSurface,
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(LeagueScreen.STANDINGS.ordinal)
                    }
                })

            Tab(
                selected = pagerState.currentPage == LeagueScreen.MATCHES.ordinal,
                text = {
                    Text(
                        text = stringResource(R.string.tab_matches)
                    )
                },
                selectedContentColor = MaterialTheme.colorScheme.primary,
                unselectedContentColor = MaterialTheme.colorScheme.onSurface,
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(LeagueScreen.MATCHES.ordinal)
                    }
                })
        }
        val modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = contentPadding.calculateStartPadding(LayoutDirection.Ltr),
                end = contentPadding.calculateEndPadding(LayoutDirection.Ltr),
                bottom = contentPadding.calculateBottomPadding()
            )

        HorizontalPager(
            modifier = Modifier.fillMaxWidth(),
            state = pagerState,
            userScrollEnabled = true
        ) { page ->
            when (page) {
                0 -> ConfigurationPageScreen(modifier, leagueViewModel)
                1 -> StandingsPageScreen(modifier, leaguesUiState)
                2 -> MatchesPageScreen(modifier, leagueViewModel)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LeagueTopBar(
    leagueName: String = "",
    leagueCode: String = "",
    onClickBack: () -> Unit = {},
    onClickLeagueCode: () -> Unit = {}
) {
    TopAppBar(
        title = { Text(text = leagueName) },
        navigationIcon = {
            Icon(
                modifier = Modifier.clickable { onClickBack() },
                painter = painterResource(id = R.drawable.ic_arrow_back),
                contentDescription = null
            )
        },
        actions = {
            Row(
                modifier = Modifier
                    .padding(end = 8.dp)
                    .clickable { onClickLeagueCode() },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    modifier = Modifier.padding(end = 8.dp),
                    text = leagueCode,
                    style = MaterialTheme.typography.labelMedium
                )
                Icon(
                    painter = painterResource(id = R.drawable.ic_content_copy),
                    contentDescription = null
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            actionIconContentColor = MaterialTheme.colorScheme.onSurfaceVariant
        )
    )
}