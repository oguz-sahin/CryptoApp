package com.example.cryptoapp.util

import android.text.TextUtils
import android.util.Patterns
import java.util.*

fun String.isValidEmail() =
    TextUtils.isEmpty(this).not() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

fun String.isValidPassword() =
    TextUtils.isEmpty(this).not() && this.length >= 6

fun String?.upperCase() = this?.toUpperCase(Locale.getDefault())

fun String.convertStringForSearchQuery() = "%$this%"