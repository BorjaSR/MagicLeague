package es.bsalazar.magicleague.ui.profile

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import es.bsalazar.magicleague.R
import es.bsalazar.magicleague.models.Deck
import es.bsalazar.magicleague.models.Match
import es.bsalazar.magicleague.utils.GradientHelper


class DecksAdapter(var decks: List<Deck>, val onDeckLongClicked: (Deck) -> Unit) :
    RecyclerView.Adapter<DeckViewHolder>() {

    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DeckViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.item_deck, parent, false);
        return DeckViewHolder(v)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.context = recyclerView.context
    }

    override fun getItemCount() = decks.size

    override fun onBindViewHolder(holder: DeckViewHolder, position: Int) {
        val deck = decks[position]
        with(holder) {
            container.setOnLongClickListener {
                onDeckLongClicked(deck)
                true
            }
            deckColors.setBackgroundDrawable(GradientHelper(context).createGradientForDeckList(deck.colors))
            deckName.text = deck.name
        }
    }
}

class DeckViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var container: LinearLayout = itemView.findViewById(R.id.container)
    var deckColors: ImageView = itemView.findViewById(R.id.deck_colors)
    var deckName: TextView = itemView.findViewById(R.id.deck_name)
}
