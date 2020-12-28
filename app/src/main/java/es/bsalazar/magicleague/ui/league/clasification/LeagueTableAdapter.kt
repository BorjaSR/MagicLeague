package es.bsalazar.magicleague.ui.league.clasification

import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import es.bsalazar.magicleague.R
import es.bsalazar.magicleague.models.PlayerLeague


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
//            GradientDrawable(
//                GradientDrawable.Orientation.LEFT_RIGHT, intArrayOf(
//                    ContextCompat.getColor(context, R.color.mtg_white),
//                    ContextCompat.getColor(context, R.color.mtg_white),
//                    ContextCompat.getColor(context, R.color.mtg_red),
//                    ContextCompat.getColor(context, R.color.mtg_red),
//                    ContextCompat.getColor(context, R.color.transucent)
//                )
//            )

            playerName.text = player.Name
            playerPoints.text = player.Points.toString()
            playerWins.text = player.Wins.toString()
            playerDefeats.text = player.Defeats.toString()
            playerTies.text = player.Ties.toString()
            playerLifeGap.text = player.LifeGap.toString()
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
