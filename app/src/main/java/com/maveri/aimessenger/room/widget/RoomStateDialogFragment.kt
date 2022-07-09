package com.maveri.aimessenger.room.widget

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.maveri.aimessenger.databinding.RoomStateDialogViewBinding

class RoomStateDialogFragment : DialogFragment() {

    private lateinit var binding: RoomStateDialogViewBinding

    private var buttonOnClickListener: View.OnClickListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = RoomStateDialogViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.roomDisconnect.setOnClickListener {
            dismiss()
            buttonOnClickListener?.onClick(it)
        }
    }

    class Builder(val clickListener: View.OnClickListener? = null) {
        fun build(): RoomStateDialogFragment {
            return RoomStateDialogFragment().apply {
                buttonOnClickListener = clickListener
            }
        }
    }
}
