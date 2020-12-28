package es.bsalazar.magicleague.ui.profile

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import es.bsalazar.magicleague.MainActivity
import es.bsalazar.magicleague.R
import es.bsalazar.magicleague.data.SharedPreferences

class ProfileFragment : Fragment() {

    private lateinit var profileViewModel: ProfileViewModel
    private var editPlayerName: EditText? = null
    private var recyclerView: RecyclerView? = null
    private var adapter: DecksAdapter? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        profileViewModel = ViewModelProvider(this).get(ProfileViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_profile, container, false)

        editPlayerName = root.findViewById(R.id.et_player_name)
        recyclerView = root.findViewById(R.id.recycler_decks)


        SharedPreferences.getInstance(requireContext()).getUserName()?.let { editPlayerName?.setText(it) }
        adapter = DecksAdapter(SharedPreferences.getInstance(requireContext()).getUserDecks()) {
            AlertDialog.Builder(requireContext())
                .setTitle("¿Eliminar mazo?")
                .setPositiveButton(android.R.string.yes) { _, _ ->
                    SharedPreferences.getInstance(requireContext()).removeDeck(it)
                    adapter?.decks = SharedPreferences.getInstance(requireContext()).getUserDecks()
                    adapter?.notifyDataSetChanged()
                }
                .show()
        }
        recyclerView?.adapter = adapter

        root.findViewById<Button>(R.id.add_deck_button).setOnClickListener {
            CreateDeckDialog {
                SharedPreferences.getInstance(requireContext()).saveDeck(it)
                adapter?.decks = SharedPreferences.getInstance(requireContext()).getUserDecks()
                adapter?.notifyDataSetChanged()
            }.show(childFragmentManager, "CREATE DECK")
        }

        root.findViewById<Button>(R.id.save_button).setOnClickListener {
            val playerName = editPlayerName?.text.toString()
            if (SharedPreferences.getInstance(requireContext()).saveUserName(playerName))
                startActivity(Intent(activity, MainActivity::class.java))
        }

        return root
    }
}