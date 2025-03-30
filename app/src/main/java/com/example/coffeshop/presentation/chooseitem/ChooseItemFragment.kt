package com.example.coffeshop.presentation.chooseitem

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSmoothScroller
import androidx.recyclerview.widget.LinearSmoothScroller.SNAP_TO_START
import com.example.coffeshop.databinding.FragmentChooseItemBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChooseItemFragment : Fragment() {


    private var _binding: FragmentChooseItemBinding? = null
    private val binding: FragmentChooseItemBinding
        get() = _binding ?: throw RuntimeException("FragmentChooseItemBinding is null")

    private val viewModel: ChooseItemViewModel by viewModels()

    private lateinit var itemListAdapter: ItemListAdapter
    private lateinit var categoryListAdapter: CategoryListAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChooseItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.listItem.observe(viewLifecycleOwner) {
            setupCategoryRecyclerView()
            setupProductsRecyclerView(it)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupCategoryRecyclerView() {
        val categories = viewModel.getListCategory()
        categoryListAdapter = CategoryListAdapter(categories) { categoryIndex ->
            scrollToCategory(categoryIndex, categories)
        }
        binding.categoryRecyclerView.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        binding.categoryRecyclerView.adapter = categoryListAdapter
    }

    private fun setupProductsRecyclerView(products: List<Any>) {
        itemListAdapter = ItemListAdapter(products)
        val layoutManager = GridLayoutManager(context, 3) // Сетка с 3 элементами в строке
        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
            override fun getSpanSize(position: Int): Int {

                return if (itemListAdapter.isHeader(position)) 3 else 1
            }
        }
        binding.productsRecyclerView.layoutManager = layoutManager
        binding.productsRecyclerView.adapter = itemListAdapter
    }

    private fun scrollToCategory(categoryIndex: Int, categories: List<String>) {
        val targetCategory = categories[categoryIndex]
        val targetPosition = findPositionForCategory(targetCategory)
        if (targetPosition != -1) {
            val smoothScroller = object : LinearSmoothScroller(context) {
                override fun getVerticalSnapPreference(): Int {
                    return SNAP_TO_START
                }
            }
            smoothScroller.targetPosition = targetPosition
            binding.productsRecyclerView.layoutManager?.startSmoothScroll(smoothScroller)
        }
    }

    private fun findPositionForCategory(category: String): Int {
        // Находим позицию заголовка категории
        return itemListAdapter.items.indexOfFirst { it is String && it == category }
    }
}