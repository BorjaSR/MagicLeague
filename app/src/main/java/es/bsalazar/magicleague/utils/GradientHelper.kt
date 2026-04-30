package es.bsalazar.magicleague.utils

import android.content.Context
import androidx.compose.ui.graphics.Color
import android.graphics.drawable.GradientDrawable
import androidx.compose.ui.graphics.Brush
import androidx.core.content.ContextCompat
import es.bsalazar.magicleague.R
import es.bsalazar.magicleague.ui.theme.MtgBlack
import es.bsalazar.magicleague.ui.theme.MtgBlue
import es.bsalazar.magicleague.ui.theme.MtgColorless
import es.bsalazar.magicleague.ui.theme.MtgGreen
import es.bsalazar.magicleague.ui.theme.MtgRed
import es.bsalazar.magicleague.ui.theme.MtgWhite
import es.bsalazar.magicleague.ui.theme.Translucent

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


fun createBrushForDeckList(
    colorList: ArrayList<Constants.MTG_COLOR>,
    startTransparency: Boolean = false,
    endTransparency: Boolean = false,
    startOffset: Float = 0f,
    endOffset: Float = 0f
): Brush {
    if (colorList.size == 1) colorList.add(colorList[0])

    val finalColors = colorList.map { it.getColor() }.toMutableList()

    if (startTransparency || startOffset > 0f) finalColors.add(0, Translucent)
    if (endTransparency || endOffset > 0f) finalColors.add(Translucent)

    val finalColorStops = mutableListOf<Pair<Float, Color>>()

    var myStartOffset = startOffset
    var myEndOffset = endOffset
    if ((startOffset + endOffset) > 1f) {
        myStartOffset = 0f
        myEndOffset = 0f
    }

    val colorStops = ((1f - myStartOffset - myEndOffset) / (finalColors.size - 1)).roundOffDecimal()
    var actualStop = myStartOffset
    finalColors.forEachIndexed { index, color ->
        finalColorStops.add(
            Pair(actualStop, color)
        )
        actualStop += colorStops
    }

    return Brush.horizontalGradient(
        colorStops = finalColorStops.toTypedArray()
    )
}

private fun Constants.MTG_COLOR.getColor(): Color = when (this) {
    Constants.MTG_COLOR.BLACK -> MtgBlack
    Constants.MTG_COLOR.WHITE -> MtgWhite
    Constants.MTG_COLOR.GREEN -> MtgGreen
    Constants.MTG_COLOR.RED -> MtgRed
    Constants.MTG_COLOR.BLUE -> MtgBlue
    Constants.MTG_COLOR.COLORLESS -> MtgColorless
}