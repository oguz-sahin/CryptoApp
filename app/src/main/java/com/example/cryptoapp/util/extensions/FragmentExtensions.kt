package com.example.cryptoapp.util.extensions

import androidx.fragment.app.Fragment
import com.example.cryptoapp.R
import es.dmoral.toasty.Toasty


fun Fragment.showSuccessToast(message: String, duration: Int) {
    Toasty.success(requireContext(), message, duration).show()
}

fun Fragment.showErrorToast(message: String?, duration: Int) {
    Toasty.error(requireContext(), message ?: getString(R.string.common_warning_message), duration)
        .show()
}