package com.maveri.aimessenger.room

import android.text.SpannableString
import android.text.Spanned
import android.text.style.RelativeSizeSpan
import java.text.DateFormatSymbols
import java.text.SimpleDateFormat
import java.util.*

object MessageUtil {
    private const val TIME_FORMAT = "HH:mm"
    private const val DATE_FORMAT = "dd/MM"

    fun getConvertedTime(time: Long) : SpannableString {
        val date = SpannableString(SimpleDateFormat(TIME_FORMAT).format(Date(time)))
        date.setSpan(RelativeSizeSpan(0.5f), 0, 5, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        return date
    }

    fun getConvertedDate(time: Long) : String{
        val dateParts = SpannableString(SimpleDateFormat(DATE_FORMAT).format(Date(time))).split("/")

        return DateFormatSymbols().months[Integer.parseInt(dateParts[1])-1] + " " + dateParts[0]
    }
}