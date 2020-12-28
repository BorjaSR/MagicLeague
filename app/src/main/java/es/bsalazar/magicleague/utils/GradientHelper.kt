package es.bsalazar.magicleague.utils

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import androidx.core.content.ContextCompat
import es.bsalazar.magicleague.R

class GradientHelper(val context: Context) {

    fun createGradientForDeckList(list: ArrayList<Constants.MTG_COLOR>): GradientDrawable {
        if(list.size == 1) list.add(list[0])
        return GradientDrawable(
            GradientDrawable.Orientation.LEFT_RIGHT,
            list.map { it.getIntColor() }.toIntArray()
        )
    }

    private fun Constants.MTG_COLOR.getIntColor(): Int = when (this) {
        Constants.MTG_COLOR.BLACK -> ContextCompat.getColor(context, R.color.mtg_black)
        Constants.MTG_COLOR.WHITE -> ContextCompat.getColor(context, R.color.mtg_white)
        Constants.MTG_COLOR.GREEN -> ContextCompat.getColor(context, R.color.mtg_green)
        Constants.MTG_COLOR.RED -> ContextCompat.getColor(context, R.color.mtg_red)
        Constants.MTG_COLOR.BLUE -> ContextCompat.getColor(context, R.color.mtg_blue)
        Constants.MTG_COLOR.COLORLESS -> ContextCompat.getColor(context, R.color.mtg_colorless)
    }
}