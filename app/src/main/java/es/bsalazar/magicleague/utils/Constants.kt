package es.bsalazar.magicleague.utils

class Constants {
    companion object {
        val LEAGUE_CODE_KEY = "LEAGUE_CODE_KEY"
    }

    enum class LEAGUE_STATE {
        GETTING_ORGANIZED,
        IN_PROGRESS,
        FINISHED
    }

    enum class MATCH_STATE {
        PENDING,
        IN_PROGRESS,
        FINISHED
    }

    enum class MTG_COLOR {
        WHITE,
        GREEN,
        RED,
        BLUE,
        BLACK,
        COLORLESS
    }
}