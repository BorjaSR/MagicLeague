package es.bsalazar.magicleague.ui.league.configuration

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import es.bsalazar.magicleague.R
import es.bsalazar.magicleague.data.Firestore
import es.bsalazar.magicleague.ui.league.LeagueViewModel
import es.bsalazar.magicleague.utils.Constants
import es.bsalazar.magicleague.utils.MatchesCreator
import es.bsalazar.magicleague.utils.gone
import es.bsalazar.magicleague.utils.visible

class ConfigurationFragment : Fragment() {


    private lateinit var leagueViewModel: LeagueViewModel

    private lateinit var etLeagueName: EditText
    private lateinit var etLeagueLaps: EditText
    private lateinit var lapsLeagueSaved: TextView


    private lateinit var radioGroup: RadioGroup
    private lateinit var radioLaps: RadioButton
    private lateinit var radioInfinite: RadioButton
    private lateinit var saveButton: Button

    private var laps: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        leagueViewModel = ViewModelProvider(requireActivity()).get(LeagueViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_configuration, container, false)

        lapsLeagueSaved = root.findViewById(R.id.laps_league_saved)
        etLeagueName = root.findViewById(R.id.et_league_name)
        etLeagueLaps = root.findViewById(R.id.et_league_laps)

        radioGroup = root.findViewById(R.id.radio_group)
        radioLaps = root.findViewById(R.id.radioButton)
        radioInfinite = root.findViewById(R.id.radioButton2)

        saveButton = root.findViewById(R.id.start_league_button)

        radioGroup.setOnCheckedChangeListener { _, _ ->
            when (radioLaps.isChecked) {
                true -> {
                    etLeagueLaps.isEnabled = true
                    etLeagueLaps.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.white
                        )
                    )
                    etLeagueLaps.requestFocus()
                    laps =
                        if (etLeagueLaps.text.toString().isNotEmpty()) etLeagueLaps.text.toString()
                            .toInt() else 0
                }
                false -> {
                    etLeagueLaps.setTextColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.grey_400
                        )
                    )
                    etLeagueLaps.isEnabled = false
                    laps = -1
                }
            }
        }
        setObservers()

        saveButton.setOnClickListener {

            if (leagueViewModel.league.value?.state == Constants.LEAGUE_STATE.GETTING_ORGANIZED) {
                if (radioLaps.isChecked) {
                    laps =
                        if (etLeagueLaps.text.toString().isNotEmpty()) etLeagueLaps.text.toString()
                            .toInt() else 0
                }

                leagueViewModel.startLeague(laps)
                
            } else {
                val league = leagueViewModel.league.value
                league?.let {
                    it.name = etLeagueName.text.toString()
                    leagueViewModel.updateLeague(it)
                }
            }
        }

        return root
    }

    private fun setObservers() {
        leagueViewModel.league.observe(requireActivity(), Observer { league ->
            etLeagueName.setText(league.name)

            if (league.state == Constants.LEAGUE_STATE.GETTING_ORGANIZED) {
                lapsLeagueSaved.gone()
                etLeagueLaps.visible()
                radioGroup.visible()

                if (league.laps != -1) {
                    etLeagueLaps.setText(league.laps.toString())
                    radioLaps.isChecked = true
                } else {
                    etLeagueLaps.setText("2")
                    radioInfinite.isChecked = true
                }
            } else {
                saveButton.text = getString(R.string.save_changes)

                lapsLeagueSaved.visible()
                etLeagueLaps.gone()
                radioGroup.gone()

                if (league.laps == -1) lapsLeagueSaved.text = "Indefinida"
                else lapsLeagueSaved.text = league.laps.toString()
            }
        })
    }
}