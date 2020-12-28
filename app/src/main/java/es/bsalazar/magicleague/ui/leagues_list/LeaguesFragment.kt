package es.bsalazar.magicleague.ui.leagues_list

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import es.bsalazar.magicleague.R
import es.bsalazar.magicleague.data.SharedPreferences
import es.bsalazar.magicleague.ui.league.LeagueActivity
import es.bsalazar.magicleague.utils.Constants

class LeaguesFragment : Fragment() {

    private lateinit var viewModel: LeaguesViewModel
    var leaguesList: RecyclerView? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(LeaguesViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)

        setObservers()

        leaguesList = root.findViewById(R.id.leaguesList)

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        context?.let {
            viewModel.getMyLeagues(SharedPreferences.getInstance(it).getUserID())
        }
    }

    private fun setObservers() {
        viewModel.leaguesList.observe(viewLifecycleOwner, Observer {
            leaguesList?.adapter = LeaguesAdapter(it) { league ->
                val intent = Intent(context, LeagueActivity::class.java)
                intent.putExtra(Constants.LEAGUE_CODE_KEY, league.id)
                startActivity(intent)
            }
        })
    }
}