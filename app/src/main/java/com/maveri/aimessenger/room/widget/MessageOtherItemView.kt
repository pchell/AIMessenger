package com.maveri.aimessenger.room.widget

import android.content.Context
import android.text.SpannableString
import android.text.Spanned
import android.text.style.RelativeSizeSpan
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.maveri.aimessenger.databinding.MessageOtherItemBinding
import com.maveri.aimessenger.model.Message
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class MessageOtherItemView
@JvmOverloads constructor(context: Context, attr: AttributeSet? = null, defStyleAttr: Int = 0) :
    FrameLayout(context, attr, defStyleAttr) {
    private val binding = MessageOtherItemBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        layoutParams = binding.root.layoutParams
    }

    fun setItem(item: Message) {
        val date = SpannableString(SimpleDateFormat("HH:mm").format(Date()))
        date.setSpan(RelativeSizeSpan(0.5f), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        binding.messageOtherText.text = item.message
        binding.messageOtherTime.text = date
    }
}