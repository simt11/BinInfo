package com.example.bininfo.ui.screens

import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import com.example.bininfo.R
import com.example.bininfo.utilits.AppTextWatcher
import kotlinx.android.synthetic.main.fragment_home.enterCardNumber

class HomeFragment : Fragment(R.layout.fragment_home) {
    private lateinit var response: BinViewModel

    override fun onStart() {
        super.onStart()
/*        enterCardNumber.addTextChangedListener(AppTextWatcher {
            val string = enterCardNumber.text.toString()
            if (string > 4) {
                val value = response.getBinInfo(string.toInt()).binUiStatus
                Toast.makeText(context, "$value", Toast.LENGTH_SHORT).show()
            })

        }*/
    }
}