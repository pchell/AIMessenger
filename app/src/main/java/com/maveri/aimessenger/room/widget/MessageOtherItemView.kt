package com.maveri.aimessenger.room.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.maveri.aimessenger.databinding.MessageOtherItemBinding
import com.maveri.aimessenger.model.Message
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MessageOtherItemView
@JvmOverloads constructor(context: Context, attr: AttributeSet? = null, defStyleAttr: Int = 0) :
    FrameLayout(context, attr, defStyleAttr) {
    private val binding = MessageOtherItemBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        layoutParams = binding.root.layoutParams
    }

    fun setItem(item: Message) {
        binding.messageOtherText.text = item.message
    }
}