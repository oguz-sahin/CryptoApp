package com.example.cryptoapp.ui.login

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
import com.example.cryptoapp.databinding.FragmentLoginBinding
import com.example.cryptoapp.util.clearError
import com.example.cryptoapp.util.extensions.showErrorToast
import com.example.cryptoapp.util.getString
import com.example.cryptoapp.util.setErrorAndClearFocus
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private val loginViewModel: LoginViewModel by viewModels()
    private lateinit var binding: FragmentLoginBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initObserver()
        initClickEvents()
        initListener()
    }

    private fun initObserver() {
        loginViewModel.invalidEmailMessage.observe(viewLifecycleOwner, { message ->
            message?.let {
                binding.TextInputLayoutEmail.setErrorAndClearFocus(getString(it))
            }
        })

        loginViewModel.invalidPasswordMessage.observe(viewLifecycleOwner, { message ->
            message?.let {
                binding.TextInputLayoutPassword.setErrorAndClearFocus(getString(it))
            }
        })

        loginViewModel.status.observe(viewLifecycleOwner, {
            it?.let {
                when (it.status) {
                    LOADING -> Unit
                    SUCCESS -> openHomePage()
                    ERROR -> showErrorToast(
                        it.message, Toast.LENGTH_LONG
                    )
                }
                binding.isLoadingStatus = it.status == LOADING
            }
        }
        )
    }

    private fun openHomePage() {
        findNavController().navigate(R.id.action_loginFragment_to_homeFragment)
    }

    private fun initClickEvents() {
        binding.ButtonSignIn.setOnClickListener {
            val authModel = getEmailAndPassword()
            loginViewModel.sigIn(authModel.email, authModel.password)
        }

        binding.TextViewSignUp.setOnClickListener {
            openRegisterPage()
        }
    }

    private fun openRegisterPage() {
        findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
    }

    private fun getEmailAndPassword(): AuthModel {
        val email = binding.TextInputEditTextEmail.getString()
        val password = binding.TextInputEditTextPassword.getString()
        return AuthModel(email, password)
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
    }


}