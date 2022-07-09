package com.maveri.aimessenger.room.widget

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.maveri.aimessenger.databinding.MessageUserItemBinding
import com.maveri.aimessenger.model.Message
import com.maveri.aimessenger.room.MessageUtil
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MessageUserItemView
@JvmOverloads constructor(context: Context, attr: AttributeSet? = null, defStyleAttr: Int = 0) :
    FrameLayout(context, attr, defStyleAttr) {
    private val binding = MessageUserItemBinding.inflate(LayoutInflater.from(context), this, true)

    init {
        layoutParams = binding.root.layoutParams
    }

    fun setItem(item: Message) {
        binding.messageUserText.text = item.message
        binding.messageUserTime.text = MessageUtil.getConvertedTime(item.timestamp)
        if(item.visibilityDate){
            binding.messageUserDate.text = MessageUtil.getConvertedDate(item.timestamp)
            binding.messageUserDate.visibility = View.VISIBLE
        }
    }
}