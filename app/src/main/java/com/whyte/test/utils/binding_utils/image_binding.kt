package com.whyte.test.utils.binding_utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide

@BindingAdapter("imageUrl")
fun setImageFromUrl(mImageView: ImageView, valItem: String?) {
    valItem?.let {
        mImageView.context?.let {
            Glide.with(it)
                .load( valItem)
                .into(mImageView)
        }
    }
}