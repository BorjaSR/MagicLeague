package es.bsalazar.magicleague.data

import android.content.Context
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import es.bsalazar.magicleague.models.Deck
import java.util.*
import kotlin.collections.ArrayList

class SharedPreferences(val context: Context) {

    private val MAGIC_LEAGUE_PREFERENCES = "MagicLeaguePreferences"
    private val USER_ID_KEY = "userIdKey"
    private val USER_NAME_KEY = "userNameKey"
    private val USER_DECKS_KEY = "userDecks"

    private val sharedPreferences get() = context.getSharedPreferences(MAGIC_LEAGUE_PREFERENCES, Context.MODE_PRIVATE)

    companion object {
        private var INSTANCE: SharedPreferences? = null

        fun getInstance(context: Context): SharedPreferences {
            if (INSTANCE == null)
                INSTANCE = SharedPreferences(context)
            return INSTANCE!!
        }
    }

    private fun saveUserID(userID: String): Boolean {
        return sharedPreferences.edit()
                .putString(USER_ID_KEY, userID)
                .commit()
    }

    fun getUserID(): String {
        return sharedPreferences.getString(USER_ID_KEY, null) ?: kotlin.run {
            val uuid = UUID.randomUUID().toString()
            saveUserID(uuid)
            return@run uuid
        }
    }

    fun saveUserName(userName: String): Boolean {
        return sharedPreferences.edit()
            .putString(USER_NAME_KEY, userName)
            .commit()
    }

    fun getUserName(): String? {
        return sharedPreferences.getString(USER_NAME_KEY, null)
    }

    fun saveDeck(deck: Deck): Boolean {
        val userDecks = getUserDecks()
        userDecks.add(deck)
        return sharedPreferences.edit()
            .putString(USER_DECKS_KEY, Gson().toJson(userDecks))
            .commit()
    }

    fun removeDeck(deck: Deck): Boolean {
        val userDecks = getUserDecks()
        for (userDeck in userDecks){
            if(userDeck.name == deck.name)
                userDecks.remove(userDeck)
        }

        return sharedPreferences.edit()
            .putString(USER_DECKS_KEY, Gson().toJson(userDecks))
            .commit()
    }

    fun getUserDecks(): ArrayList<Deck> {
        val itemType = object : TypeToken<ArrayList<Deck>>() {}.type
        return Gson().fromJson<List<Deck>>(sharedPreferences.getString(USER_DECKS_KEY, Gson().toJson(arrayListOf<Deck>())), itemType) as ArrayList<Deck>
    }
}