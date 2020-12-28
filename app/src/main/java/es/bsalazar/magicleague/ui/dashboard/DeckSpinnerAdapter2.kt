package es.bsalazar.magicleague.ui.dashboard

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import es.bsalazar.magicleague.R
import es.bsalazar.magicleague.models.Deck
import es.bsalazar.magicleague.utils.GradientHelper

class DeckSpinnerAdapter2(context: Context?, val decks: ArrayList<Deck>) :
    ArrayAdapter<Deck>(context!!, 0, decks) {



    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    private fun initView(position: Int, convertView: View?, parent: ViewGroup): View {
        // It is used to set our custom view.

        val viewNotNull = convertView?.let { view ->
            return@let view
        } ?: kotlin.run {
            return@run LayoutInflater.from(context).inflate(R.layout.item_deck, parent, false)
        }

//        var convertView = convertView
//        if (convertView == null) {
//            convertView =
//                LayoutInflater.from(context).inflate(R.layout.item_deck, parent, false)
//        }


        val deck_name = viewNotNull.findViewById<TextView>(R.id.deck_name)
        val deck_colors = viewNotNull.findViewById<ImageView>(R.id.deck_colors)

        deck_name?.setTextColor(ContextCompat.getColor(context, R.color.charcoal))
        deck_name?.text = decks[position].name
        deck_colors?.setBackgroundDrawable(GradientHelper(context).createGradientForDeckList(decks[position].colors))



//        val textViewName = convertView.findViewById<TextView>(R.id.text_view)
//        val currentItem: AlgorithmItem? = getItem(position)
//
//        // It is used the name to the TextView when the
//        // current item is not null.
//        if (currentItem != null) {
//            textViewName.setText(currentItem.getAlgorithmName())
//        }
        return viewNotNull
    }
}