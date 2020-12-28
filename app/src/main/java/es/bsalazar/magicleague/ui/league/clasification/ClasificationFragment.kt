package es.bsalazar.magicleague.ui.league.clasification

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import es.bsalazar.magicleague.R
import es.bsalazar.magicleague.ui.league.LeagueViewModel

/**
 * A placeholder fragment containing a simple view.
 */
class ClasificationFragment : Fragment() {

    private lateinit var leagueViewModel: LeagueViewModel
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        leagueViewModel = ViewModelProvider(requireActivity()).get(LeagueViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_clasification, container, false)
        recyclerView = root.findViewById(R.id.players_league)
        recyclerView.addItemDecoration(
            DividerItemDecoration(
                context,
                LinearLayoutManager.VERTICAL
            )
        )
        setObservers()
        return root
    }

    private fun setObservers() {
        leagueViewModel.league.observe(requireActivity(), Observer { league ->
            recyclerView.adapter = LeagueTableAdapter(league.playersInfo.sortedByDescending { it.Points })
        })
    }
}