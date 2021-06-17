package com.maveri.aimessenger.main.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maveri.aimessenger.model.Room
import com.maveri.aimessenger.repository.FirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.observers.DisposableCompletableObserver
import io.reactivex.rxjava3.observers.DisposableSingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val firebaseRepository: FirebaseRepository) :
    ViewModel() {

    val viewState: MutableLiveData<MainViewState.State> = MutableLiveData()

    companion object {
        const val TAG = "MainViewModel"
    }

    fun signInAnonymously() {
        firebaseRepository.signInAnonymously()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableCompletableObserver() {
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
            .subscribe(object : DisposableSingleObserver<Room>() {
                override fun onSuccess(room: Room) {
                    viewState.value = MainViewState.State(room = room)
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, e.stackTraceToString())
                }
            })
    }

    fun checkRoomChanges(roomId: String, isCheckRoomConnections: Boolean) {
        firebaseRepository.checkRoomChanges(roomId, isCheckRoomConnections)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSingleObserver<Room>() {
                override fun onSuccess(room: Room) {
                    viewState.value = MainViewState.State(room = room)
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, e.stackTraceToString())
                }
            })
    }

    fun disconnectFromRoom(roomId: String) {
        firebaseRepository.disconnectFromRoom(roomId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : DisposableSingleObserver<Room>() {
                override fun onSuccess(room: Room) {
                    viewState.value = MainViewState.State(room = room)
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, e.stackTraceToString())
                }
            })
    }
}