package es.bsalazar.magicleague.utils

import es.bsalazar.magicleague.models.Match
import es.bsalazar.magicleague.models.MatchPlayer
import es.bsalazar.magicleague.models.PlayerLeague

class MatchesCreator {

    companion object {
        fun crateMatch(players: ArrayList<PlayerLeague>, laps: Int): ArrayList<Match> {
            val matches: ArrayList<Match> = arrayListOf()

            players.forEachIndexed { firstIndex, firstPlayerLeague ->
                players.forEachIndexed { secondIndex, secondPlayerLeague ->
                    if (secondIndex > firstIndex) {
                        matches.add(
                            Match(
                                MatchPlayer(firstPlayerLeague.id, firstPlayerLeague.Name, 20),
                                MatchPlayer(secondPlayerLeague.id, secondPlayerLeague.Name, 20)
                            )
                        )
                    }
                }
            }

            matches.shuffle()

            val lapsMatches: ArrayList<Match> = arrayListOf()
            if (laps > 1) {
                for (i in 1..laps) {
                    for (match in matches) {
                        lapsMatches.add(
                            Match(
                                MatchPlayer(match.player1.playerID, match.player1.playerName, 20),
                                MatchPlayer(match.player2.playerID, match.player2.playerName, 20),
                                if(i == laps && laps % 2 != 0) -1 else i % 2
                            )
                        )
                    }
                }
            }

            return lapsMatches
        }
    }
}