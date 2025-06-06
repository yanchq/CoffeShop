package com.example.coffeshop.presentation.load

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.coffeshop.databinding.FragmentLoadBinding
import com.example.coffeshop.domain.entity.Item
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoadFragment : Fragment() {

    private val viewModel: LoadViewModel by viewModels()

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

        launchObservers()
        viewModel.getLoggedId()
    }

    private fun launchObservers() {
        viewModel.isLogged.observe(viewLifecycleOwner) {
            if (!it) {
                findNavController().navigate(LoadFragmentDirections.actionLoadFragmentToSignUpFragment())
            } else {
                viewModel.getListItem()
            }
        }

        viewModel.listItem.observe(viewLifecycleOwner) {
            it.forEach {
                if (it is Item) {
                    Glide.with(this)
                        .load(it.image)
                        .preload()
                }
            }
            findNavController().navigate(LoadFragmentDirections.actionLoadFragmentToHomeFragment())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}