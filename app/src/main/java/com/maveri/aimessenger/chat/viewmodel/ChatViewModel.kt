package com.maveri.aimessenger.chat.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maveri.aimessenger.repository.FirebaseRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(private val firebaseRepository: FirebaseRepository): ViewModel() {
    val viewState: MutableLiveData<ChatViewState.State> = MutableLiveData()
}