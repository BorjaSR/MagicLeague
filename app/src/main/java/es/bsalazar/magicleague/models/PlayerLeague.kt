package es.bsalazar.magicleague.models

import es.bsalazar.magicleague.utils.Constants

data class PlayerLeague(
    var id : String = "",
    var name : String = "",
    var points: Int = 0,
    var wins: Int = 0,
    var defeats: Int = 0,
    var ties: Int = 0,
    var lifeGap: Int = 0,
    var colors: ArrayList<Constants.MTG_COLOR> = arrayListOf()
)