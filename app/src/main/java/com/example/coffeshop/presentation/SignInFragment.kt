package com.example.coffeshop.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.coffeshop.R
import com.example.coffeshop.databinding.FragmentSignInBinding
import kotlinx.coroutines.launch

class SignInFragment : Fragment() {

    private var _binding: FragmentSignInBinding? = null
    private val binding: FragmentSignInBinding
        get() = _binding ?: throw RuntimeException("FragmentSignInBinding is null")

    private val viewModel: SignInViewModel by lazy {
        ViewModelProvider(this)[SignInViewModel::class]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignInBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        launchObservers()
        launchButtons()
        launchEditTexts()
    }

    private fun launchButtons() {

        binding.registerButton.setOnClickListener {
            findNavController().navigate(
                SignInFragmentDirections
                    .actionSignInFragmentToSignUpFragment()
            )
        }

        binding.loginButton.setOnClickListener {

            lifecycleScope.launch {
                viewModel.login(
                    binding.etEmail.text.toString(),
                    binding.etPassword.text.toString(),
                    {
                        findNavController().navigate(
                            SignInFragmentDirections
                                .actionSignInFragmentToHomeFragment()
                        )
                    }
                )
            }
        }
    }

    private fun launchObservers() {

        viewModel.weakPasswordFlag.observe(viewLifecycleOwner) {
            binding.etPasswordLayout.error = resources.getString(R.string.weak_password)
        }

        viewModel.badEmailFlag.observe(viewLifecycleOwner) {
            binding.etEmailLayout.error = resources.getString(R.string.incorrect_email)
        }
    }

    private fun launchEditTexts() {

        binding.etPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                binding.etPasswordLayout.error?.apply {
                    binding.etPasswordLayout.error = null
                }
            }
        })

        binding.etEmail.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                binding.etEmailLayout.error?.apply {
                    binding.etEmailLayout.error = null
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}