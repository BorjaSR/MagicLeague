package es.bsalazar.magicleague.models

import es.bsalazar.magicleague.utils.Constants

data class PlayerLeague(
    var id : String = "",
    var Name : String = "",
    var Points: Int = 0,
    var Wins: Int = 0,
    var Defeats: Int = 0,
    var Ties: Int = 0,
    var LifeGap: Int = 0,
    var colors: ArrayList<Constants.MTG_COLOR> = arrayListOf()
)