package com.maveri.aimessenger.di

import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.maveri.aimessenger.BaseApplication
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn

@Module
@InstallIn(BaseApplication::class)
class FirebaseModule {

    @Provides
    fun provideFirebaseDatabase() = FirebaseDatabase.getInstance()

    @Provides
    fun provideFirebaseAuth() = Firebase.auth
}