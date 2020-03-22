package com.example.dolaptestapp.product_detail.model.api_social


import com.example.dolaptestapp.product_detail.model.SocialDetails
import io.reactivex.Single
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory




class SocialApiService {
    // TODO: Replace this test API's base url with your url
    private val BASE_URL = "https://api.myjson.com"


    private val api = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()
        .create(SocialApi::class.java)

    fun getSocialDetails(): Single<SocialDetails>{
        return api.getSocialDetails()
    }

}