package com.example.coffeshop.presentation

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.coffeshop.R
import com.example.coffeshop.databinding.FragmentLoadBinding
import kotlinx.coroutines.launch

class LoadFragment : Fragment() {

    private lateinit var viewModel: LoadViewModel

    private var _binding: FragmentLoadBinding? = null
    private val binding: FragmentLoadBinding
        get() = _binding ?: throw RuntimeException("FragmentLoadBinding is null")


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoadBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(this)[LoadViewModel::class]
        launchObservers()
        lifecycleScope.launch {
            viewModel.getLoggedId()
        }
    }

    private fun launchObservers() {
        viewModel.loggedId.observe(viewLifecycleOwner) {
            if (it == 0L) {
                findNavController().navigate(LoadFragmentDirections
                    .actionLoadFragmentToSignUpFragment())
            } else {
                findNavController().navigate(LoadFragmentDirections
                    .actionLoadFragmentToHomeFragment())
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}