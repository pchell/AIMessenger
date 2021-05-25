package com.maveri.aimessenger.ui.main;

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.maveri.aimessenger.databinding.SearchDialogViewBinding
import kotlinx.android.synthetic.main.search_dialog_view.*


class DialogViewFragment : Fragment(){

    private var _binding: SearchDialogViewBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = SearchDialogViewBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
