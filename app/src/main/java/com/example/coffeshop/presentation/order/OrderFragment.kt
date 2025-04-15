package com.example.coffeshop.presentation.order

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.coffeshop.databinding.FragmentOrderBinding
import com.example.coffeshop.domain.entity.Item
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

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
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.currentOrder.collect {
                    setupRecycler(it)
                    if (binding.saveButton.isEnabled == false && it.isNotEmpty())
                        binding.saveButton.isEnabled = true
                    else if (it.isEmpty() && binding.saveButton.isEnabled == true)
                        binding.saveButton.isEnabled = false
                }
            }
        }
        setupSwipeListener(binding.productsRecyclerView)

        binding.saveButton.setOnClickListener {
            binding.saveButton.isEnabled = false
            viewModel.placeOrder { orderId ->
                val intent = Intent(requireContext(), OrderStatusService::class.java).apply {
                    putExtra(OrderStatusService.EXTRA_ORDER_ID, orderId)
                }
                ContextCompat.startForegroundService(requireContext(), intent)
            }
        }
    }

    private fun setupRecycler(orderList: List<Item>) {
        val adapter = OrderListAdapter(orderList)
        val layoutManager = LinearLayoutManager(context)
        binding.productsRecyclerView.layoutManager = layoutManager
        binding.productsRecyclerView.adapter = adapter
    }

    private fun setupSwipeListener(rvShopList: RecyclerView?) {
        val callback = object : ItemTouchHelper.SimpleCallback(
            0,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = viewModel.currentOrder.value[viewHolder.adapterPosition]
                viewModel.deleteFromOrder(item.id)
            }
        }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(rvShopList)
    }
}