package com.maveri.aimessenger.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maveri.aimessenger.repository.FirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.observers.DisposableCompletableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val firebaseRepository: FirebaseRepository): ViewModel() {

    val viewState: MutableLiveData<MainViewState> = MutableLiveData()

    fun signInAnonymously() {
        firebaseRepository.signInAnonymously().subscribe(object : DisposableCompletableObserver() {
            override fun onComplete() {
                viewState.value = MainViewState(authStatus = AuthFirebaseStatus.Success )
            }

            override fun onError(e: Throwable?) {
                viewState.value = MainViewState(authStatus = AuthFirebaseStatus.Error )
            }

        })
    }

    fun startUserSearch() {
        firebaseRepository.startUserSearch()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<String> {
                override fun onSubscribe(item: Disposable) {

                }

                override fun onNext(token: String) {
                    viewState.value = MainViewState(token = token)
                }

                override fun onError(e: Throwable) {

                }

                override fun onComplete() {

                }
            })

    }
}