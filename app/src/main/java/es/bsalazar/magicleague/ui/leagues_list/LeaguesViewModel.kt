package es.bsalazar.magicleague.ui.leagues_list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import es.bsalazar.magicleague.data.Firestore
import es.bsalazar.magicleague.models.League

class LeaguesViewModel : ViewModel() {

    var leaguesList = MutableLiveData<List<League>>()

    fun getMyLeagues(userID: String) {
        Firestore.instance.getMyLeagues(userID) { leaguesList.value = it }
    }
}