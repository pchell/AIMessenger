package com.maveri.aimessenger.room.widget

import android.R.attr.timeZone
import android.content.Context
import android.text.SpannableString
import android.text.Spanned
import android.text.style.RelativeSizeSpan
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.maveri.aimessenger.databinding.MessageOtherItemBinding
import com.maveri.aimessenger.model.Message
import dagger.hilt.android.AndroidEntryPoint
import java.text.DateFormat
import java.text.DateFormatSymbols
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
        binding.messageOtherText.text = item.message
        binding.messageOtherTime.text = convertingTime(item.timestamp)
        if(item.visibilityDate) {
            binding.messageOtherDate.text = convertingDate(item.timestamp)
            binding.messageOtherDate.visibility = View.VISIBLE
        }
    }

    private fun convertingTime(time: Long) : SpannableString{
        val date = SpannableString(SimpleDateFormat("HH:mm").format(Date(time)))
        date.setSpan(RelativeSizeSpan(0.5f), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        return date
    }

    private fun convertingDate(time: Long) : String{
        val dateParts = SpannableString(SimpleDateFormat("dd/MM").format(Date(time))).split("/")

        return DateFormatSymbols().months[Integer.parseInt(dateParts[1])-1] + " " + dateParts[0]
    }
}