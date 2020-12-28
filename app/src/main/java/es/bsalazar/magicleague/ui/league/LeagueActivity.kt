package es.bsalazar.magicleague.ui.league

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.tabs.TabLayout
import androidx.viewpager.widget.ViewPager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import es.bsalazar.magicleague.R
import es.bsalazar.magicleague.ui.league.clasification.ClasificationFragment
import es.bsalazar.magicleague.ui.league.configuration.ConfigurationFragment
import es.bsalazar.magicleague.ui.league.matches.MatchesFragment
import es.bsalazar.magicleague.utils.Constants

class LeagueActivity : AppCompatActivity() {

    private lateinit var leagueViewModel: LeagueViewModel
    private var subtitleTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_league)

        leagueViewModel = ViewModelProvider(this).get(LeagueViewModel::class.java).apply {
            leagueID = intent.extras?.getString(Constants.LEAGUE_CODE_KEY)
        }

        val sectionsPagerAdapter =
            SectionsPagerAdapter(
                listOf(
                    Pair(getString(R.string.tab_configuration), ConfigurationFragment()),
                    Pair(getString(R.string.tab_clasification), ClasificationFragment()),
                    Pair(getString(R.string.tab_matches), MatchesFragment())
                ),
                supportFragmentManager
            )
        val viewPager: ViewPager = findViewById(R.id.view_pager)
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        tabs.setupWithViewPager(viewPager)

        subtitleTextView = findViewById(R.id.subtitle)

        setObservers()
        viewPager.currentItem = 1
    }

    private fun copyToClipBoard(text: String) {

        // Get the clipboard system service
        val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

        // Copy text to clipboard
        val clip = ClipData.newPlainText("LEAGUE CODE", text)
        clipboard.setPrimaryClip(clip)

        Toast.makeText(this, getString(R.string.text_copy_to_clipboard), Toast.LENGTH_SHORT).show()
    }

    private fun setObservers() {
        leagueViewModel.league.observe(this, Observer { league ->
            findViewById<TextView>(R.id.title).text = league.name
            league.id?.let { leagueID ->
                subtitleTextView?.text = leagueID
                subtitleTextView?.setOnClickListener { copyToClipBoard(leagueID) }
            }
        })
    }
}