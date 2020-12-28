package es.bsalazar.magicleague.ui.league

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import es.bsalazar.magicleague.data.Firestore
import es.bsalazar.magicleague.models.League
import es.bsalazar.magicleague.utils.MatchesCreator

class LeagueViewModel : ViewModel() {

    val league = MutableLiveData<League>()
    var leagueID: String? = null
        set(value) {
            value?.let { getLeague(it) }
            field = value
        }

    fun getLeague(leagueID: String) {
        Firestore.instance.getRealTimeLeague(leagueID) {
            league.value = it
        }
    }

    fun startLeague(laps: Int){
        league.value?.let {
            Firestore.instance.startLeague(it, MatchesCreator.crateMatch(it.playersInfo, laps)){

            }
        }
    }

    fun updateLeague(league: League){
        Firestore.instance.updateLeague(league){

        }
    }
}