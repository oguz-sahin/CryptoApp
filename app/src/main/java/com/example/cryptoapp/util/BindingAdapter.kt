package com.example.cryptoapp.util

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("app:loadUrl")
fun ImageView.loadImage(url: String?) {
    if (url.isNullOrEmpty().not()) {
        Glide.with(this.context).load(url).into(this)
    }
}