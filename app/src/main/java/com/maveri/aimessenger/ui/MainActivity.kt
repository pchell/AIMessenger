package com.maveri.aimessenger.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Observer
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

    private fun render(viewState: MainViewState) {
        when (viewState.authStatus) {
            is AuthFirebaseStatus.Success -> binding.mainStartSearchButton.isEnabled = true
            is AuthFirebaseStatus.Error -> binding.mainStartSearchButton.isEnabled = false
        }
    }


}