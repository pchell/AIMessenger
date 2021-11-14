package com.maveri.aimessenger.common

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.maveri.aimessenger.databinding.AllHeaderViewBinding

class HeaderView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) :
    FrameLayout(context, attrs, defStyleAttr) {

    private val binding = AllHeaderViewBinding.inflate(LayoutInflater.from(context), this, true)

    fun setHeaderTitle(title: String) {
      binding.headerTitle.text = title
    }
}