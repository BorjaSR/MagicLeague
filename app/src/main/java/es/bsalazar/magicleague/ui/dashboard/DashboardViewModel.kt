package es.bsalazar.magicleague.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.bsalazar.magicleague.data.Firestore
import es.bsalazar.magicleague.models.League

class DashboardViewModel : ViewModel() {

    fun saveLeague(league: League) {
        Firestore.instance.saveLeague(league, {

        })
    }

    fun joinToLeague(leagueId: String, playerId: String, playerName: String) {
        Firestore.instance.addPlayerToLeague(playerId, playerName, leagueId){

        }
    }
}