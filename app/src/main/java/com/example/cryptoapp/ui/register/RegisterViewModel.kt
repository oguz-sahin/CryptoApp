package com.example.cryptoapp.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoapp.R
import com.example.cryptoapp.data.Resource
import com.example.cryptoapp.data.Resource.Companion.error
import com.example.cryptoapp.data.Resource.Companion.loading
import com.example.cryptoapp.data.Resource.Companion.success
import com.example.cryptoapp.util.isValidEmail
import com.example.cryptoapp.util.isValidPassword
import com.google.firebase.auth.FirebaseAuth
import com.hadilq.liveevent.LiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(private val firebaseAuth: FirebaseAuth) : ViewModel() {

    private val _invalidEmailMessage = MutableLiveData<Int>()
    val invalidEmailMessage: LiveData<Int> get() = _invalidEmailMessage

    private val _invalidPasswordMessage = MutableLiveData<Int>()
    val invalidPasswordMessage: LiveData<Int> get() = _invalidPasswordMessage

    private val _notSamePasswordMessage = MutableLiveData<Int>()
    val notSamePasswordMessage: LiveData<Int> get() = _notSamePasswordMessage

    private val _signUpStatus = LiveEvent<Resource<Boolean>>()
    val signUpStatus: LiveData<Resource<Boolean>> = _signUpStatus


    fun signUp(email: String, password: String, confirmPassword: String) {
        if (isEmailValid(email).not()) return
        if (isPasswordValid(password, confirmPassword)) {
            signUpEmailAndPassword(email, password)
        }
    }

    private fun isEmailValid(email: String): Boolean {
        return if (email.isValidEmail().not()) {
            _invalidEmailMessage.postValue(R.string.register_enter_valid_email_error)
            false
        } else {
            true
        }
    }

    private fun isPasswordValid(password: String, confirmPassword: String): Boolean {
        return if (password.isValidPassword()) {
            if (password == confirmPassword) {
                true
            } else {
                _notSamePasswordMessage.postValue(R.string.register_not_same_password_error)
                false
            }
        } else {
            _invalidPasswordMessage.postValue(R.string.register_enter_valid_password_error)
            false
        }
    }

    private fun signUpEmailAndPassword(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _signUpStatus.postValue(loading())
            firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        _signUpStatus.postValue(success(true))
                    } else {
                        _signUpStatus.postValue(
                            error(
                                task.exception?.message ?: "Something Went Wrong"
                            )
                        )
                    }
                }
        }
    }
}