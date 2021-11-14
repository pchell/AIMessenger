package com.maveri.aimessenger.room.view

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.maveri.aimessenger.R
import com.maveri.aimessenger.databinding.RoomFragmentBinding
import com.maveri.aimessenger.room.viewmodel.RoomViewModel
import com.maveri.aimessenger.room.viewmodel.RoomViewState
import com.maveri.aimessenger.room.widget.RoomMessageAdapter
import com.maveri.aimessenger.room.widget.RoomStateDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.room_fragment.*

@AndroidEntryPoint
class RoomFragment : Fragment() {
    private lateinit var binding: RoomFragmentBinding
    private val viewModel by viewModels<RoomViewModel>()
    private val args by navArgs<RoomFragmentArgs>()

    private val adapter = RoomMessageAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = RoomFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.headerView.setHeaderTitle(getString(R.string.header_room))

        binding.roomMessagesList.adapter = adapter

        viewModel.viewState.observe(viewLifecycleOwner, {
            it?.let { render(it) }
        })

        viewModel.checkRoomChanges(args.roomId, false)
        viewModel.getRoomMessages(args.roomId)

        binding.roomMessage.apply {
            setOnTouchListener { _, event ->
                if (event.action == MotionEvent.ACTION_UP) {
                    if (event.rawX >= (right - compoundDrawables[2].bounds.width())) {
                        if (text.trim().isNotEmpty()) {
                            viewModel.sendRoomMessage(args.roomId, text.toString())
                            text.clear()
                        }
                    }
                }
                false
            }
        }
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
        if (!viewState.message.isNullOrEmpty()) {
            adapter.submitList(viewState.message)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.viewState.value = null
        viewModel.viewState.removeObservers(viewLifecycleOwner)
    }
}

