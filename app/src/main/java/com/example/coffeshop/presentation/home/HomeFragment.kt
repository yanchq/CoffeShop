package com.example.coffeshop.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.coffeshop.R
import com.example.coffeshop.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = _binding ?: throw RuntimeException("FragmentHomeBinding is null")

    private val viewModel: HomeViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = childFragmentManager.findFragmentById(R.id.home)!!.findNavController()

        binding.tv.setOnClickListener {
            lifecycleScope.launch {
                viewModel.unlogged()
            }
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToLoadFragment())
        }

        binding.orderButton.setOnClickListener {
            if (navController.currentDestination?.id != R.id.orderFragment)
                navController.navigate(R.id.action_global_orderFragment)
        }

        binding.homeButton.setOnClickListener {
            if (navController.currentDestination?.id != R.id.chooseItemFragment)
                navController.popBackStack(R.id.chooseItemFragment, false)
        }

        navController.addOnDestinationChangedListener { _, destination, _ ->
            updateButtons(destination.id)
        }
    }

    private fun updateButtons(currentDestinationId: Int) {
        when (currentDestinationId) {

            R.id.orderFragment -> {
                binding.homeButton.setImageResource(R.drawable.not_active_home_icon)
                binding.orderButton.setImageResource(R.drawable.active_order_icon)
            }

            else -> {
                binding.homeButton.setImageResource(R.drawable.active_home_icon)
                binding.orderButton.setImageResource(R.drawable.not_active_order_icon)
            }


        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}