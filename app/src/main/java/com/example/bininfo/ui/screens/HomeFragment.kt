package com.example.bininfo.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.bininfo.R
import com.example.bininfo.databinding.FragmentHomeBinding
import com.example.bininfo.utilits.AppTextWatcher

class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: BinViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        this.binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }
//45717360
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    viewModel = BinViewModel()
        binding.enterCardNumber.addTextChangedListener(AppTextWatcher {
            val string = binding.enterCardNumber.text.toString()
            if (string.length > 4) {
                viewModel.getBinInfo(string.toInt())
                val value = viewModel.binUiStatus
                binding.textAnswer.text = value
                Toast.makeText(context, value, Toast.LENGTH_SHORT).show()
            }
        })
    }
}