package com.maveri.aimessenger.main.widget;

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.maveri.aimessenger.databinding.SearchDialogViewBinding
import kotlinx.android.synthetic.main.search_dialog_view.*


class SearchDialogFragment : DialogFragment(){

    private lateinit var binding: SearchDialogViewBinding

    private var negativeOnClickListener: View.OnClickListener? = null
    private var positiveOnClickListener: View.OnClickListener? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = SearchDialogViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchCancel.setOnClickListener {
            dismiss()
            negativeOnClickListener?.onClick(it)
        }
    }

    class Builder(
        val negativeListener: View.OnClickListener? = null,
        val positiveListener: View.OnClickListener? = null
    ) {
        fun build(): SearchDialogFragment {
            return SearchDialogFragment().apply {
                negativeOnClickListener = negativeListener
                positiveOnClickListener = positiveListener
            }
        }
    }
}
