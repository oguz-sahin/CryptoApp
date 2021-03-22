package com.example.cryptoapp.ui.register

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.cryptoapp.R
import com.example.cryptoapp.data.Status.*
import com.example.cryptoapp.data.model.AuthModel
import com.example.cryptoapp.databinding.FragmentRegisterBinding
import com.example.cryptoapp.util.clearError
import com.example.cryptoapp.util.extensions.showErrorToast
import com.example.cryptoapp.util.extensions.showSuccessToast
import com.example.cryptoapp.util.getString
import com.example.cryptoapp.util.setErrorAndClearFocus
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {
    private lateinit var binding: FragmentRegisterBinding
    private val registerViewModel: RegisterViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegisterBinding.inflate(inflater, container, false).also {
            it.lifecycleOwner = viewLifecycleOwner
        }
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initObserver()
        initListener()
        initClickEvents()

    }

    private fun initClickEvents() {
        binding.ButtonSignUp.setOnClickListener {
            val fields = getFields()
            registerViewModel.signUp(fields.email, fields.password, fields.confirmPassword)
        }

    }

    private fun initListener() {
        binding.TextInputEditTextEmail.onFocusChangeListener =
            View.OnFocusChangeListener { _, hasFocus: Boolean ->
                if (hasFocus) {
                    binding.TextInputLayoutEmail.clearError()
                }
            }

        binding.TextInputEditTextPassword.onFocusChangeListener =
            View.OnFocusChangeListener { _, hasFocus: Boolean ->
                if (hasFocus) {
                    binding.TextInputLayoutPassword.clearError()
                }
            }

        binding.TextInputEditTextConfirmPassword.onFocusChangeListener =
            View.OnFocusChangeListener { _, hasFocus ->
                if (hasFocus) {
                    binding.TextInputLayoutConfirmPassword.clearError()
                }
            }
    }

    private fun getFields(): AuthModel {
        val email = binding.TextInputEditTextEmail.getString()
        val password = binding.TextInputEditTextPassword.getString()
        val confirmPassword = binding.TextInputEditTextConfirmPassword.getString()
        return AuthModel(email, password, confirmPassword)
    }


    private fun initObserver() {
        registerViewModel.invalidEmailMessage.observe(viewLifecycleOwner, { invalidEmailMessage ->
            invalidEmailMessage?.let {
                binding.TextInputLayoutEmail.setErrorAndClearFocus(getString(it))
            }
        })

        registerViewModel.invalidPasswordMessage.observe(viewLifecycleOwner,
            { invalidPasswordMessage ->
                invalidPasswordMessage?.let {
                    binding.TextInputLayoutPassword.setErrorAndClearFocus(getString(it))
                }
            })

        registerViewModel.notSamePasswordMessage.observe(viewLifecycleOwner,
            { notSamePasswordMessage ->
                notSamePasswordMessage?.let {
                    binding.TextInputLayoutConfirmPassword.setErrorAndClearFocus(getString(it))
                }
            })

        registerViewModel.signUpStatus.observe(viewLifecycleOwner, { status ->
            status?.let {
                when (it.status) {
                    LOADING -> Unit
                    SUCCESS -> {
                        showSuccessToast(
                            getString(R.string.register_success_message),
                            Toast.LENGTH_LONG
                        )
                        openHomeFragment()
                    }
                    ERROR -> {
                        showErrorToast(
                            it.message,
                            Toast.LENGTH_LONG
                        )
                    }
                }
                binding.isLoadingStatus = it.status == LOADING
            }
        })
    }

    private fun openHomeFragment() {
        findNavController().navigate(R.id.action_registerFragment_to_homeFragment)
    }
}