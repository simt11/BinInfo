package com.example.bininfo.ui.screens

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.bininfo.database.AppDatabase
import com.example.bininfo.database.CardDao
import com.example.bininfo.database.CardData
import com.example.bininfo.databinding.FragmentBinBinding
import com.example.bininfo.network.BinCard
import com.example.bininfo.ui.screens.viewmodel.BinViewModel
import com.example.bininfo.utilits.hideKeyboard
import com.example.bininfo.utilits.replaceFragment
import com.example.bininfo.utilits.showToast
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class BinFragment() : Fragment() {

    private val BIN_NUMBER: String = ""
    private lateinit var binding: FragmentBinBinding
    private lateinit var viewModel: BinViewModel
    private lateinit var mainActivity: MainActivity
    private lateinit var db: AppDatabase
    private lateinit var cardDao: CardDao
    private lateinit var preferences: SharedPreferences

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
        db = AppDatabase.getDatabase(requireContext())
        cardDao = db.cardDao()
    }
/*    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments?.getString("CardNumber") != null){
            binding.enterCardNumber.setText(arguments?.getString("CardNumber").toString())
            showToast(arguments?.getString("CardNumber").toString())}
    }*/
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        this.binding = FragmentBinBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferences = requireActivity().getSharedPreferences("BIN", Context.MODE_PRIVATE)
        viewModel = ViewModelProvider(this).get(BinViewModel::class.java)
        val string = arguments?.getString("CardNumber")
        if ( string != null){
            binding.enterCardNumber.setText(string.toString(),TextView.BufferType.NORMAL)
            showToast(string.toString())
        }
        binding.root.setOnTouchListener { _, _ ->
            hideKeyboard()
            false
        }
        binding.enterCardNumber.setOnClickListener {
            true
        }


        binding.registerBtnNext.setOnClickListener {
            val string = binding.enterCardNumber.text.toString()
            viewModel.getBinInfo(string)
            viewModel.viewFlag.observe(viewLifecycleOwner) {
                if (it.isMinLength) {
                    hideKeyboard()
                    saveCardNumber(string)
                    viewModel.binUiStatus.observe(viewLifecycleOwner) {
                        setValue(it)
                    }
                }
            }
        }

        binding.answerCoordinates.setOnClickListener {
            val coordinates = binding.answerCoordinates.text.toString()
            viewModel.answerCoordinat(coordinates)
            viewModel.intent.observe(viewLifecycleOwner) {
                requireActivity().startActivity(it)
            }
        }
        binding.answerBankUrl.setOnClickListener {
            val bankUrl = binding.answerBankUrl.text.toString()
            viewModel.answerBankUrl(bankUrl)
            viewModel.intent.observe(viewLifecycleOwner) {
                requireActivity().startActivity(it)
            }
        }
        binding.answerBankPhone.setOnClickListener {
            val bankPhone = binding.answerBankPhone.text.toString()
            viewModel.answerBankPhone(bankPhone)
            viewModel.intent.observe(viewLifecycleOwner) {
                requireActivity().startActivity(it)
            }
        }
        binding.buttonHistory.setOnClickListener {
            replaceFragment(HistoryFragment())
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
        binding.answerCoordinates.text =
            "${listResults.country?.latitude}, ${listResults.country?.longitude}"
        binding.answerBankName.text = listResults.bank?.name
        binding.answerBankUrl.text = listResults.bank?.url
        binding.answerBankPhone.text = listResults.bank?.phone
        binding.answerBankCity.text = listResults.bank?.city
    }

    private fun saveCardNumber(number: String) {
        val cardData = CardData(cardNumber = number)
        GlobalScope.launch {
            cardDao.insertCardData(cardData)
        }
    }

}
