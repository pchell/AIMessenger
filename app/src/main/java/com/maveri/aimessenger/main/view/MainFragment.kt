package com.maveri.aimessenger.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.maveri.aimessenger.databinding.MainFragmentBinding
import com.maveri.aimessenger.main.viewmodel.MainViewModel
import com.maveri.aimessenger.main.viewmodel.MainViewState
import com.maveri.aimessenger.main.widget.SearchDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {

    private lateinit var binding: MainFragmentBinding
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = MainFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.viewState.observe(viewLifecycleOwner, {
            render(it)
        })

        viewModel.signInAnonymously()

        binding.mainStartSearchButton.setOnClickListener {
            viewModel.startUserSearch()
        }
    }

    private fun render(viewState: MainViewState.State) {
        if (viewState.room != null) {
            if (viewState.room.isMyRoom) {
                SearchDialogFragment.Builder(
                    negativeListener = {

                    },
                    positiveListener = {

                    }
                ).build().show(parentFragmentManager, null)
            } else {
                findNavController().navigate(MainFragmentDirections.toChat())
            }
            Toast.makeText(context, viewState.token, Toast.LENGTH_LONG).show()
        } else {
            when (viewState.authStatus) {
                is MainViewState.AuthFirebaseStatus.Success -> binding.mainStartSearchButton.isEnabled =
                    true
                is MainViewState.AuthFirebaseStatus.Error -> binding.mainStartSearchButton.isEnabled =
                    false
            }
        }
    }
}