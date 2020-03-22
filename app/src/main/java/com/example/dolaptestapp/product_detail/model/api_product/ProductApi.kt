package com.example.dolaptestapp.product_detail.model.api_product

import com.example.dolaptestapp.product_detail.model.ProductDetails
import io.reactivex.Single
import retrofit2.http.GET

interface ProductApi {
    // TODO: Replace this test API's endpoint with your endpoint
    @GET("bins/cfxu4")
    fun getProductDetails(): Single<ProductDetails>
}