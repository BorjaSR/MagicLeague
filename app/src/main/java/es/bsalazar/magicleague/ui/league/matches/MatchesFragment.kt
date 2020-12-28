package es.bsalazar.magicleague.ui.league.matches

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import es.bsalazar.magicleague.R
import es.bsalazar.magicleague.ui.league.LeagueActivity
import es.bsalazar.magicleague.ui.league.LeagueViewModel
import es.bsalazar.magicleague.ui.life_counter.LifeCounterActivity
import es.bsalazar.magicleague.utils.Constants
import es.bsalazar.magicleague.utils.Variables
import es.bsalazar.magicleague.utils.gone
import es.bsalazar.magicleague.utils.visible

class MatchesFragment : Fragment() {


    private lateinit var leagueViewModel: LeagueViewModel

    private lateinit var recyclerView: RecyclerView
    private lateinit var leagueNotStarted: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        leagueViewModel = ViewModelProvider(requireActivity()).get(LeagueViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_matches, container, false)
        recyclerView = root.findViewById(R.id.recycler_matches)
        leagueNotStarted = root.findViewById(R.id.league_not_started)

        setObservers()

        return root
    }

    private fun setObservers() {
        leagueViewModel.league.observe(requireActivity(), Observer { league ->
            if(league.state == Constants.LEAGUE_STATE.IN_PROGRESS){
                leagueNotStarted.gone()
                recyclerView.adapter = MatchesAdapter(league.matches){
                    Variables.matchOfLifeCounter = Pair(league, it)
                    val intent = Intent(context, LifeCounterActivity::class.java)
                    startActivity(intent)
                }
            } else leagueNotStarted.visible()
        })
    }
}