package es.bsalazar.magicleague.ui.dashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import es.bsalazar.magicleague.R
import es.bsalazar.magicleague.models.Deck
import es.bsalazar.magicleague.utils.GradientHelper


class DeckSpinnerAdapter(val context: Context, val decks: ArrayList<Deck>) : BaseAdapter() {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val view = inflater.inflate(R.layout.item_deck, null);

        val deck_name = view?.findViewById<TextView>(R.id.deck_name)
        val deck_colors = view?.findViewById<ImageView>(R.id.deck_colors)

        deck_name?.setTextColor(ContextCompat.getColor(context, R.color.charcoal))
        deck_name?.text = decks[position].name
        deck_colors?.setBackgroundDrawable(GradientHelper(context).createGradientForDeckList(decks[position].colors))

        return view
    }

    override fun getItem(position: Int) = decks[position]

    override fun getItemId(position: Int) = position.toLong()

    override fun getCount() = decks.size

    private class ItemHolder(row: View?) {

        val deck_name: TextView = row?.findViewById(R.id.deck_name) as TextView
        val deck_colors: ImageView = row?.findViewById(R.id.deck_colors) as ImageView
    }
}