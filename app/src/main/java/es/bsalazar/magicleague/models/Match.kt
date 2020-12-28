package es.bsalazar.magicleague.models

import es.bsalazar.magicleague.utils.Constants

class Match(
    val player1: MatchPlayer = MatchPlayer(),
    val player2: MatchPlayer = MatchPlayer(),
    var startingPlayer: Int = 1,
    var state: Constants.MATCH_STATE = Constants.MATCH_STATE.PENDING
)