package com.maveri.aimessenger.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.maveri.aimessenger.main.viewmodel.MainViewState
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject
import kotlin.random.Random

class FirebaseRepository @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase,
    private val firebaseAuth: FirebaseAuth
) {

    companion object {
        const val DATABASE_ROOT_ROOMS = "search"

        const val MIN_RANDOM_NUMBER = 0
        const val MAX_RANDOM_NUMBER = 100000
     }

    fun signInAnonymously(): Completable {
        return Completable.create { emitter ->
            firebaseAuth.signInAnonymously()
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        emitter.onComplete()
                    } else {
                        emitter.onError(it.exception)
                    }
                }
        }.subscribeOn(Schedulers.single());
    }

    fun startUserSearch(): Observable<MainViewState.Room> {
        return Observable.create { emitter ->
            firebaseAuth.currentUser?.let {
                val databaseReference = firebaseDatabase.getReference(DATABASE_ROOT_ROOMS)
                databaseReference.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val keys = (snapshot.value as HashMap<String, HashMap<*, *>>)
                            .filterValues { item -> item.size == 1 }.keys.toList()
                        if (keys.isNotEmpty()) {
                            databaseReference.child(keys[0]).child(it.uid).setValue("true")
                            databaseReference.removeEventListener(this)
                            emitter.onNext(MainViewState.Room(keys[0], false))
                        } else {
                            val personalRoomId = it.uid + Random.nextInt(MIN_RANDOM_NUMBER, MAX_RANDOM_NUMBER)
                            databaseReference.child(personalRoomId).child(it.uid).setValue("true")
                            databaseReference.removeEventListener(this)
                            emitter.onNext(MainViewState.Room(personalRoomId, true))
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        databaseReference.removeEventListener(this)
                        emitter.onError(error.toException())
                    }
                })

            }
        }
    }
}