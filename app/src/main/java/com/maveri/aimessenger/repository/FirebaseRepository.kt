package com.maveri.aimessenger.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.ObservableEmitter
import io.reactivex.rxjava3.core.ObservableOnSubscribe
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject
import kotlin.random.Random

class FirebaseRepository @Inject constructor(
    private val firebaseDatabase: FirebaseDatabase,
    private val firebaseAuth: FirebaseAuth
) {

    companion object {
        const val DATABASE_ROOT_SEARCH = "search"
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

    fun startUserSearch(): Observable<String> {
        return Observable.create { emitter ->
            firebaseAuth.currentUser?.let {
                val databaseReference = firebaseDatabase.getReference(DATABASE_ROOT_SEARCH)
                databaseReference.child(it.uid).setValue("true")
                databaseReference.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val keys = (snapshot.value as HashMap<String, String>)
                            .filterKeys { key -> key != it.uid }.keys.toList()
                        if (keys.isNotEmpty()) {
                            if (keys.size == 1)
                                emitter.onNext(it.uid + " - " + keys[0])
                            else
                                emitter.onNext(it.uid + " - " + keys[Random.nextInt(0, keys.size - 1)])
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