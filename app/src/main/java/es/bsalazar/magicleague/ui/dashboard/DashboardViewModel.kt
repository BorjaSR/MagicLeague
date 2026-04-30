package es.bsalazar.magicleague.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.bsalazar.magicleague.data.Firestore
import es.bsalazar.magicleague.models.League
import es.bsalazar.magicleague.models.PlayerLeague

class DashboardViewModel : ViewModel() {

    fun saveLeague(league: League) {
        Firestore.instance.saveLeague(league, {

        })
    }

    fun joinToLeague(leagueId: String, playerLeague: PlayerLeague) {
        Firestore.instance.addPlayerToLeague(playerLeague, leagueId) {

        }
    }
}