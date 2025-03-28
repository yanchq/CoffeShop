package com.example.coffeshop.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.coffeshop.databinding.FragmentChooseItemBinding
import com.example.coffeshop.databinding.FragmentSignInBinding
import com.example.coffeshop.domain.usecase.GetListItemUseCase
import kotlinx.coroutines.launch

class ChooseItemFragment: Fragment() {


    private var _binding: FragmentChooseItemBinding? = null
    private val binding: FragmentChooseItemBinding
        get() = _binding ?: throw RuntimeException("FragmentChooseItemBinding is null")

    private val viewModel: ChooseItemViewModel by lazy {
        ViewModelProvider(this)[ChooseItemViewModel::class]
    }


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

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}