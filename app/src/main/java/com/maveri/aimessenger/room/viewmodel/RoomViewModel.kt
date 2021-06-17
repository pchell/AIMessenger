package com.maveri.aimessenger.room.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maveri.aimessenger.model.Message
import com.maveri.aimessenger.model.Room
import com.maveri.aimessenger.repository.FirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.observers.DisposableCompletableObserver
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class RoomViewModel @Inject constructor(private val firebaseRepository: FirebaseRepository) :
    ViewModel() {
    val viewState: MutableLiveData<RoomViewState.State> = MutableLiveData()

    companion object {
        const val TAG = "RoomViewModel"
    }

    fun checkRoomChanges(roomId: String, isCheckRoomConnections: Boolean) {
        firebaseRepository.checkRoomChanges(roomId, isCheckRoomConnections)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSingleObserver<Room>() {
                override fun onSuccess(room: Room) {
                    viewState.value = RoomViewState.State(room = room)
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, e.stackTraceToString())
                }

            })
    }

    fun getRoomMessages(roomId: String) {
        firebaseRepository.getRoomMessages(roomId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<List<Message>> {
                override fun onSubscribe(item: Disposable) {
                }

                override fun onNext(message: List<Message>) {
                    viewState.value = RoomViewState.State(message = message)
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, e.stackTraceToString())
                }

                override fun onComplete() {

                }
            })
    }

    fun disconnectFromRoom(roomId: String) {
        firebaseRepository.disconnectFromRoom(roomId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSingleObserver<Room>() {
                override fun onSuccess(room: Room) {
                    viewState.value = RoomViewState.State(room = room)
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, e.stackTraceToString())
                }

            })
    }

    fun sendRoomMessage(roomId: String, message: String) {
        firebaseRepository.sendRoomMessage(roomId, message)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableCompletableObserver() {
                override fun onComplete() {
                    // TODO: necessary add correct processing logic after sending message
                    // viewState.value = RoomViewState.State()
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, e.stackTraceToString())
                }

            })
    }
}