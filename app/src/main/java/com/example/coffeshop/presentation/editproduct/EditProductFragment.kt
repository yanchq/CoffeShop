package com.example.coffeshop.presentation.editproduct

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.coffeshop.R
import com.example.coffeshop.databinding.FragmentEditProductBinding
import com.example.coffeshop.domain.entity.Item
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class EditProductFragment : Fragment() {

    private val viewModel: EditProductViewModel by viewModels()

    private var _binding: FragmentEditProductBinding? = null
    private val binding: FragmentEditProductBinding
        get() = _binding ?: throw RuntimeException("EditProductFragmentBinding is null")

    private val args: EditProductFragmentArgs by navArgs()
    private lateinit var item: Item

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentEditProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val itemId = args.itemId
        viewModel.getItem(itemId)

        setContent()

        binding.saveButton.setOnClickListener {
            val cupSize = binding.cupSizeSpinner.selectedItem as String
            val sugar = binding.sugarSpinner.selectedItem as String
            val syrup = binding.syrupSpinner.selectedItem as String
            viewModel.saveToOrder(item.copy(
                size = cupSize,
                sugar = sugar,
                syrup = syrup
            ))

            findNavController().navigate(
                EditProductFragmentDirections.actinoEditProductFragmentToChooseItemFragment()
            )
        }
    }

    private fun setContent() {

        viewModel.item.observe(viewLifecycleOwner) {
            item = it
            binding.productName.text = it.name
            Glide.with(this)
                .load(it.image)
                .placeholder(R.drawable.ic_product)
                .into(binding.icProduct)
        }
    }
}