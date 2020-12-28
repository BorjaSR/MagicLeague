package es.bsalazar.magicleague.models

import com.google.firebase.firestore.Exclude
import es.bsalazar.magicleague.utils.Constants

data class League (
        var name: String = "",
        var players: ArrayList<String> = arrayListOf(),
        var playersInfo: ArrayList<PlayerLeague> = arrayListOf(),
        @get:Exclude  var id: String? = null,
        var state: Constants.LEAGUE_STATE = Constants.LEAGUE_STATE.GETTING_ORGANIZED,
        var laps: Int = 2,
        var matches: ArrayList<Match> = arrayListOf()
)