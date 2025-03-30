package com.example.coffeshop.presentation.signup

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.coffeshop.R
import com.example.coffeshop.databinding.FragmentSignUpBinding
import com.example.coffeshop.domain.entity.Client
import com.example.coffeshop.presentation.SignUpFragmentDirections
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SignUpFragment : Fragment() {

    private var _binding: FragmentSignUpBinding? = null
    private val binding: FragmentSignUpBinding
        get() = _binding ?: throw RuntimeException("FragmentSignUpBinding is null")

    private val viewModel: SignUpViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSignUpBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        launchObservers()
        bindInputListeners()
        launchOnClickListeners()
    }

    private fun bindInputListeners() {

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

        binding.etUsername.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                binding.etUsernameLayout.error = null
            }

        })
    }

    private fun launchObservers() {

        viewModel.weakPasswordFlag.observe(viewLifecycleOwner) {
            binding.etPasswordLayout.error = resources.getString(R.string.weak_password)
        }

        viewModel.badEmailFlag.observe(viewLifecycleOwner) {
            when (it) {
                1 -> binding.etEmailLayout.error = resources.getString(R.string.incorrect_email)
                2 -> binding.etEmailLayout.error = resources.getString(R.string.user_exists)
            }
        }

        viewModel.badUsernameFlag.observe(viewLifecycleOwner) {
            binding.etUsernameLayout.error = resources.getString(R.string.incorrect_username)
        }
    }

    private fun launchOnClickListeners() {

        binding.registerButton.setOnClickListener {

            lifecycleScope.launch {
                try {
                    viewModel.saveClient(
                        Client(
                            name = binding.etUsername.text.toString(),
                            email = binding.etEmail.text.toString()
                        ),
                        binding.etPassword.text.toString(),
                        successCallback = {
                            findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToHomeFragment())
                        }
                    )
                }  catch (e: FirebaseAuthInvalidCredentialsException) {
                    binding.etEmailLayout.error = resources.getString(R.string.bad_email)
                }
            }
        }

        binding.signInButton.setOnClickListener {
            findNavController().navigate(SignUpFragmentDirections.actionSignUpFragmentToSignInFragment())
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

    }

}