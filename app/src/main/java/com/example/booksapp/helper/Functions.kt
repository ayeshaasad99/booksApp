package com.example.booksapp.helper

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("profileImage")
fun loadImage(view: ImageView, imageUrl: String?) {

    Glide.with(view.context)
        .load(imageUrl)
        .into(view)

}



