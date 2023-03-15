package com.example.bininfo.ui.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bininfo.database.AppDatabase
import com.example.bininfo.database.CardDao
import com.example.bininfo.databinding.FragmentHistoryBinding
import com.example.bininfo.ui.screens.viewmodel.CardNumberViewModel
import com.example.bininfo.ui.screens.viewmodel.CardNumberViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HistoryFragment : Fragment() {
    private var _binding: FragmentHistoryBinding? = null
    private val binding get() = _binding!!
    private lateinit var recyclerView: RecyclerView
    private lateinit var cardNumberAdapter: CardNumberAdapter
    private lateinit var db: AppDatabase
    private lateinit var cardDao: CardDao
    private val viewModel: CardNumberViewModel by viewModels()

/*    private val viewModel: CardNumberViewModel by activityViewModels {
        CardNumberViewModelFactory(
            (activity?.application as CardNumberApplication).database.cardDao()
        )
    }*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = AppDatabase.getDatabase(requireContext())
        cardDao = db.cardDao()
        cardNumberAdapter = CardNumberAdapter({})
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
        val cardNumberAdapter = CardNumberAdapter({})
        recyclerView.adapter = cardNumberAdapter

        lifecycleScope.launch {
            viewModel.fullCardNumber().collectLatest { cardDataList ->
                cardDataList.subList(cardDataList)
            }
        }
/*        viewModel.fullCardNumber().collect(viewLifecycleOwner){cardDataList ->
            cardDataList.sub

        }*/
/*        lifecycle.coroutineScope.launch {
            viewModel.fullCardNumber().collect(){
                cardNumberAdapter.submitList(it)
            }
        }*/
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}