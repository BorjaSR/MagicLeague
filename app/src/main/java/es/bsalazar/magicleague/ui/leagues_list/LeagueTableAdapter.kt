package es.bsalazar.magicleague.ui.leagues_list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import es.bsalazar.magicleague.R
import es.bsalazar.magicleague.models.PlayerLeague

class LeagueTableAdapter(val players : List<PlayerLeague>) : RecyclerView.Adapter<LeagueTableViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeagueTableViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_player_league, parent, false);
        return LeagueTableViewHolder(v)
    }

    override fun getItemCount() = players.size

    override fun onBindViewHolder(holder: LeagueTableViewHolder, position: Int) {
        val player = players[position]
        with(holder){
            playerName.text = player.name
            playerWins.text = player.wins.toString()
            playerDefeats.text = player.defeats.toString()
            playerLifeGap.text = player.lifeGap.toString()
        }
    }
}

class LeagueTableViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var playerName: TextView = itemView.findViewById(R.id.playerName)
    var playerWins: TextView = itemView.findViewById(R.id.playerWins)
    var playerDefeats: TextView = itemView.findViewById(R.id.playerDefeats)
    var playerLifeGap: TextView = itemView.findViewById(R.id.playerLifeGap)
}