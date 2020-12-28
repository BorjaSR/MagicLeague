package es.bsalazar.magicleague.ui.leagues_list

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.RecyclerView
import es.bsalazar.magicleague.R
import es.bsalazar.magicleague.models.League
import es.bsalazar.magicleague.utils.Constants

class LeaguesAdapter(val leagues : List<League>, val onClick: (League) -> Unit = {}) : RecyclerView.Adapter<LeagueViewHolder>() {

    private var context: Context? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeagueViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_league, parent, false);
        return LeagueViewHolder(v)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        context = recyclerView.context
    }

    override fun getItemCount() = leagues.size

    override fun onBindViewHolder(holder: LeagueViewHolder, position: Int) {
        val league = leagues[position]
        with(holder){
            name.text = league.name
            state.text = when(league.state){
                Constants.LEAGUE_STATE.GETTING_ORGANIZED -> context?.getString(R.string.state_league_getting_organized)
                Constants.LEAGUE_STATE.IN_PROGRESS -> context?.getString(R.string.state_league_in_progress)
                Constants.LEAGUE_STATE.FINISHED -> context?.getString(R.string.state_league_finished)
            }

            state.setTextColor(when(league.state){
                Constants.LEAGUE_STATE.GETTING_ORGANIZED -> ContextCompat.getColorStateList(context!!, R.color.orange_400)
                Constants.LEAGUE_STATE.IN_PROGRESS -> ContextCompat.getColorStateList(context!!, R.color.mtg_green)
                Constants.LEAGUE_STATE.FINISHED -> ContextCompat.getColorStateList(context!!, R.color.mtg_red)
            })

            ViewCompat.setBackgroundTintList(stateIndicator, when(league.state){
                Constants.LEAGUE_STATE.GETTING_ORGANIZED -> ContextCompat.getColorStateList(context!!, R.color.orange_400)
                Constants.LEAGUE_STATE.IN_PROGRESS -> ContextCompat.getColorStateList(context!!, R.color.mtg_green)
                Constants.LEAGUE_STATE.FINISHED -> ContextCompat.getColorStateList(context!!, R.color.mtg_red)
            })

            container.setOnClickListener { onClick(league) }
        }
    }
}

class LeagueViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var name: TextView = itemView.findViewById(R.id.league_name)
    var state: TextView = itemView.findViewById(R.id.league_state)
    var stateIndicator: View = itemView.findViewById(R.id.league_state_indicator)
    var container: LinearLayout = itemView.findViewById(R.id.container)
}