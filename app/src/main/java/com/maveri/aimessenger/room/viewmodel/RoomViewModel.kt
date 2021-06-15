package com.maveri.aimessenger.room.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maveri.aimessenger.main.viewmodel.MainViewState
import com.maveri.aimessenger.model.Message
import com.maveri.aimessenger.model.Room
import com.maveri.aimessenger.repository.FirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class RoomViewModel @Inject constructor(private val firebaseRepository: FirebaseRepository): ViewModel() {
    val viewState: MutableLiveData<RoomViewState.State> = MutableLiveData()

    fun checkRoomChanges(roomId: String, isCheckRoomConnections: Boolean) {
        firebaseRepository.checkRoomChanges(roomId, isCheckRoomConnections)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Room> {
                override fun onSubscribe(item: Disposable) {
                }

                override fun onNext(room: Room) {
                    viewState.value = RoomViewState.State(room = room)
                }

                override fun onError(e: Throwable) {

                }

                override fun onComplete() {

                }
            })
    }

    fun getRoomMessages(roomId: String, isCheckRoomConnections: Boolean) {
        firebaseRepository.getRoomMessages(roomId, isCheckRoomConnections)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Message> {
                override fun onSubscribe(item: Disposable) {
                }

                override fun onNext(message: Message) {
                    viewState.value = RoomViewState.State(message = message)
                }

                override fun onError(e: Throwable) {

                }

                override fun onComplete() {

                }
            })
    }

    fun disconnectFromRoom(roomId: String) {
        firebaseRepository.disconnectFromRoom(roomId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Room> {
                override fun onSubscribe(item: Disposable) {
                }

                override fun onNext(room: Room) {
                    viewState.value = RoomViewState.State(room = room)
                }

                override fun onError(e: Throwable) {

                }

                override fun onComplete() {

                }
            })
    }

    fun sendRoomMessage(roomId: String, isCheckRoomConnections: Boolean, message: String) {
        firebaseRepository.sendRoomMessage(roomId, isCheckRoomConnections, message)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Message> {
                override fun onSubscribe(item: Disposable) {
                }

                override fun onNext(message: Message) {
                    viewState.value = RoomViewState.State(message = message)
                }

                override fun onError(e: Throwable) {

                }

                override fun onComplete() {

                }
            })
    }
}