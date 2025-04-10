package com.example.coffeshop.presentation.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.coffeshop.databinding.FragmentOrderBinding
import com.example.coffeshop.domain.entity.Item
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderFragment : Fragment() {

    private val viewModel: OrderViewModel by viewModels()

    private var _binding: FragmentOrderBinding? = null
    private val binding: FragmentOrderBinding
        get() = _binding ?: throw RuntimeException("FragmentOrderBinding is null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOrderBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getCurrentOrder()
        viewModel.currentOrder.observe(viewLifecycleOwner) {
            setupRecycler(it)
        }
    }

    private fun setupRecycler(orderList: List<Item>) {
        val adapter = OrderListAdapter(orderList)
        val layoutManager = LinearLayoutManager(context)
        binding.productsRecyclerView.layoutManager = layoutManager
        binding.productsRecyclerView.adapter = adapter
    }

}