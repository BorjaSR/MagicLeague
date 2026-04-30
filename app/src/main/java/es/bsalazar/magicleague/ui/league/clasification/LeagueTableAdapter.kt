package es.bsalazar.magicleague.ui.league.clasification

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import es.bsalazar.magicleague.R
import es.bsalazar.magicleague.models.PlayerLeague
import es.bsalazar.magicleague.utils.GradientHelper


class LeagueTableAdapter(private val players: List<PlayerLeague>) :
    RecyclerView.Adapter<LeagueTableViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeagueTableViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.item_player_league, parent, false);
        return LeagueTableViewHolder(v)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.context = recyclerView.context
    }

    override fun getItemCount() = players.size

    override fun onBindViewHolder(holder: LeagueTableViewHolder, position: Int) {
        val player = players[position]
        with(holder) {

            background.background = GradientHelper(context).createGradientForDeckList(player.colors)

            playerName.text = player.name
            playerPoints.text = player.points.toString()
            playerWins.text = player.wins.toString()
            playerDefeats.text = player.defeats.toString()
            playerTies.text = player.ties.toString()
            playerLifeGap.text = player.lifeGap.toString()
        }
    }
}

class LeagueTableViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var background: View = itemView.findViewById(R.id.background_colors)

    var playerName: TextView = itemView.findViewById(R.id.playerName)
    var playerPoints: TextView = itemView.findViewById(R.id.playerPoints)
    var playerWins: TextView = itemView.findViewById(R.id.playerWins)
    var playerDefeats: TextView = itemView.findViewById(R.id.playerDefeats)
    var playerTies: TextView = itemView.findViewById(R.id.playerTies)
    var playerLifeGap: TextView = itemView.findViewById(R.id.playerLifeGap)
}
