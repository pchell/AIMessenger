package com.maveri.aimessenger.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.navigation.findNavController
import com.maveri.aimessenger.R
import com.maveri.aimessenger.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //TODO: migrate to viewLifecycleOwner
        viewModel.viewState.observe(this, {
            render(it)
        })

        viewModel.signInAnonymously()

        binding.mainStartSearchButton.setOnClickListener {
            viewModel.startUserSearch()
        }
    }

    private fun render(viewState: MainViewState.State) {
        if (viewState.room != null) {
            if(viewState.room.isMyRoom) {

            } else {

            }
            Toast.makeText(this, viewState.token, Toast.LENGTH_LONG).show()
        } else {
            when (viewState.authStatus) {
                is MainViewState.AuthFirebaseStatus.Success -> binding.mainStartSearchButton.isEnabled = true
                is MainViewState.AuthFirebaseStatus.Error -> binding.mainStartSearchButton.isEnabled = false
            }
        }
    }
}