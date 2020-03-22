package com.example.dolaptestapp.product_detail.model.api_social

import com.example.dolaptestapp.product_detail.model.SocialDetails
import io.reactivex.Single
import retrofit2.http.GET

interface SocialApi {
    // TODO: Replace this test API's endpoint with your endpoint
    @GET("bins/1dezco")
    fun getSocialDetails(): Single<SocialDetails>
}