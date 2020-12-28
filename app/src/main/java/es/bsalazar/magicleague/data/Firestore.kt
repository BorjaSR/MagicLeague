package es.bsalazar.magicleague.data

import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import es.bsalazar.magicleague.models.League
import es.bsalazar.magicleague.models.Match
import es.bsalazar.magicleague.models.PlayerLeague
import es.bsalazar.magicleague.utils.Constants

class Firestore {

    private val LEAGUES_COLLECTION = "Leagues"

    private val db get() = FirebaseFirestore.getInstance()

    companion object {
        val instance: Firestore by lazy { return@lazy Firestore() }
    }

    fun getLeagues(success: (List<League>) -> Unit) {
        db.collection(LEAGUES_COLLECTION)
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                if (querySnapshot != null) {
                    success(querySnapshot.documents.map {
                        val league = it.toObject(League::class.java)
                        league?.id = it.id
                        league
                    }.filterNotNull())
                }
            }
    }

    fun getMyLeagues(userID: String, success: (List<League>) -> Unit) {
        db.collection(LEAGUES_COLLECTION)
            .whereArrayContains("players", userID)
            .addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                if (querySnapshot != null) {
                    success(querySnapshot.documents.map {
                        val league = it.toObject(League::class.java)
                        league?.id = it.id
                        league
                    }.filterNotNull())
                }
            }
    }

    fun saveLeague(league: League, success: (League) -> Unit, failure: () -> Unit = {}) {
        db.collection(LEAGUES_COLLECTION)
            .add(league)
            .addOnSuccessListener {
                league.id = it.id
                success(league)
            }
            .addOnFailureListener {
                failure()
            }
    }

    fun getLeague(leagueId: String, callback: (League?) -> Unit) {
        db.collection(LEAGUES_COLLECTION)
            .document(leagueId)
            .get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    task.result?.let { document ->
                        val league = document.toObject(League::class.java)
                        league?.id = document.id
                        callback(league)
                    } ?: kotlin.run {
                        callback(null)
                    }
                } else {
                    callback(null)
                }
            }
    }

    fun getRealTimeLeague(leagueId: String, callback: (League?) -> Unit) {
        db.collection(LEAGUES_COLLECTION)
            .document(leagueId)
            .addSnapshotListener { snapshot, e ->
                if (snapshot != null && snapshot.exists()) {
                    val league = snapshot.toObject(League::class.java)
                    league?.id = snapshot.id
                    callback(league)
                } else callback(null)
            }
    }

    fun startLeague(league: League, matches: ArrayList<Match>, listener: (Boolean) -> Unit) {
        league.state = Constants.LEAGUE_STATE.IN_PROGRESS
        league.matches = matches
        db.collection(LEAGUES_COLLECTION).document(league.id.orEmpty())
            .set(league)
            .addOnSuccessListener { listener(true) }
            .addOnFailureListener { listener(false) }
    }


    fun updateLeague(league: League, listener: (Boolean) -> Unit) {
        db.collection(LEAGUES_COLLECTION).document(league.id.orEmpty())
            .set(league)
            .addOnSuccessListener { listener(true) }
            .addOnFailureListener { listener(false) }
    }

    fun addPlayerToLeague(
        playerID: String,
        playerName: String?,
        leagueID: String,
        listener: (Boolean) -> Unit
    ) {
        getLeague(leagueID) { leagueNullable ->
            leagueNullable?.let { league ->
                val playerLeague = PlayerLeague(playerID, playerName.orEmpty())
                league.players.add(playerID)
                league.playersInfo.add(playerLeague)
                db.collection(LEAGUES_COLLECTION).document(league.id.orEmpty())
                    .set(league)
                    .addOnSuccessListener { listener(true) }
                    .addOnFailureListener { listener(false) }
            }
        }
    }
}