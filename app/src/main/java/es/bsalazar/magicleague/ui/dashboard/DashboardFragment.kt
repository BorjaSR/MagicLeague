package es.bsalazar.magicleague.ui.dashboard

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import es.bsalazar.magicleague.MainActivity
import es.bsalazar.magicleague.R
import es.bsalazar.magicleague.data.SharedPreferences
import es.bsalazar.magicleague.models.League
import es.bsalazar.magicleague.models.PlayerLeague
import es.bsalazar.magicleague.utils.Constants


class DashboardFragment : Fragment() {

    private lateinit var viewModel: DashboardViewModel
    val playerID: String? by lazy { context?.let { SharedPreferences.getInstance(it).getUserID() } }
    val playerName: String? by lazy {
        context?.let {
            SharedPreferences.getInstance(it).getUserName()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
        val joinLeague: TextView = root.findViewById(R.id.join_league)
        val createLeague: TextView = root.findViewById(R.id.createLeague)

        createLeague.setOnClickListener {
            CreateLeagueDialog(onClickCreate = { leagueName ->
                context?.let { context ->
                    viewModel.saveLeague(
                        League(
                            leagueName,
                            arrayListOf(SharedPreferences.getInstance(context).getUserID()),
                            arrayListOf(
                                PlayerLeague(
                                    SharedPreferences.getInstance(context).getUserID(),
                                    SharedPreferences.getInstance(context).getUserName().orEmpty()
                                )
                            )
                        )
                    )
                    (activity as MainActivity).navToLeagues()
                }
            }).show(childFragmentManager, "CREATE LEAGUE")

        }

        joinLeague.setOnClickListener {
            JoinLeagueDialog(onClickJoin = { leagueId ->
                context?.let {
                    viewModel.joinToLeague(
                        leagueId,
                        SharedPreferences.getInstance(it).getUserID(),
                        SharedPreferences.getInstance(it).getUserName().orEmpty()
                    )
                }
            }).show(childFragmentManager, "JOIN")
        }

        return root
    }
}