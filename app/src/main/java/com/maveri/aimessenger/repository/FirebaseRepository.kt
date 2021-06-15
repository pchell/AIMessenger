package com.maveri.aimessenger.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.maveri.aimessenger.model.Message
import com.maveri.aimessenger.model.Room
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
        const val DATABASE_ROOT_ROOMS = "rooms"
        const val DATABASE_ROOT_USERS = "users"
        const val DATABASE_ROOT_MESSAGES = "message"

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

    fun startUserSearch(): Observable<Room> {
        return Observable.create { emitter ->
            firebaseAuth.currentUser?.let { user ->
                val databaseReference = firebaseDatabase.getReference(DATABASE_ROOT_ROOMS)
                databaseReference.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val keys = snapshot.value?.let { value ->
                            (value as HashMap<String, HashMap<*, *>>)
                                .filterValues { item ->
                                    item.size == 1 && (item[DATABASE_ROOT_USERS] as HashMap<*, *>).size == 1
                                }
                                .keys.toList()
                        } ?: emptyList()
                        databaseReference.removeEventListener(this)
                        if (keys.isNotEmpty()) {
                            databaseReference.child(keys[0]).child(DATABASE_ROOT_USERS)
                                .child(user.uid).setValue(true)
                            emitter.onNext(Room(keys[0], true))
                        } else {
                            val personalRoomId =
                                user.uid + Random.nextInt(MIN_RANDOM_NUMBER, MAX_RANDOM_NUMBER)
                            databaseReference.child(personalRoomId).child(DATABASE_ROOT_USERS)
                                .child(user.uid)
                                .setValue(true)
                            emitter.onNext(Room(personalRoomId))
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

    fun checkRoomChanges(roomId: String, isCheckRoomConnections: Boolean): Observable<Room> {
        return Observable.create { emitter ->
            firebaseAuth.currentUser?.let { user ->
                val databaseReference =
                    firebaseDatabase.getReference(DATABASE_ROOT_ROOMS).child(roomId)
                databaseReference.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.value != null) {
                            val keys =
                                (snapshot.value as HashMap<String, HashMap<*, *>>)[DATABASE_ROOT_USERS]?.let {
                                    it.filterKeys { item ->
                                        !item.equals(user.uid)
                                    }
                                }

                            if (!keys.isNullOrEmpty()) {
                                if (isCheckRoomConnections) {
                                    databaseReference.removeEventListener(this)
                                    emitter.onNext(Room(roomId, true))
                                }
                            } else if (!isCheckRoomConnections) {
                                databaseReference.removeEventListener(this)
                                emitter.onNext(Room(roomId, isDisconnect = true))
                            }
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

    fun disconnectFromRoom(roomId: String): Observable<Room> {
        return Observable.create { emitter ->
            firebaseAuth.currentUser?.let { user ->
                val databaseReference =
                    firebaseDatabase.getReference(DATABASE_ROOT_ROOMS).child(roomId)
                databaseReference.addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        if (snapshot.value != null) {
                            val keys =
                                (snapshot.value as HashMap<String, HashMap<*, *>>)[DATABASE_ROOT_USERS]?.let {
                                    it.filterKeys { item ->
                                        !item.equals(user.uid)
                                    }
                                }

                            databaseReference.removeEventListener(this)

                            if (keys.isNullOrEmpty()) {
                                databaseReference.removeValue()
                                emitter.onNext(Room(roomId, isDisconnect = true))
                            } else {
                                databaseReference.child(DATABASE_ROOT_USERS).child(user.uid)
                                    .removeValue()
                                emitter.onNext(Room(roomId, isDisconnect = true))
                            }
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

//    fun getRoomMessages(roomId: String, isCheckRoomConnections: Boolean): Observable<Message> {
//        return Observable.create { emitter ->
//            firebaseAuth.currentUser?.let { user ->
//                val databaseReference =
//                    firebaseDatabase.getReference(DATABASE_ROOT_ROOMS).child(roomId)
//                databaseReference.addValueEventListener(object : ValueEventListener {
//                    override fun onDataChange(snapshot: DataSnapshot) {
//                        if (snapshot.value != null) {
//                            val message =
//                                (snapshot.value as HashMap<String, HashMap<*, *>>)[DATABASE_ROOT_MESSAGES]?.let {
//                                    it.filterKeys { item ->
//                                        !item.equals(user.uid)
//                                    }
//                                }
//
//
//
//                            if (!message.isNullOrEmpty()) {
//                                if (isCheckRoomConnections) {
//                                    databaseReference.removeEventListener(this)
//                                    emitter.onNext(Room(roomId, true))
//                                }
//                            } else if (!isCheckRoomConnections) {
//                                databaseReference.removeEventListener(this)
//                                emitter.onNext(Room(roomId, isDisconnect = true))
//                            }
//                        }
//                    }
//
//                    override fun onCancelled(error: DatabaseError) {
//                        databaseReference.removeEventListener(this)
//                        emitter.onError(error.toException())
//                    }
//                })
//
//            }
//        }
//    }

    fun sendRoomMessage(roomId: String, isCheckRoomConnections: Boolean, message: String): Observable<Message> {
        return Observable.create { emitter ->
            firebaseAuth.currentUser?.let { user ->
                val databaseReference =
                    firebaseDatabase.getReference(DATABASE_ROOT_ROOMS).child(roomId)
                            databaseReference.child(DATABASE_ROOT_MESSAGES).child(user.uid).push().setValue(message)
                            emitter.onNext(Message(user.uid, message))
            }
        }
    }
}