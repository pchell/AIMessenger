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


class SearchDialogFragment : DialogFragment() {

    private lateinit var binding: SearchDialogViewBinding

    private var buttonOnClickListener: View.OnClickListener? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = SearchDialogViewBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.searchCancel.setOnClickListener {
            dismiss()
            buttonOnClickListener?.onClick(it)
        }
    }

    class Builder(
        val clickListener: View.OnClickListener? = null,
    ) {
        fun build(): SearchDialogFragment {
            return SearchDialogFragment().apply {
                buttonOnClickListener = clickListener
            }
        }
    }
}
