package com.maveri.aimessenger.main.viewmodel

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
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val firebaseRepository: FirebaseRepository) :
    ViewModel() {

    val viewState: MutableLiveData<MainViewState.State> = MutableLiveData()

    fun signInAnonymously() {
        firebaseRepository.signInAnonymously().subscribe(object : DisposableCompletableObserver() {
            override fun onComplete() {
                viewState.value =
                    MainViewState.State(authStatus = MainViewState.AuthFirebaseStatus.Success)
            }

            override fun onError(e: Throwable?) {
                viewState.value =
                    MainViewState.State(authStatus = MainViewState.AuthFirebaseStatus.Error)
            }

        })
    }

    fun startUserSearch() {
        firebaseRepository.startUserSearch()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Room> {
                override fun onSubscribe(item: Disposable) {
                }

                override fun onNext(room: Room) {
                    viewState.value = MainViewState.State(room = room)
                }

                override fun onError(e: Throwable) {

                }

                override fun onComplete() {

                }
            })
    }

    fun checkRoomChanges(roomId: String, isCheckRoomConnections: Boolean) {
        firebaseRepository.checkRoomChanges(roomId, isCheckRoomConnections)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<Room> {
                override fun onSubscribe(item: Disposable) {
                }

                override fun onNext(room: Room) {
                    viewState.value = MainViewState.State(room = room)
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
                    viewState.value = MainViewState.State(room = room)
                }

                override fun onError(e: Throwable) {

                }

                override fun onComplete() {

                }
            })
    }


}