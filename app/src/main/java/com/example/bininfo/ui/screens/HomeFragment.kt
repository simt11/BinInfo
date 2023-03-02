package com.example.bininfo.ui.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.bininfo.BinCard
import com.example.bininfo.R
import com.example.bininfo.databinding.FragmentHomeBinding
import com.example.bininfo.utilits.AppTextWatcher

class HomeFragment(context: Context) : Fragment(R.layout.fragment_home) {
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
                    setValue(listResults)
                }
            }
        })

        binding.answerCoordinates.setOnClickListener {
            val Coordinates = binding.answerCoordinates.text.toString()
            if (binding.answerCoordinates.text.isNotEmpty()) {
                val queryCoordinates: Uri = Uri.parse("geo:${Coordinates}")
                val intent = Intent(Intent.ACTION_VIEW, queryCoordinates)
                context?.startActivity(intent)
            }
        }
        binding.answerBankUrl.setOnClickListener {
            val bankUrl = binding.answerBankUrl.text.toString()
            if (binding.answerBankUrl.text.isNotEmpty()) {
                val queryUrl: Uri = Uri.parse("https://${bankUrl}")
                val intent = Intent(Intent.ACTION_VIEW, queryUrl)
                context?.startActivity(intent)
            }
        }
        binding.answerBankPhone.setOnClickListener {
            val bankPhone = binding.answerBankPhone.text.toString()
            if (binding.answerBankPhone.text.isNotEmpty()) {
                val queryPhone: Uri = Uri.parse("{bankPhone}")
                val intent = Intent(Intent.ACTION_DIAL, queryPhone)
                context?.startActivity(intent)
            }
        }

    }

    fun setValue(listResults: BinCard) {
        binding.answerSCHEME.text = listResults.scheme
        binding.answerBRAND.text = listResults.brand
        binding.answerLength.text = listResults.number?.length.toString()
        binding.answerLuhn.text = listResults.number?.luhn.toString()
        binding.answerType.text = listResults.type
        binding.answerPrepaid.text = listResults.prepaid?.toString()
        binding.answerCountry.text = listResults.country?.name
        binding.answerCoordinates.text = "${listResults.country?.latitude}, ${listResults.country?.longitude}"
        binding.answerBankName.text = listResults.bank?.name
        binding.answerBankUrl.text = listResults.bank?.url
        binding.answerBankPhone.text = listResults.bank?.phone
        binding.answerBankCity.text = listResults.bank?.city
    }
}