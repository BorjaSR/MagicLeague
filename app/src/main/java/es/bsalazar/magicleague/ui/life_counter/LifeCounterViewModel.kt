package es.bsalazar.magicleague.ui.life_counter

import android.os.CountDownTimer
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LifeCounterViewModel : ViewModel() {

    val lives1LiveData = MutableLiveData<Int>()
    val poisonCounters1LiveData = MutableLiveData<Int>()
    val difference1LiveData = MutableLiveData<Int?>()

    var lives1: Int = 20
        set(value) {
            lives1LiveData.value = value
            field = value
        }
    var poisonCounters1: Int = 0
        set(value) {
            poisonCounters1LiveData.value = value
            field = value
        }
    var difference1: Int? = null
        set(value) {
            countdownTimer1.start()
            difference1LiveData.value = value
            field = value
        }


    var countdownTimer1: CountDownTimer = object : CountDownTimer(2000, 1000) {
        override fun onFinish() {
            difference1 = null
        }

        override fun onTick(millisUntilFinished: Long) {
            // not used
        }
    }

    fun addLife1() {
        lives1++
        difference1 = (difference1 ?: 0) + 1
    }

    fun removeLife1() {
        lives1--
        difference1 = (difference1 ?: 0) - 1
    }

    fun addPoisonCounter() {
        poisonCounters1++
        difference1 = (difference1 ?: 0) + 1
    }

    fun removePoisonCounter() {
        poisonCounters1--
        difference1 = (difference1 ?: 0) - 1
    }


//    fun getLeague(leagueID: String) {
//        Firestore.instance.getRealTimeLeague(leagueID) {
//            league.value = it
//        }
//    }
}