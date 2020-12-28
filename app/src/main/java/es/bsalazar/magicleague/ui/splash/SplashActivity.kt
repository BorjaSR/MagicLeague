package es.bsalazar.magicleague.ui.splash

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import es.bsalazar.magicleague.MainActivity
import es.bsalazar.magicleague.R
import es.bsalazar.magicleague.data.SharedPreferences

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        SharedPreferences.getInstance(this).getUserName()?.let {
            startActivity(Intent(this, MainActivity::class.java))
        }

        findViewById<EditText>(R.id.et_player_name).requestFocus()
        findViewById<Button>(R.id.continue_button).setOnClickListener {
            val playerName = findViewById<EditText>(R.id.et_player_name).text.toString()
            if(SharedPreferences.getInstance(this).saveUserName(playerName))
                startActivity(Intent(this, MainActivity::class.java))
            this.finish()
        }
    }
}