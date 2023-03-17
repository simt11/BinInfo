package com.example.bininfo.ui.screens

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.bininfo.R
import com.example.bininfo.database.AppDatabase
import com.example.bininfo.database.CardDao
import com.example.bininfo.database.CardData
import com.example.bininfo.databinding.FragmentBinBinding
import com.example.bininfo.network.BinCard
import com.example.bininfo.ui.screens.viewmodel.BinViewModel
import com.example.bininfo.utilits.hideKeyboard
import com.example.bininfo.utilits.intentCoordinates
import com.example.bininfo.utilits.intentPhone
import com.example.bininfo.utilits.intentUrl
import com.example.bininfo.utilits.replaceFragment
import com.example.bininfo.utilits.showToast
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

val BIN_NUMBER: String = ""
private const val MIN_LENGTH_CARD = 4

class BinFragment() : Fragment() {
    private lateinit var binding: FragmentBinBinding
    private lateinit var viewModel: BinViewModel
    private lateinit var preferences: SharedPreferences
    private lateinit var mainActivity: MainActivity
    private lateinit var db: AppDatabase
    private lateinit var cardDao: CardDao

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
        db = AppDatabase.getDatabase(requireContext())
        cardDao = db.cardDao()
    }

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
        binding.root.setOnTouchListener { _, _ ->
            hideKeyboard()
            false
        }
        binding.enterCardNumber.setOnClickListener {
            true
        }


        binding.registerBtnNext.setOnClickListener {
            val string = binding.enterCardNumber.text.toString()
            hideKeyboard()
/*            if (string.length > MIN_LENGTH_CARD) {
                saveCardNumber(string)*/
                viewModel.getBinInfo(string)
                viewModel.binUiStatus.observe(viewLifecycleOwner) {setValue(it)}
/*            } else {
                showToast(getString(R.string.toast_min_number))
            }*/
        }

        binding.answerCoordinates.setOnClickListener {
            val coordinates = binding.answerCoordinates.text.toString()
            if (coordinates.isNotEmpty()) {
                intentCoordinates(coordinates)
            }
        }
        binding.answerBankUrl.setOnClickListener {
            val bankUrl = binding.answerBankUrl.text.toString()
            if (bankUrl.isNotEmpty()) {
                intentUrl(bankUrl)
            }
        }
        binding.answerBankPhone.setOnClickListener {
            val bankPhone = binding.answerBankPhone.text.toString()
            if (bankPhone.isNotEmpty()) {
                intentPhone(bankPhone)
            }
        }
        binding.buttonHistory.setOnClickListener {
            replaceFragment(HistoryFragment())
        }
    }

    override fun onPause() {
        super.onPause()
        val editor = preferences.edit()
        val string = binding.enterCardNumber.text.toString()
        editor.putString(BIN_NUMBER, string)
        editor.commit()
    }

    override fun onResume() {
        super.onResume()
        if (preferences.contains(BIN_NUMBER)) {
            val string = preferences.getString(BIN_NUMBER, "")
            if (arguments?.getString("CardNumber").isNullOrEmpty()) {
                binding.enterCardNumber.setText(string)
            } else {
                binding.enterCardNumber.setText(arguments?.getString("CardNumber").toString())
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
