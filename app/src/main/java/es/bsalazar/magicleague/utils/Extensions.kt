package es.bsalazar.magicleague.utils

import android.view.View
import java.math.RoundingMode
import java.text.DecimalFormat

fun View.visible() {
    this.visibility = View.VISIBLE
}

fun View.gone() {
    this.visibility = View.GONE
}

fun String.Companion.EMPTY() = ""

fun Double.roundOffDecimal(): Double {
    val df = DecimalFormat("#.##")
    df.roundingMode = RoundingMode.FLOOR
    return df.format(this).toDouble()
}
fun Float.roundOffDecimal(): Float {
    val df = DecimalFormat("#.##")
    df.roundingMode = RoundingMode.FLOOR
    return df.format(this).toFloat()
}