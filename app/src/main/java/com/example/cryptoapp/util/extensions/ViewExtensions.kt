package com.example.cryptoapp.util

import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

fun TextInputLayout.clearError() {
    error = null
    isErrorEnabled = false
}

fun TextInputLayout.setErrorAndClearFocus(error: String) {
    this.error = error
    clearFocus()
}


fun View.getString(): String {
    when (this) {
        is TextView -> return text.toString()
        is EditText -> return text.toString()
    }
    return ""
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun EditText.getQueryTextChangeStateFlow(): StateFlow<String> {
    val query = MutableStateFlow("")
    this.addTextChangedListener {
        query.value = it.toString()
    }
    return query
}

fun View.setVisibility(isVisible: Boolean) {
    if (isVisible) visible() else gone()
}