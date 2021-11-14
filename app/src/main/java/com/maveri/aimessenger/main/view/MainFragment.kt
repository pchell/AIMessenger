package com.maveri.aimessenger.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.maveri.aimessenger.R
import com.maveri.aimessenger.databinding.MainFragmentBinding
import com.maveri.aimessenger.main.viewmodel.MainViewModel
import com.maveri.aimessenger.main.viewmodel.MainViewState
import com.maveri.aimessenger.main.widget.SearchDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment() {
    private lateinit var binding: MainFragmentBinding
    private val viewModel by viewModels<MainViewModel>()

    private var searchDialog: SearchDialogFragment? = null

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

        binding.headerView.setHeaderTitle(getString(R.string.header_main))

        viewModel.viewState.observe(viewLifecycleOwner, {
            it?.let { render(it) }
        })

        viewModel.signInAnonymously()

        binding.mainStartSearchButton.setOnClickListener {
            viewModel.startUserSearch()
        }
    }

    private fun render(viewState: MainViewState.State?) {
        if (viewState?.room != null) {
            when {
                viewState.room.isDisconnect -> {
                    searchDialog?.dismiss()
                }
                viewState.room.isReadyStart -> {
                    findNavController().navigate(MainFragmentDirections.toChat(roomId = viewState.room.roomId))
                    searchDialog?.dismiss()
                }
                else -> {
                    viewModel.checkRoomChanges(viewState.room.roomId, true)
                    searchDialog = SearchDialogFragment.Builder(
                        clickListener = {
                            viewModel.disconnectFromRoom(viewState.room.roomId)
                        }
                    ).build()
                    searchDialog?.show(parentFragmentManager, null)
                }
            }
        } else {
            when (viewState?.authStatus) {
                is MainViewState.AuthFirebaseStatus.Success -> binding.mainStartSearchButton.isEnabled =
                    true
                is MainViewState.AuthFirebaseStatus.Error -> binding.mainStartSearchButton.isEnabled =
                    false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.viewState.value = null
        viewModel.viewState.removeObservers(viewLifecycleOwner)
    }
}