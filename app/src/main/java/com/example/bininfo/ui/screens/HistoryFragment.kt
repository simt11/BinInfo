package com.example.bininfo.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.coroutineScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bininfo.database.AppDatabase
import com.example.bininfo.database.CardDao
import com.example.bininfo.databinding.FragmentHistoryBinding
import com.example.bininfo.ui.screens.viewmodel.CardNumberViewModel
import com.example.bininfo.ui.screens.viewmodel.CardNumberViewModelFactory
import com.example.bininfo.utilits.replaceFragment
import com.example.bininfo.utilits.showToast
import kotlinx.coroutines.launch

class HistoryFragment : Fragment(), CardNumberAdapter.OnItemClickListener {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var cardNumberAdapter: CardNumberAdapter
    private lateinit var db: AppDatabase
    private lateinit var cardDao: CardDao

    private val viewModel: CardNumberViewModel by activityViewModels {
        CardNumberViewModelFactory(
            (activity?.application as CardNumberApplication).database.cardDao()
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = AppDatabase.getDatabase(requireContext())
        cardDao = db.cardDao()
        cardNumberAdapter = CardNumberAdapter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = cardNumberAdapter

        lifecycle.coroutineScope.launch {
            viewModel.fullCardNumber().collect {
                cardNumberAdapter.submitList(it)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(cardNumber: String) {
        val bundle = Bundle()
        bundle.putString("CardNumber", cardNumber)
        val frag = BinFragment()
        frag.arguments = bundle
        replaceFragment(BinFragment())
    }
}