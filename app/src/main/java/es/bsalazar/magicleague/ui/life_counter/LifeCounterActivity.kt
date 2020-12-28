package es.bsalazar.magicleague.ui.life_counter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.transition.TransitionManager
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import es.bsalazar.magicleague.R
import es.bsalazar.magicleague.utils.Variables
import es.bsalazar.magicleague.utils.gone
import es.bsalazar.magicleague.utils.visible

class LifeCounterActivity : AppCompatActivity() {

    lateinit var player1_container: ConstraintLayout
    lateinit var player1_name: TextView
    lateinit var player1_minus: TextView
    lateinit var player1_plus: TextView
    lateinit var player1_lifes: TextView
    lateinit var player1_difference: TextView
    lateinit var player1_color_change_button: ImageView

    lateinit var viewModel: LifeCounterViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_life_counter)

        viewModel = ViewModelProvider(this).get(LifeCounterViewModel::class.java)

        viewModel.lives1 = 20
        viewModel.poisonCounters1 = 0

        player1_container = findViewById(R.id.player1_container)
        player1_name = findViewById(R.id.player1_name)
        player1_minus = findViewById(R.id.player1_minus)
        player1_plus = findViewById(R.id.player1_plus)
        player1_lifes = findViewById(R.id.player1_lifes)
        player1_difference = findViewById(R.id.player1_life_difference)
        player1_color_change_button = findViewById(R.id.player1_color_change_button)

        player1_minus.setOnClickListener { viewModel.removeLife1() }
        player1_plus.setOnClickListener { viewModel.addLife1() }


        Variables.matchOfLifeCounter?.let {
            player1_name.visible()
            player1_name.text = it.second.player1.playerName

            viewModel.lives1 = it.second.player1.lifes
            player1_color_change_button.gone()
        } ?: kotlin.run {
        }

        setObservers()
    }

    fun setObservers() {
        viewModel.lives1LiveData.observe(this, Observer { player1_lifes.text = it.toString() })
        viewModel.difference1LiveData.observe(this, Observer {
//            TransitionManager.beginDelayedTransition(player1_container)
            if (it == null) player1_difference.gone()
            else {
                player1_difference.visible()
                player1_difference.text = if(it > 0) "+$it" else it.toString()
            }
        })
    }
}