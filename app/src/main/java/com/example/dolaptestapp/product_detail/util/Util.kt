package com.example.dolaptestapp.product_detail.util

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.dolaptestapp.R
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable


// Circular progress indicator while image is loading
fun getProgressDrawable(context: Context): CircularProgressDrawable{
    return CircularProgressDrawable(context).apply {
        strokeWidth = 10f
        centerRadius = 50f
        start()
    }
}

// Extension function which loads an image by using Glide library
fun ImageView.loadImage(uri: String?, progressDrawable: CircularProgressDrawable){
    val options = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.drawable.error)

    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(uri)
        .into(this)
}

// This binding adapter method loads the images by using the url and extension function(loadImage)
@BindingAdapter("android:imageUrl")
fun loadImage(view: ImageView, url: String?){
    view.loadImage(url, getProgressDrawable(view.context))
}

// This binding adapter method sums anonymous and member comments to show the total number of comments
@BindingAdapter(value= ["app:anonymousComments", "app:memberComments"])
fun setTotalComments(view: TextView, anonymousComments: Int, memberComments: Int) {
    view.text = "("+ (anonymousComments + memberComments).toString() + " yorum)"
}