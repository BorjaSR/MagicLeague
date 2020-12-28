package es.bsalazar.magicleague.ui.dashboard

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.CountDownTimer
import android.transition.TransitionManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.DialogFragment
import com.google.android.material.card.MaterialCardView
import es.bsalazar.magicleague.R
import es.bsalazar.magicleague.data.SharedPreferences
import es.bsalazar.magicleague.utils.gone
import es.bsalazar.magicleague.utils.visible

class JoinLeagueDialog(val onClickJoin: (String) -> Unit) : DialogFragment() {

    private lateinit var cardView: MaterialCardView
    private lateinit var chooseDeckContainer: LinearLayout
    private lateinit var loading: LinearLayout
    private lateinit var searchLeagueContainer: LinearLayout
    private lateinit var spinner: Spinner

    private var timerFinish = false
    var timer: CountDownTimer = object : CountDownTimer(1500, 1000) {
        override fun onFinish() {
            timerFinish = true
            changeToLeagueFinded()
        }

        override fun onTick(millisUntilFinished: Long) {
            // not used
        }
    }

    override fun onActivityCreated(arg0: Bundle?) {
        super.onActivityCreated(arg0)
//        dialog?.window?.attributes?.windowAnimations = R.style.CommentsDialogAnimation
        dialog?.setCancelable(true)
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_join_league_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        cardView = view.findViewById(R.id.card_view)
        chooseDeckContainer = view.findViewById(R.id.choose_deck_container)
        loading = view.findViewById(R.id.loading)
        searchLeagueContainer = view.findViewById(R.id.search_league_container)
        spinner = view.findViewById(R.id.deck_spinner)

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                Toast.makeText(requireContext(), "pu8lsado", Toast.LENGTH_SHORT).show()
            }
        }

//        val adapter = DeckSpinnerAdapter(requireContext(), SharedPreferences.getInstance(requireContext()).getUserDecks())
        val adapter = DeckSpinnerAdapter2(requireContext(), SharedPreferences.getInstance(requireContext()).getUserDecks())
        spinner.adapter = adapter

        spinner.adapter = ArrayAdapter(requireContext(), R.layout.simple_spinner_item, SharedPreferences.getInstance(requireContext()).getUserDecks().map { it.name })

        view.findViewById<LinearLayout>(R.id.search_league_button).setOnClickListener {
            changeToLoading()
//            onClickJoin(view.findViewById<EditText>(R.id.et_league_code).text.toString())
        }

        view.findViewById<LinearLayout>(R.id.join_league_button).setOnClickListener {
            dismiss()
        }
    }

    private fun changeToLoading(){
        TransitionManager.beginDelayedTransition(cardView)
        searchLeagueContainer.gone()
        loading.visible()
        timer.start()
    }

    private fun changeToLeagueFinded(){
        TransitionManager.beginDelayedTransition(cardView)
        loading.gone()
        chooseDeckContainer.visible()
    }
}