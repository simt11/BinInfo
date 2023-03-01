package com.example.bininfo.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(BinViewModel::class.java)
        binding.enterCardNumber.addTextChangedListener(AppTextWatcher {
            val string = binding.enterCardNumber.text.toString()
            if (string.length > 4) {
                viewModel.getBinInfo(string)
                viewModel.binUiStatus.observe(viewLifecycleOwner) { listResults ->
                    binding.answerSCHEME.text = listResults.scheme
                    binding.answerBRAND.text = listResults.brand
                    binding.answerLength.text = listResults.number?.length.toString()
                    binding.answerLuhn.text = listResults.number?.luhn.toString()
                    binding.answerType.text = listResults.type
                    binding.answerPrepaid.text = listResults.prepaid?.toString()
                    binding.answerCountry.text = listResults.country?.currency
                    binding.answerBank.text = listResults.bank?.name
/*                    binding.answerSCHEME.text = listResults.scheme
                    binding.answerSCHEME.text = listResults.scheme
                    binding.answerSCHEME.text = listResults.scheme
                    binding.answerSCHEME.text = listResults.scheme
                    binding.answerSCHEME.text = listResults.scheme
                    binding.answerSCHEME.text = listResults.scheme
                    binding.answerSCHEME.text = listResults.scheme*/
                }
            }
        })
    }
}