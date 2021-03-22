package com.example.cryptoapp.ui.login

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptoapp.R
import com.example.cryptoapp.data.Resource
import com.example.cryptoapp.data.Resource.Companion.error
import com.example.cryptoapp.data.Resource.Companion.loading
import com.example.cryptoapp.data.Resource.Companion.success
import com.google.firebase.auth.FirebaseAuth
import com.hadilq.liveevent.LiveEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val firebaseAuth: FirebaseAuth) : ViewModel() {

    private val _invalidEmailMessage = MutableLiveData<Int>()
    val invalidEmailMessage: LiveData<Int> get() = _invalidEmailMessage

    private val _invalidPasswordMessage = MutableLiveData<Int>()
    val invalidPasswordMessage: LiveData<Int> get() = _invalidPasswordMessage

    private val _status = LiveEvent<Resource<Boolean>>()
    val status: LiveData<Resource<Boolean>> get() = _status


    fun sigIn(email: String, password: String) {
        val isFieldsFull = isEmailValid(email) && isPasswordValid(password)
        if (isFieldsFull) {
            sigInEmailAndPassword(email, password)
        }
    }


    private fun isEmailValid(email: String): Boolean {
        return if (TextUtils.isEmpty(email)) {
            _invalidEmailMessage.postValue(R.string.login_enter_email_error)
            false
        } else true
    }


    private fun isPasswordValid(password: String): Boolean {
        return if (TextUtils.isEmpty(password)) {
            _invalidPasswordMessage.postValue(R.string.login_enter_password_error)
            false
        } else true
    }

    private fun sigInEmailAndPassword(email: String, password: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _status.postValue(loading())
            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    _status.postValue(success(true))
                } else {
                    _status.postValue(error(task.exception?.message))
                }
            }
        }
    }

}