package es.bsalazar.magicleague.models

import es.bsalazar.magicleague.utils.Constants

class Deck(
    val name: String = "",
    val colors: ArrayList<Constants.MTG_COLOR> = arrayListOf()
)