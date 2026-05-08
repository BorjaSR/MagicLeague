package es.bsalazar.magicleague.ui.navigation

import kotlinx.serialization.Serializable


@Serializable
object DashboardScreen

@Serializable
data class League(val id: String)

@Serializable
object Leagues

@Serializable
object Dashboard

@Serializable
object Profile