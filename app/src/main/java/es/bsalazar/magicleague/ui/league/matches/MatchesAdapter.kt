package es.bsalazar.magicleague.ui.league.matches

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import es.bsalazar.magicleague.R
import es.bsalazar.magicleague.models.Match


class MatchesAdapter(private val matches: List<Match>, val onMatchClicked: (Match) -> Unit) :
    RecyclerView.Adapter<MatchesViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchesViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.item_match, parent, false);
        return MatchesViewHolder(v)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.context = recyclerView.context
    }

    override fun getItemCount() = matches.size

    override fun onBindViewHolder(holder: MatchesViewHolder, position: Int) {
        val match = matches[position]
        with(holder) {
            card.setOnClickListener { onMatchClicked(match) }

            player1.text = if(match.startingPlayer == 0) match.player1.playerName else match.player2.playerName
            player2.text = if(match.startingPlayer == 0) match.player2.playerName else match.player1.playerName
        }
    }
}

class MatchesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var card: ConstraintLayout = itemView.findViewById(R.id.card)
    var player1: TextView = itemView.findViewById(R.id.player1)
    var player2: TextView = itemView.findViewById(R.id.player2)
}
