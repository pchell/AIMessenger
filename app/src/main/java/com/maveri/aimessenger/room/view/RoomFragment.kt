package com.maveri.aimessenger.room.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.maveri.aimessenger.databinding.RoomFragmentBinding
import com.maveri.aimessenger.room.viewmodel.RoomViewModel
import com.maveri.aimessenger.room.viewmodel.RoomViewState
import com.maveri.aimessenger.room.widget.RoomStateDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RoomFragment : Fragment() {
    private lateinit var binding: RoomFragmentBinding
    private val viewModel by viewModels<RoomViewModel>()
    private val args by navArgs<RoomFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RoomFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.viewState.observe(viewLifecycleOwner, {
            it?.let { render(it) }
        })

        viewModel.checkRoomChanges(args.roomId, false)
    }

    private fun render(viewState: RoomViewState.State) {
        if (viewState.room != null) {
            when {
                viewState.room.isDisconnect ->
                    RoomStateDialogFragment.Builder(
                        clickListener = {
                            viewModel.disconnectFromRoom(viewState.room.roomId)
                            findNavController().popBackStack()
                        }
                    ).build().show(parentFragmentManager, null)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.viewState.value = null
        viewModel.viewState.removeObservers(viewLifecycleOwner)
    }
}

